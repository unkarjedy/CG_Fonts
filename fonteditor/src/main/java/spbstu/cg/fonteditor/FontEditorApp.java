package spbstu.cg.fonteditor;

import spbstu.cg.fonteditor.controller.ControlPanelController;
import spbstu.cg.fonteditor.controller.LetterEditorController;
import spbstu.cg.fonteditor.model.ControlPanelModel;
import spbstu.cg.fonteditor.model.LetterEditorModel;
import spbstu.cg.fonteditor.view.MainFontEditorView;

import java.awt.*;

/**
 * Created by Egor Gorbunov on 20.03.2015.
 * Email: egor-mailbox@ya.ru
 * Github username: egorbunov
 */
public class FontEditorApp {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Create main view, containing views of 3 components:
                // ProjectView, LetterEditorView, ControlPanelView
                MainFontEditorView editor = new MainFontEditorView();

                // Create model, view, controller for each of 3 component
                LetterEditorModel letterEditorModel = new LetterEditorModel();
                LetterEditorController letterController = new LetterEditorController(editor, letterEditorModel);

                ControlPanelModel controlPanelModel = new ControlPanelModel();
                ControlPanelController controlPanelController = new ControlPanelController(editor, controlPanelModel);
                controlPanelController.setControlPanelListener(letterController);

                letterController.control();
                controlPanelController.control();

                editor.setVisible(true);
            }
        });
    }
}
