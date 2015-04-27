package spbstu.cg.fonteditor.controller;

import spbstu.cg.fonteditor.model.FontProjectModel;
import spbstu.cg.fonteditor.model.LetterEditorModel;
import spbstu.cg.fonteditor.view.MainFontEditorView;
import spbstu.cg.fonteditor.view.ProjectPanelView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Created by Egor Gorbunov on 28.04.2015.
 * Email: egor-mailbox@ya.ru
 * Github username: egorbunov
 */
public class FontProjectController extends Controller{
    private MainFontEditorView mainView;
    private LetterEditorController letterEditorController;
    private FontProjectModel fontProjectModel;
    private ProjectPanelView projectView;

    public FontProjectController(MainFontEditorView view, LetterEditorController letterEditorController) {
        mainView = view;
        this.letterEditorController = letterEditorController;
    }

    @Override
    public void control() {
        JButton newLetterButton = mainView.getNewLetterButton();
        JButton newFontButton = mainView.getNewFontButton();
        projectView = mainView.getProjectPanel();

        newFontButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Enter font name dialog");
                String fontName = JOptionPane.showInputDialog(frame, "Input new font name");
                if (fontName.length() == 0) {
                    JOptionPane.showMessageDialog(frame, "Empty font name!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    mainView.setStatusBarMessage("Last action: new font created...");
                    fontProjectModel = new FontProjectModel(fontName);
                    projectView.setProjectName(fontName);
                    projectView.getList().setEnabled(true);
                }
            }
        });

        newLetterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fontProjectModel != null) {
                    JFrame frame = new JFrame("Enter letter dialog");
                    String letter = JOptionPane.showInputDialog(frame, "Input new letter character");
                    if (letter.length() != 1) {
                        JOptionPane.showMessageDialog(frame, "That is not a letter!", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        mainView.setStatusBarMessage("Last action: new letter created...");
                        fontProjectModel.addNewLetter(letter.charAt(0));
                        projectView.getListModel().addElement(letter.charAt(0));
                    }
                } else {
                    JOptionPane.showMessageDialog(new JFrame(), "Create new font project first!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        projectView.getList().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int index = projectView.getList().getSelectedIndex();
                LetterEditorModel model = fontProjectModel.getLetterEditorModel(index);
                letterEditorController.stopControl();
                letterEditorController.setModel(model);
                letterEditorController.control();
            }
        });
    }
}
