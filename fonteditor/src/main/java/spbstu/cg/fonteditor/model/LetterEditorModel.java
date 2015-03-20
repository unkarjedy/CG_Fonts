package spbstu.cg.fonteditor.model;

import spbstu.cg.font.Letter;
import spbstu.cg.fontcommons.*;
import spbstu.cg.fontcommons.point.ControlPoint;
import spbstu.cg.fontcommons.point.Point;

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
     * Not yet completed spline
     */
    private Spline activeSpline;

    /**
     * Currently active point
     */
    private Point activePoint = null;

    /**
     * Point under cursor
     */
    private Point underCursorPoint = null;


    public LetterEditorModel() {
        currentLetter = new Letter("Letter", 100, 100); // TODO: delete hardcode
        activeSpline = new Spline();
        currentLetter.addSpline(activeSpline);
    }

    public void moveActivePoint(Point moveVector) {
        if (activePoint == null)
            return;

        activePoint.move(moveVector);
    }

    /**
     * Returns neares point (for now only Control Point) to given one
     */
    public Point findNearestPoint(Point p) {
        for (ControlPoint point : activeSpline) {
            if (PointUtils.getSquaredDist(point, p) < 10.0) {
                return point;
            }
        }
        return null;
    }

    /**
     * Method, which tries to find a point near given coordinates
     * and activates is (sets as an active point)
     * @param point
     * @return false if no point activated
     */
    public boolean activatePoint(Point point) {
        // TODO: implement
        return false;
    }


    /**
     * Simply changes class of the active point
     *
     * @param newPointType new type - the Class which must extend {@link spbstu.cg.fontcommons.point.ControlPoint}.
     */
    public void changeActivePointType(Class<? extends ControlPoint> newPointType) {
        if (activePoint == null)
            throw new NullPointerException();

        // TODO: Oh yeah, baby. Love reflection. Do we need this?
        try {
            activePoint = newPointType.getDeclaredConstructor(int.class, int.class).
                    newInstance(activePoint.getX(), activePoint.getY());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void addControlPoint(ControlPoint point) {
        activeSpline.addControlPoint(point);
    }

    /**
     * Finds nearest point to cursor, returns true if it exists and
     * mark that point as a underCursorPoint.
     */
    public boolean setCurrentCursorPos(Point pos) {
        Point p = findNearestPoint(pos);
        if (p != null) {
            setUnderCursorPoint(p);
            return true;
        }
        else {
            setUnderCursorPoint(null);
            return false;
        }
    }

    public Point getUnderCursorPoint() {
        return underCursorPoint;
    }

    public void setUnderCursorPoint(Point underCursorPoint) {
        this.underCursorPoint = underCursorPoint;
    }

    public List<Spline> getSplines() {
        return currentLetter.getSplines();
    }
}
