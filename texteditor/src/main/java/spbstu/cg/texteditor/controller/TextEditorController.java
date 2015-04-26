package spbstu.cg.texteditor.controller;

import spbstu.cg.texteditor.model.TextEditorModel;
import spbstu.cg.texteditor.view.MainTextEditorView;
import spbstu.cg.texteditor.view.TextEditorView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

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

    private void initViewListeners() {
        mainView.getSizeComboBox().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                float size = Float.valueOf((String) mainView.getSizeComboBox().getSelectedItem());
                textEditorModel.setSize(size);
                textView.repaint();;
                setStatus("New letter size is " + size);
            }
        });

        mainView.getFontComboBox().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String font = (String)mainView.getFontComboBox().getSelectedItem();
                mainView.getAvalaibleLettersLabel().setText(MainTextEditorView.AVAILABLE_LETTERS + "A B C D ...");
                textEditorModel.setFont(font);
                textView.repaint();

                setStatus("New font is " + font);
            }
        });

        mainView.getMenuLoad().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textEditorModel.addNewFont();

                setStatus("Load font");
            }
        });

        mainView.getMenuLoadText().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textEditorModel.loadText();

                setStatus("Load text");
            }
        });

        mainView.getMenuNew().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textEditorModel.clearLetters();
                textView.repaint();

                setStatus("new page");
            }
        });

        mainView.getMenuSave().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textEditorModel.saveText();

                setStatus("Save text");
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
