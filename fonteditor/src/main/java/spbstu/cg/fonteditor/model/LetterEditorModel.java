package spbstu.cg.fonteditor.model;

import spbstu.cg.fontcommons.font.*;
import spbstu.cg.fontcommons.spline.*;
import spbstu.cg.fontcommons.point.ControlPoint;
import spbstu.cg.fontcommons.point.HandlePoint;
import spbstu.cg.fontcommons.point.Point;
import spbstu.cg.fontcommons.point.PointType;
import spbstu.cg.fontcommons.point.PointUtils;
import spbstu.cg.fonteditor.Consts;

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
     *  allready created
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

    private void activateAndAddNewSpline() {
        currentSpline = new Spline();
        currentLetter.addSpline(currentSpline);
    }


    public LetterEditorModel() {
        currentLetter = new Letter("Letter", 100, 100); // TODO: delete hardcode
        activateAndAddNewSpline();
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

    public Point changeActivePointType(PointType newPointType) {
        if (activePoint == null || activeSpline == null)
            throw new NullPointerException();

        return activeSpline.changePointType(activePointIndex, newPointType);
    }

    public void addControlPoint(ControlPoint point) {
        currentSpline.addControlPoint(point);
    }

    /**
     * Tries to end currently edited spline. It's okay if
     * under cursor point is the first point of the spline.
     * @return true if spline ended
     */
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
            return true;
        }
        return false;
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

    public void activateUnderCursorPoint() {
        this.activePoint = underCursorPoint;
        activeSpline = getPointSpline(activePoint);
        activePointIndex = touchedControlPointIndex;
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

    private void setUnderCursorPoint(Point underCursorPoint) {
        this.underCursorPoint = underCursorPoint;
        if (underCursorPoint == null)
            touchedControlPointIndex = -1;
    }

    /**
     * Returns neares point (for now only Control Point) to given one
     */
    private Point findNearestPoint(float x, float y) {
        for (Spline spline : currentLetter.getSplines()) {
            touchedControlPointIndex = -1;
            int i = 0;
            for (ControlPoint point : spline) {
                touchedControlPointIndex = i++;
                if (PointUtils.getSquaredDist(point.getX(), point.getY(), x, y) < Consts.DISTANCE_EPS) {
                    return point;
                }
                if (point.getHandlePoints() != null) {
                    for (HandlePoint hp : point.getHandlePoints()) {
                        if (hp == null)
                            continue;
                        if (PointUtils.getSquaredDist(hp.getX(), hp.getY(), x, y) < Consts.DISTANCE_EPS) {
                            return hp;
                        }
                    }
                }
            }
        }
        return null;
    }

}
