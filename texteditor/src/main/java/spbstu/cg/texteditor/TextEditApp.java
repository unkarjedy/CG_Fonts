package spbstu.cg.texteditor;

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
                MainTextEditorView textEditorView = new MainTextEditorView();

                textEditorView.setVisible(true);
            }
        });
    }
}
