package spbstu.cg.fonteditor.model;

import spbstu.cg.font.Letter;
import spbstu.cg.fontcommons.*;
import spbstu.cg.fontcommons.point.ControlPoint;
import spbstu.cg.fontcommons.point.HandlePoint;
import spbstu.cg.fontcommons.point.Point;
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
        activeSpline = new Spline();
        currentLetter.addSpline(activeSpline);
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

    public void changeActivePointType(Class<? extends ControlPoint> newPointType) {
        if (activePoint == null)
            throw new NullPointerException();

        activeSpline.changePointType(activePointIndex, newPointType);
    }

    public void addControlPoint(ControlPoint point) {
        activeSpline.addControlPoint(point);
    }

    /**
     * Tries to end currently edited spline. It's okay if
     * under cursor point is the first point of the spline.
     * @return true if spline ended
     */
    public boolean endCurrentSpline() {
        if (activeSpline.getControlPoints() == null) {
            return false;
        }
        if (activeSpline.getControlPoints().size() == 0) {
            return false;
        }

        if (underCursorPoint == activeSpline.getControlPoints().get(0)) {
            activeSpline.addControlPoint(activeSpline.getControlPoints().get(0));
            activeSpline = null;
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
        activePointIndex = touchedControlPointIndex;
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
