package spbstu.cg.texteditor.controller;

import spbstu.cg.fontcommons.font.Font;
import spbstu.cg.fontcommons.font.FontManager;
import spbstu.cg.texteditor.model.TextEditorModel;
import spbstu.cg.texteditor.view.MainTextEditorView;
import spbstu.cg.texteditor.view.TextEditorView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by JDima on 26/04/15.
 */
public class TextEditorController extends Controller {
    private MainTextEditorView mainView;
    private TextEditorView textView;
    private TextEditorModel textEditorModel;

    public TextEditorController(MainTextEditorView view, TextEditorModel textEditorModel) {
        mainView = view;
        this.textEditorModel = textEditorModel;
        this.textView = view.getTextEditor();
        initViewListeners();

    }

    private List<String> openTextDialog() {
        final JFileChooser fc = new JFileChooser();

        int returnVal = fc.showOpenDialog(mainView);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File f = fc.getSelectedFile();
            String line;
            try (
                    InputStream fis = new FileInputStream(f.getPath());
                    InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
                    BufferedReader br = new BufferedReader(isr);
            ) {
                List<String> text = new LinkedList<>();
                while ((line = br.readLine()) != null) {
                    text.add(line);
                }
                return text;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private String saveTextDialog() {
        final JFileChooser fc = new JFileChooser();

        int returnVal = fc.showSaveDialog(mainView);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File f = fc.getSelectedFile();
            if (f.exists()) {
                try {
                    Files.delete(Paths.get(f.getPath()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return f.getPath();
        }
        return null;
    }

    private void openFontDialog() {
        final JFileChooser fc = new JFileChooser();

        int returnVal = fc.showOpenDialog(mainView);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File f = fc.getSelectedFile();
            Font font = FontManager.loadFontFromFile(f.getPath());
            return;
        }

    }

    private void initViewListeners() {
        mainView.getSizeComboBox().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                float size = Float.valueOf((String) mainView.getSizeComboBox().getSelectedItem());
                textEditorModel.setSize(size);
                textView.repaint();
                setStatus("New letter size is " + size);
            }
        });

        mainView.getFontComboBox().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String font = (String)mainView.getFontComboBox().getSelectedItem();
                mainView.getAvalaibleLettersLabel().setText(MainTextEditorView.AVAILABLE_LETTERS
                        +  textEditorModel.getAlphabet());
                textEditorModel.setFont(font);
                textView.repaint();

                setStatus("Current font is " + font);
            }
        });

        mainView.getMenuLoad().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFontDialog();

                String font = textEditorModel.addNewFont();
                if (font != null) {
                    mainView.getFontComboBox().addItem(font);
                }

                setStatus("Load new font ");
            }
        });

        mainView.getMenuLoadText().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> text = openTextDialog();
                if (text != null) {
                    textEditorModel.loadText(text);
                }
                textView.repaint();
                setStatus("Load text ");
            }
        });

        mainView.getMenuNew().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textEditorModel.clearLetters();
                textView.repaint();

                setStatus("New page ");
            }
        });

        mainView.getMenuSave().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textEditorModel.saveText(saveTextDialog());

                setStatus("Save text ");
            }
        });

    }

    void setStatus(String text) {
        mainView.setStatusBarText(MainTextEditorView.LAST_CHANGE + text);
    }

    @Override
    public void control() {
        AbstractAction keyAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setStatus("Key pressed \"" + e.getActionCommand() + "\"");
                textEditorModel.addLetter(e.getActionCommand());
                textView.repaint();
            }
        };

        AbstractAction backspace = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setStatus("Key pressed \"" + "Backspace" + "\"");
                textEditorModel.deleteLastLetter();
                textView.repaint();
            }
        };

        for (int i = 0x41; i <= 0x5A; i++) {
            textView.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(i, 0),
                    "keyAction");
        }
        textView.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0),
                "backspace");
        textView.getActionMap().put("backspace",
                backspace);
        textView.getActionMap().put("keyAction",
                keyAction);
    }
}
