package spbstu.cg.fonteditor.controller;

import spbstu.cg.fontcommons.point.ControlPoint;
import spbstu.cg.fontcommons.point.CornerControlPoint;
import spbstu.cg.fontcommons.point.Point;
import spbstu.cg.fonteditor.model.LetterEditorModel;
import spbstu.cg.fonteditor.view.FontEditorView;
import spbstu.cg.fonteditor.view.LetterEditorView;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Created by Egor on 20.03.2015.
 * Email: egor-mailbox@ya.ru
 * Github username: egorbunov
 */
public class FontEditorController {
    FontEditorView view;
    LetterEditorModel letterEditorModel;

    public FontEditorController(FontEditorView view, LetterEditorModel model) {
        this.view = view;
        letterEditorModel = model;
    }

    public void control() {
        final LetterEditorView letterEditor = view.getLetterEditor();

        letterEditor.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ControlPoint p = new CornerControlPoint(e.getX(), e.getY());
                boolean isActivated = letterEditorModel.activatePoint(p);
                if (!isActivated) {
                    letterEditorModel.addControlPoint(p);
                    view.getLetterEditor().drawPoint(p);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        letterEditor.addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                boolean needReDraw = letterEditorModel.getUnderCursorPoint() != null;
                boolean isPoint = letterEditorModel.setCurrentCursorPos(new Point(e.getX(), e.getY()));
                if (isPoint) {
                    view.getLetterEditor().activate(letterEditorModel.getUnderCursorPoint());
                }
                needReDraw = needReDraw != isPoint;

                if (needReDraw)
                    view.getLetterEditor().reDrawSplines(letterEditorModel.getSplines());
            }
        });
    }

}
