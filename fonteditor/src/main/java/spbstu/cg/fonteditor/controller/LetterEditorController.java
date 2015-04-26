package spbstu.cg.fonteditor.controller;

import spbstu.cg.fontcommons.point.*;
import spbstu.cg.fonteditor.model.LetterEditorModel;
import spbstu.cg.fonteditor.view.ControlPanelListener;
import spbstu.cg.fonteditor.view.ControlPanelView;
import spbstu.cg.fonteditor.view.MainFontEditorView;
import spbstu.cg.fonteditor.view.LetterEditorView;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Created by Egor on 20.03.2015.
 * Email: egor-mailbox@ya.ru
 * Github username: egorbunov
 * <p/>
 * Main app controller.
 */
public class LetterEditorController extends Controller implements ControlPanelListener {
    private MainFontEditorView mainView;
    private LetterEditorModel letterEditorModel;
    private LetterEditorView letterEditorView;

    private ControlPanelView controlPanelView;

    public int pressedButton;

    public LetterEditorController(MainFontEditorView view, LetterEditorModel model) {
        this.mainView = view;
        letterEditorModel = model;
        letterEditorView = mainView.getLetterEditor();
        controlPanelView = mainView.getControlPanel();
    }

    public void control() {
        letterEditorView.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
                Point touchedPoint = letterEditorModel.setCurrentCursorPos(e.getX(), e.getY());
                if (SwingUtilities.isLeftMouseButton(e)) {
                    if (touchedPoint != null) {
                        if (touchedPoint.getType().isControlPointType()) {
                            letterEditorModel.activateUnderCursorPoint();
                            letterEditorView.setActivePoint(touchedPoint);
                            controlPanelView.enablePointTypesBox(true);
                            controlPanelView.setPointType(touchedPoint.getType());
                        }
                    } else {
                        // creating new control point
                        ControlPoint point = new ControlPoint(e.getX(), e.getY());
                        //ControlPoint point = new SmoothControlPoint(e.getX(), e.getY());
                        letterEditorModel.addControlPoint(point);

                        letterEditorView.setPointUnderCursor(point);
                        letterEditorView.setSplines(letterEditorModel.getSplines());
                    }
                }

                if (SwingUtilities.isRightMouseButton(e)) {
                    if (touchedPoint != null) {
                        if (letterEditorModel.endCurrentSpline()) {
                            mainView.setStatusBarMessage("Spline ended...");
                        } else {
                            mainView.setStatusBarMessage("Can't end current spline in that point!");
                        }
                    }
                }

                letterEditorView.repaint();
                pressedButton = -1;
            }

            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                pressedButton = e.getButton();
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
                if (pressedButton == MouseEvent.BUTTON1) {
                    if (letterEditorModel.moveUnderCursorPointTo(e.getX(), e.getY()))
                        letterEditorView.repaint();
                } else {
                    mouseMoved(e);
                }
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
        letterEditorModel.changeActivePointType(newType); // point is still active
    }
}
