package spbstu.cg.fonteditor.controller;

import spbstu.cg.fontcommons.point.ControlPoint;
import spbstu.cg.fontcommons.point.CurveControlPoint;
import spbstu.cg.fontcommons.point.Point;
import spbstu.cg.fonteditor.model.LetterEditorModel;
import spbstu.cg.fonteditor.view.ControlPanelListener;
import spbstu.cg.fonteditor.view.FontEditorView;
import spbstu.cg.fonteditor.view.LetterEditorView;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Created by Egor on 20.03.2015.
 * Email: egor-mailbox@ya.ru
 * Github username: egorbunov
 *
 * Main app controller.
 */
public class FontEditorController implements ControlPanelListener {
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
                Point touchedPoint = letterEditorModel.setCurrentCursorPos(e.getX(), e.getY());
                if (touchedPoint == null && SwingUtilities.isLeftMouseButton(e)) {
                    // creating new control point
                    ControlPoint point = new CurveControlPoint(e.getX(), e.getY());
                    letterEditorModel.addControlPoint(point);

                    view.getLetterEditor().setPointUnderCursor(point);
                    view.getLetterEditor().setSplines(letterEditorModel.getSplines());
                    view.getLetterEditor().repaint();
                } else {
                    if (SwingUtilities.isRightMouseButton(e)) {
                        if (letterEditorModel.endCurrentSpline()) {
                            view.setStatusBarMessage("Spline ended...");
                        } else {
                            view.setStatusBarMessage("Can't end current spline in that point!");
                        }
                    } else if (SwingUtilities.isLeftMouseButton(e)) {
                        letterEditorModel.activateUnderCursorPoint();
                        view.getLetterEditor().setActivePoint(touchedPoint);
                        view.getLetterEditor().repaint();
                    }
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
                if (letterEditorModel.moveUnderCursorPointTo(e.getX(), e.getY()))
                    view.getLetterEditor().repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                Point prevPoint = letterEditorModel.getUnderCursorPoint();
                Point cur = letterEditorModel.setCurrentCursorPos(e.getX(), e.getY());
                if (cur != prevPoint) {
                    view.getLetterEditor().setPointUnderCursor(cur);
                    view.getLetterEditor().repaint();
                }
            }
        });
    }

    @Override
    public void pointTypeChanged(Class<? extends Point> newType) {

    }
}
