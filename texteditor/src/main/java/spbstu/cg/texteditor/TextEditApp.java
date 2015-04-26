package spbstu.cg.texteditor;

import spbstu.cg.texteditor.controller.TextEditorController;
import spbstu.cg.texteditor.model.TextEditorModel;
import spbstu.cg.texteditor.view.MainTextEditorView;

import java.awt.*;

/**
 * Created by JDima on 26/04/15.
 */
public class TextEditApp {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                TextEditorModel textEditorModel = new TextEditorModel();

                MainTextEditorView editor = new MainTextEditorView(textEditorModel);

                TextEditorController textEditorController = new TextEditorController(editor, textEditorModel);
                textEditorController.control();

                editor.setVisible(true);
            }
        });
    }
}
