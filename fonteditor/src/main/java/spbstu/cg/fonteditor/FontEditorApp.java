package spbstu.cg.fonteditor;

import spbstu.cg.fontcommons.utils.Logger;
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
                final MainFontEditorView editor = new MainFontEditorView();

                ActionStack actionStack = new ActionStack();

                Logger logger = new Logger() {
                    @Override
                    public void log(String s) {
                        editor.setStatusBarMessage(s);
                        System.out.println(s);
                    }
                };

                // Create model, view, controller for each of 3 component
                LetterEditorController letterController = new LetterEditorController(editor, logger);

                ControlPanelController controlPanelController = new ControlPanelController(editor, logger);
                controlPanelController.setControlPanelListener(letterController);



                FontProjectController projectController = new FontProjectController(editor, letterController, logger);
                controlPanelController.control();
                projectController.control();

                editor.setVisible(true);
            }
        });
    }
}
