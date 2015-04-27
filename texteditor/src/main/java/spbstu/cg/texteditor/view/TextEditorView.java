package spbstu.cg.texteditor.view;

import spbstu.cg.texteditor.model.TextEditorModel;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Created by JDima on 26/04/15.
 */
public class TextEditorView extends JComponent {
    private static final Stroke DASHED_STROKE = new BasicStroke(0.8f, BasicStroke.CAP_BUTT,
            BasicStroke.JOIN_BEVEL, 0, new float[]{5}, 0);
    private static final Stroke SIMPLE_SOLID_STROKE = new BasicStroke(1.0f, BasicStroke.CAP_SQUARE,
            BasicStroke.JOIN_MITER, 10.0f);

    private Rectangle bounds;
    private Graphics2D g2D;
    private TextEditorModel model;

    public TextEditorView(TextEditorModel textEditorModel) {
        this.model = textEditorModel;
        bounds = null;
        g2D = null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g2D = (Graphics2D) g;
        setupGraphics(g2D);
        g2D.setColor(Color.white);
        bounds = g2D.getClipBounds();
        g2D.fill(bounds);
        g2D.setColor(Color.black);
        System.out.println(bounds.height);

        int height = 10 * (int)model.getSize();
        int widht = 10 * (int)model.getSize();

        //TODO PAINT FONT
        int y = 0;
        int x = 0;
        for (int i = 0; i < model.getText().size(); i++) {
            if (x + 10 + widht> bounds.width) {
                if (y + 2 * (height + 10) > bounds.height) {
                    break;
                }
                y += height + 10;
                x = 0;
            }
            g2D.drawRect(x + 10, y + 10, widht, height);
            x +=  10 + widht;
        }
    }

    private void setupGraphics(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
    }

}
