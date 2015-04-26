package spbstu.cg.fonteditor.model;

import spbstu.cg.fontcommons.font.*;
import spbstu.cg.fontcommons.spline.*;
import spbstu.cg.fontcommons.point.ControlPoint;
import spbstu.cg.fontcommons.point.HandlePoint;
import spbstu.cg.fontcommons.point.Point;
import spbstu.cg.fontcommons.point.PointType;
import spbstu.cg.fontcommons.point.PointUtils;
import spbstu.cg.fonteditor.Constants;
import spbstu.cg.fonteditor.controller.LetterEditorController;
import spbstu.cg.fonteditor.controller.LetterEditorModelListener;
import spbstu.cg.fonteditor.model.action.*;

import javax.swing.*;
import java.util.List;

/**
 * Created by Egor on 06.03.2015.
 * Email: egor_mailbox@ya.ru
 * Github username: egorbunov
 *
 * Model, which stores and manipulates all data connected to currently edited letter
 *
 * TODO: add constructor
 */
public class LetterEditorModel {

    /**
     * Active letter for editing
     */
    private Letter currentLetter;

    /**
     * Not yet completed spline, but it already added to currentLetter! (it just the last one
     * in the list of splines of the currentLetter)
     */
    private Spline currentSpline;

    /**
     *  Spline of active point. It can be any spline including
     *  already created
     */
    private Spline activeSpline;

    /**
     * Currently active point and it's index in current spline
     */
    private Point activePoint = null;
    private int activePointIndex = -1;

    /**
     * Point under cursor
     */
    private Point underCursorPoint = null;
    private int touchedControlPointIndex = -1; // TODO: I don't like this

    /**
     * Last move vector
     */
    private float lastDx, lastDy;

    private boolean isUnderCursorPointMoving;

    private ActionStack actionStack;

    private LetterEditorModelListener modelListener;



    private void activateAndAddNewSpline() {
        currentSpline = new Spline();
        currentLetter.addSpline(currentSpline);
    }

    /**
     * Returns nearest point (for now only Control Point) to given one
     */
    private Point findNearestPoint(float x, float y) {
        for (Spline spline : currentLetter.getSplines()) {
            touchedControlPointIndex = -1;
            int i = 0;
            for (ControlPoint point : spline) {
                touchedControlPointIndex = i++;
                if (PointUtils.getSquaredDist(point.getX(), point.getY(), x, y) < Constants.DISTANCE_EPS) {
                    return point;
                }
                if (point.getHandlePoints() != null) {
                    for (HandlePoint hp : point.getHandlePoints()) {
                        if (hp == null)
                            continue;
                        if (PointUtils.getSquaredDist(hp.getX(), hp.getY(), x, y) < Constants.DISTANCE_EPS) {
                            return hp;
                        }
                    }
                }
            }
        }
        return null;
    }

    private void addControlPoint(ControlPoint point) {
        currentSpline.addControlPoint(point);
        this.setUnderCursorPoint(point);
    }

    private Spline getPointSpline(Point point) {
        for(Spline spline : currentLetter.getSplines()){
            for(ControlPoint cp : spline.getControlPoints()) {
                if(cp.equals(point))
                    return spline;
            }
        }
        return null;
    }

    private int getPointIndex(Point point, Spline spline) {
        int ind = 0;
        for(ControlPoint cp : spline.getControlPoints()) {
            if(cp.equals(point))
                return ind;
            ind += 1;
        }
        return -1;
    }

    private void setUnderCursorPoint(Point underCursorPoint) {
        this.underCursorPoint = underCursorPoint;
        if (underCursorPoint == null)
            touchedControlPointIndex = -1;
    }


    public LetterEditorModel() {
        currentLetter = new Letter("Letter", 100, 100); // TODO: delete hardcode
        activateAndAddNewSpline();
    }

    /**
     * That is the helping method for undo/redo. {@link FinishSplineAction};
     */
    public void deleteLastSplineAndActivatePrev() {
        currentLetter.getSplines().remove(currentLetter.getSplines().size() - 1);
        currentSpline = currentLetter.getSplines().get(currentLetter.getSplines().size() - 1);
    }

    public Spline getCurrentSpline() {
        return currentSpline;
    }

    /**
     * Moves point underCursorPoint (which is supposed to reference the point, which is
     * under users cursor) to new coordinates.
     * @return true if underCursorPoint is not null (so the cursor is really points on some point)
     *         false else
     */
    public boolean moveUnderCursorPointTo(float x, float y) {
        if (underCursorPoint == null)
            return false;
        underCursorPoint.move(x - underCursorPoint.getX(), y - underCursorPoint.getY());
        return true;
    }



    /**
     * Finds nearest point to cursor, returns true if it exists and
     * mark that point as a underCursorPoint.
     */
    public Point setCurrentCursorPos(float x, float y) {
        Point p = findNearestPoint(x, y);
        setUnderCursorPoint(p);
        return p;
    }

    public Point getUnderCursorPoint() {
        return underCursorPoint;
    }

    public List<Spline> getSplines() {
        return currentLetter.getSplines();
    }

    public void activatePoint(Point point) {
        if (point == null) {
            activePoint = null;
            activeSpline = null;
        }

        Spline spline = getPointSpline(point);
        if (spline != null) {
            activeSpline = spline;
            activePoint = point;
            activePointIndex = getPointIndex(point, spline);
        }

        modelListener.activePointChanged(point);

        if (point == null || point instanceof ControlPoint) {
            modelListener.controlPointTypeChanged((ControlPoint) point);
        }
    }

    public void activateUnderCursorPoint() {
        this.activePoint = underCursorPoint;
        activeSpline = getPointSpline(activePoint);
        activePointIndex = touchedControlPointIndex;

        modelListener.activePointChanged(underCursorPoint);

        if (underCursorPoint == null || underCursorPoint instanceof ControlPoint) {
            modelListener.controlPointTypeChanged((ControlPoint) underCursorPoint);
        }

    }

    public void startMovingUnderCursorPoint() {
        if (!isUnderCursorPointMoving) {
            isUnderCursorPointMoving = true;
            lastDx = underCursorPoint.getX();
            lastDy = underCursorPoint.getY();
        }
    }

    public boolean isUnderCursorPointMoving() {
        return isUnderCursorPointMoving;
    }

    /**
     * Tries to end currently edited spline. It's okay if
     * under cursor point is the first point of the spline.
     * @return true if spline ended
     */
    @RedoUndo
    public boolean endCurrentSpline() {
        if (currentSpline.getControlPoints() == null) {
            return false;
        }
        if (currentSpline.getControlPoints().size() == 0) {
            return false;
        }

        if (underCursorPoint == currentSpline.getControlPoints().get(0)) {
            currentSpline.addControlPoint(currentSpline.getControlPoints().get(0));
            currentSpline = null;


            activateAndAddNewSpline();

            actionStack.addAction(new FinishSplineAction(this));

            return true;
        }
        return false;
    }

    @RedoUndo
    public void endMovingUnderCursorPoint() {
        if (isUnderCursorPointMoving) {
            isUnderCursorPointMoving = false;
            lastDx = underCursorPoint.getX() - lastDx;
            lastDy = underCursorPoint.getY() - lastDy;

            actionStack.addAction(new PointMoveAction(underCursorPoint, lastDx, lastDy));
        }
    }

    @RedoUndo
    public void changeActivePointType(PointType newPointType) {
        if (activePoint == null || activeSpline == null)
            throw new NullPointerException();

        if (!activePoint.getType().equals(newPointType)) {
            actionStack.addAction(new ChangeTypeAction(modelListener, activeSpline,
                    activePointIndex, activePoint.getType(), newPointType));

            activeSpline.changePointType(activePointIndex, newPointType);

            modelListener.controlPointTypeChanged((ControlPoint) activePoint);
        }
    }

    @RedoUndo
    public void addControlPointAt(int x, int y) {
        ControlPoint point = new ControlPoint(x, y);
        addControlPoint(point);

        actionStack.addAction(new AddLastAction(this, currentSpline, point));
    }

    public void undo() {
        actionStack.undo();
    }

    public void redo() {
        actionStack.redo();
    }

    public void setListener(LetterEditorModelListener listener) {
        modelListener = listener;
    }

    public void changeActivePointWeight(float weight) {
        if (activePoint == null || activeSpline == null)
            throw new NullPointerException();

        activeSpline.changePointWeight(activePointIndex, weight);
    }

    public void setActionStack(ActionStack actionStack) {
        this.actionStack = actionStack;
    }
}
