package spbstu.cg.fonteditor.controller;

import spbstu.cg.fontcommons.point.*;
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
    private AbstractAction undoAction;
    private AbstractAction redoAction;
    private MainFontEditorView mainView;
    private LetterEditorModel letterEditorModel;
    private LetterEditorView letterEditorView;

    private ControlPanelView controlPanelView;

    public int pressedButton;

    int w, h;

    private final MouseListener mouseListener;
    private final MouseMotionListener mouseMotionListener;

    public LetterEditorController(MainFontEditorView view) {
        this.mainView = view;
        letterEditorView = mainView.getLetterEditor();
        controlPanelView = mainView.getControlPanel();
        letterEditorView.addComponentListener(this);
        letterEditorView.setEnabled(false);


        mouseListener = new MouseListener() {
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

                            controlPanelView.getSplineTypeCheckbox()
                                    .setSelected(letterEditorModel.getActiveSpline().isExternal());
                            controlPanelView.getSplineTypeCheckbox().setEnabled(true);
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
        };
        mouseMotionListener = new MouseMotionListener() {

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
        };

        undoAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                letterEditorModel.undo();
                letterEditorView.repaint();
                controlPanelView.repaint();
            }
        };

        redoAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                letterEditorModel.redo();
                letterEditorView.repaint();
                controlPanelView.repaint();
            }
        };


    }

    public void setModel(LetterEditorModel model) {
        letterEditorModel = model;
        letterEditorModel.setViewSize(w, h);
        letterEditorModel.setListener(this);
        letterEditorView.setBoundingBox(letterEditorModel.getBoundingBox());


        controlPanelView.enablePointTypesBox(false);
        controlPanelView.enableWeightSlider(false);
        controlPanelView.enableCurrentSplineType(false);


        letterEditorView.setSplines(letterEditorModel.getSplines());
        letterEditorView.setBoundingBox(letterEditorModel.getBoundingBox());
        letterEditorView.setActivePoint(null);
        letterEditorView.setPointUnderCursor(null);

        letterEditorView.repaint();
    }

    public void control() {
        letterEditorView.addMouseListener(mouseListener);
        letterEditorView.addMouseMotionListener(mouseMotionListener);


        // TODO: It must not be mainView.getProjectPanel(), but list eats all focus
        mainView.getProjectPanel().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_Z,
                        InputEvent.CTRL_DOWN_MASK), "undoAction");
        mainView.getProjectPanel().getActionMap().put("undoAction", undoAction);

        mainView.getProjectPanel().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_Y,
                        InputEvent.CTRL_DOWN_MASK), "redoAction");
        mainView.getProjectPanel().getActionMap().put("redoAction", redoAction);
    }

    public void stopControl() {
        letterEditorView.removeMouseListener(mouseListener);
        letterEditorView.removeMouseMotionListener(mouseMotionListener);
    }

    @Override
    public void pointTypeChanged(PointType newType) {
        letterEditorModel.changeActivePointType(newType); // point is still active
    }

    @Override
    public void pointWeightChanged(float weight) {
        letterEditorModel.changeActivePointWeight(weight);
    }

    @Override
    public void splineTypeChanged(boolean selected) {
        letterEditorModel.setActiveSplineType(selected);
    }

    @Override
    public void drawLetterChanged(boolean drawLetter) {
        letterEditorView.setDrawLetter(drawLetter);
    }

    public void activePointChanged(Point activePoint) {
        letterEditorView.setActivePoint(activePoint);
        if (activePoint == null) {
            controlPanelView.enablePointTypesBox(false);
            controlPanelView.enableWeightSlider(false);
            controlPanelView.enableCurrentSplineType(false);

        }
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
    public void pointWeightChanged(Point point) {
        if (point == null) {
            controlPanelView.enableWeightSlider(false);
        } else {
            controlPanelView.enableWeightSlider(true);
            controlPanelView.setSliderWeight(point.getWeight());
        }
    }

    @Override
    public void componentResized(ComponentEvent e) {
        w = e.getComponent().getWidth();
        h = e.getComponent().getHeight();
        if (letterEditorModel != null) {
            letterEditorModel.setViewSize(w,
                    h);
        }
    }

    @Override
    public void componentMoved(ComponentEvent e) {}

    @Override
    public void componentShown(ComponentEvent e) {}

    @Override
    public void componentHidden(ComponentEvent e) {}
}
