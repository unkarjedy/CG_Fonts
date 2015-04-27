package spbstu.cg.fonteditor.controller;

import spbstu.cg.fontcommons.point.*;
import spbstu.cg.fonteditor.model.BoundingBox;
import spbstu.cg.fonteditor.model.action.ActionStack;
import spbstu.cg.fonteditor.model.LetterEditorModel;
import spbstu.cg.fonteditor.view.ControlPanelListener;
import spbstu.cg.fonteditor.view.ControlPanelView;
import spbstu.cg.fonteditor.view.MainFontEditorView;
import spbstu.cg.fonteditor.view.LetterEditorView;

import javax.swing.*;
import java.awt.event.*;

/**
 * Created by Egor on 20.03.2015.
 * Email: egor-mailbox@ya.ru
 * Github username: egorbunov
 * <p/>
 * Main app controller.
 */
public class LetterEditorController extends Controller implements ControlPanelListener, LetterEditorModelListener, ComponentListener {
    private MainFontEditorView mainView;
    private LetterEditorModel letterEditorModel;
    private LetterEditorView letterEditorView;

    private ControlPanelView controlPanelView;

    private final ActionStack actionStack;

    public int pressedButton;


    public LetterEditorController(MainFontEditorView view, LetterEditorModel model, ActionStack actionStack) {
        this.mainView = view;
        letterEditorModel = model;
        this.actionStack = actionStack;
        letterEditorView = mainView.getLetterEditor();
        controlPanelView = mainView.getControlPanel();

        letterEditorView.setBoundingBox(letterEditorModel.getBoundingBox());
        letterEditorModel.setListener(this);
        letterEditorModel.setActionStack(actionStack);

        letterEditorView.addComponentListener(this);

    }

    public void control() {
        letterEditorView.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (letterEditorModel.isUnderCursorPointMoving()) {
                    letterEditorModel.endMovingUnderCursorPoint();
                    return;
                }

                Point touchedPoint = letterEditorModel.setCurrentCursorPos(e.getX(), e.getY());

                
                if (SwingUtilities.isLeftMouseButton(e)) {
                    if (touchedPoint != null) {
                        if (touchedPoint.getType().isControlPointType()) {
                            letterEditorModel.activateUnderCursorPoint();

                            letterEditorView.setActivePoint(touchedPoint);
                            controlPanelView.enablePointTypesBox(true);
                            controlPanelView.getPointWeightSlider().setEnabled(true);
                            controlPanelView.setPointType(touchedPoint.getType());
                            controlPanelView.setSliderWeight(touchedPoint.getWeight());
                        }


                    } else {
                        letterEditorModel.addControlPointAt(e.getX(), e.getY());
                        letterEditorView.setSplines(letterEditorModel.getSplines());
                    }
                }

                // right release
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

                    if (letterEditorModel.getUnderCursorPoint() != null)
                        letterEditorModel.startMovingUnderCursorPoint();

                    if (letterEditorModel.moveUnderCursorPointTo(e.getX(), e.getY())) {
                        letterEditorView.repaint();
                    }
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


        AbstractAction undoAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                letterEditorModel.undo();
                letterEditorView.repaint();
                controlPanelView.repaint();
            }
        };

        AbstractAction redoAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                letterEditorModel.redo();
                letterEditorView.repaint();
                controlPanelView.repaint();
            }
        };

        letterEditorView.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK),
                "undoAction");
        letterEditorView.getActionMap().put("undoAction",
                undoAction);

        letterEditorView.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_Y,
                        InputEvent.CTRL_DOWN_MASK),
                "redoAction");
        letterEditorView.getActionMap().put("redoAction",
                redoAction);
    }

    @Override
    public void pointTypeChanged(PointType newType) {
        letterEditorModel.changeActivePointType(newType); // point is still active
    }

    @Override

    public void pointWeightChanged(float weight) {
        letterEditorModel.changeActivePointWeight(weight);
    }

    public void activePointChanged(Point activePoint) {
        letterEditorView.setActivePoint(activePoint);
    }

    @Override
    public void controlPointTypeChanged(ControlPoint controlPoint) {
        if (controlPoint == null) {
            controlPanelView.enablePointTypesBox(false);
        } else {
            controlPanelView.enablePointTypesBox(true);
            controlPanelView.setPointType(controlPoint.getType());
        }
    }

    @Override
    public void componentResized(ComponentEvent e) {
        letterEditorModel.setViewSize(e.getComponent().getWidth(),
                e.getComponent().getHeight());
    }

    @Override
    public void componentMoved(ComponentEvent e) {}

    @Override
    public void componentShown(ComponentEvent e) {}

    @Override
    public void componentHidden(ComponentEvent e) {}
}
