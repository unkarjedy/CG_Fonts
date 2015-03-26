package spbstu.cg.fonteditor;

import spbstu.cg.fonteditor.controller.FontEditorController;
import spbstu.cg.fonteditor.model.LetterEditorModel;
import spbstu.cg.fonteditor.view.FontEditorView;

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
                FontEditorView editor = new FontEditorView();
                LetterEditorModel letterEditorModel = new LetterEditorModel();
                FontEditorController controller = new FontEditorController(editor, letterEditorModel);
                controller.control();
                editor.setVisible(true);
            }
        });
    }
}
