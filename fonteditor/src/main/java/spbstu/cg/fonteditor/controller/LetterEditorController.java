package spbstu.cg.fonteditor.controller;

import spbstu.cg.fontcommons.point.ControlPoint;
import spbstu.cg.fontcommons.point.Point;
import spbstu.cg.fontcommons.point.SymmetricControlPoint;
import spbstu.cg.fonteditor.model.LetterEditorModel;
import spbstu.cg.fonteditor.view.ControlPanelListener;
import spbstu.cg.fonteditor.view.MainFontEditorView;
import spbstu.cg.fonteditor.view.LetterEditorView;

import javax.sound.sampled.Control;
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
public class LetterEditorController extends Controller implements ControlPanelListener {
    MainFontEditorView mainView;
    LetterEditorModel letterEditorModel;

    public LetterEditorController(MainFontEditorView view, LetterEditorModel model) {
        this.mainView = view;
        letterEditorModel = model;
    }

    public void control() {
        final LetterEditorView letterEditor = mainView.getLetterEditor();

        letterEditor.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point touchedPoint = letterEditorModel.setCurrentCursorPos(e.getX(), e.getY());

                if (SwingUtilities.isLeftMouseButton(e)) {
                    if(touchedPoint != null) {
                        letterEditorModel.activateUnderCursorPoint();
                        mainView.getLetterEditor().setActivePoint(touchedPoint);
                        mainView.getLetterEditor().repaint();
                    } else {
                        // creating new control point
                        ControlPoint point = new SymmetricControlPoint(e.getX(), e.getY());
                        letterEditorModel.addControlPoint(point);

                        mainView.getLetterEditor().setPointUnderCursor(point);
                        mainView.getLetterEditor().setSplines(letterEditorModel.getSplines());
                        mainView.getLetterEditor().repaint();
                    }
                }

                if (SwingUtilities.isRightMouseButton(e)) {
                    if(touchedPoint != null) {
                        if (letterEditorModel.endCurrentSpline()) {
                            mainView.setStatusBarMessage("Spline ended...");
                        } else {
                            mainView.setStatusBarMessage("Can't end current spline in that point!");
                        }
                    } else {

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
                    mainView.getLetterEditor().repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                Point prevPoint = letterEditorModel.getUnderCursorPoint();
                Point cur = letterEditorModel.setCurrentCursorPos(e.getX(), e.getY());
                if (cur != prevPoint) {
                    mainView.getLetterEditor().setPointUnderCursor(cur);
                    mainView.getLetterEditor().repaint();
                }
            }
        });
    }

    @Override
    public void pointTypeChanged(Class<? extends Point> newType) {

    }
}
