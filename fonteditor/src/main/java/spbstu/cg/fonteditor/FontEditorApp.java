package spbstu.cg.fonteditor;

import spbstu.cg.fonteditor.controller.ControlPanelController;
import spbstu.cg.fonteditor.controller.FontProjectController;
import spbstu.cg.fonteditor.controller.LetterEditorController;
import spbstu.cg.fonteditor.model.action.ActionStack;
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

                ActionStack actionStack = new ActionStack();

                // Create model, view, controller for each of 3 component
                LetterEditorController letterController = new LetterEditorController(editor);

                ControlPanelController controlPanelController = new ControlPanelController(editor);
                controlPanelController.setControlPanelListener(letterController);

                FontProjectController projectController = new FontProjectController(editor, letterController);
                controlPanelController.control();
                projectController.control();

                editor.setVisible(true);
            }
        });
    }
}
