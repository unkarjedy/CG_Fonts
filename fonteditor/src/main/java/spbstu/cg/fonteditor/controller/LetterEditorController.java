package spbstu.cg.fonteditor.controller;

import spbstu.cg.fontcommons.point.ControlPoint;
import spbstu.cg.fontcommons.point.Point;
import spbstu.cg.fontcommons.point.PointType;
import spbstu.cg.fontcommons.point.SymmetricControlPoint;
import spbstu.cg.fonteditor.model.LetterEditorModel;
import spbstu.cg.fonteditor.view.ControlPanelListener;
import spbstu.cg.fonteditor.view.ControlPanelView;
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
    LetterEditorView letterEditorView;

    ControlPanelView controlPanelView;

    public LetterEditorController(MainFontEditorView view, LetterEditorModel model) {
        this.mainView = view;
        letterEditorModel = model;
        letterEditorView = mainView.getLetterEditor();
        controlPanelView = mainView.getControlPanel();
    }

    public void control() {
        letterEditorView.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point touchedPoint = letterEditorModel.setCurrentCursorPos(e.getX(), e.getY());

                if (SwingUtilities.isLeftMouseButton(e)) {
                    if(touchedPoint != null) {
                        letterEditorModel.activateUnderCursorPoint();
                        letterEditorView.setActivePoint(touchedPoint);
                        letterEditorView.repaint();

                        if(touchedPoint.getType().isControlPointType()){
                            controlPanelView.enablePointTypesBox(true);
                            controlPanelView.setPointType(touchedPoint.getType());
                        } else {
                            controlPanelView.enablePointTypesBox(false);
                        }
                    } else {
                        // creating new control point
                        ControlPoint point = new SymmetricControlPoint(e.getX(), e.getY());
                        letterEditorModel.addControlPoint(point);

                        letterEditorView.setPointUnderCursor(point);
                        letterEditorView.setSplines(letterEditorModel.getSplines());
                        letterEditorView.repaint();
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

        letterEditorView.addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {
                if (letterEditorModel.moveUnderCursorPointTo(e.getX(), e.getY()))
                    letterEditorView.repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                Point prevPoint = letterEditorModel.getUnderCursorPoint();
                Point cur = letterEditorModel.setCurrentCursorPos(e.getX(), e.getY());
                if (cur != prevPoint) {
                    letterEditorView.setPointUnderCursor(cur);
                    letterEditorView.repaint();
                }
            }
        });
    }

    @Override
    public void pointTypeChanged(PointType newType) {
        System.out.println("Point type changed to: " + newType.getName());
    }
}
