package spbstu.cg.fonteditor.model;

import spbstu.cg.fontcommons.font.*;
import spbstu.cg.fontcommons.spline.*;
import spbstu.cg.fontcommons.point.ControlPoint;
import spbstu.cg.fontcommons.point.HandlePoint;
import spbstu.cg.fontcommons.point.Point;
import spbstu.cg.fontcommons.point.PointType;
import spbstu.cg.fontcommons.point.PointUtils;
import spbstu.cg.fonteditor.Constants;
import spbstu.cg.fonteditor.controller.LetterEditorModelListener;
import spbstu.cg.fonteditor.model.action.*;

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
    BoundingBox boundingBox;

    /**
     * Active letter for
     */
    private Letter letter;

    /**
     * Not yet completed spline, but it already added to letter! (it just the last one
     * in the list of splines of the letter)
     */
    private Spline currentSpline;

    public Spline getActiveSpline() {
        return activeSpline;
    }

    /**
     *  Spline of active point. It can be any spline including
     *  already created
     */
    private Spline activeSpline;

    /**
     * Currently active point and it's index in current spline
     */
    private Point activePoint = null;

    /**
     * Point under cursor
     */
    private Point underCursorPoint = null;

    private Point currentlyMovingPoint = null;

    /**
     * Last move vector
     */
    private float lastDx, lastDy;

    private boolean isUnderCursorPointMoving;

    private ActionStack actionStack;

    private LetterEditorModelListener modelListener;

    private float viewWidth, viewHeight;

    private void activateAndAddNewSpline() {
        currentSpline = new Spline();
        letter.addSpline(currentSpline);
    }

    /**
     * Returns nearest point (for now only Control Point) to given one
     */
    private Point findNearestPoint(float x, float y) {
        for (Spline spline : letter.getSplines()) {
            int i = 0;
            for (ControlPoint point : spline) {
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
        for(Spline spline : letter.getSplines()){
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
    }

    public LetterEditorModel(Letter letter) {
        this.letter = letter;
        currentSpline = null;
        boundingBox = new BoundingBox(1, 1);
        actionStack = new ActionStack();
    }

    /**
     * That is the helping method for undo/redo. {@link FinishSplineAction};
     */
    public void deleteLastSplineAndActivatePrev() {
        letter.getSplines().remove(letter.getSplines().size() - 1);
        currentSpline = letter.getSplines().get(letter.getSplines().size() - 1);
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

        // processing cases, when point moved from view box and bounding box
        if (underCursorPoint instanceof ControlPoint) {
            if (!boundingBox.isIn(x, y))
                return true;

            ControlPoint p = (ControlPoint) underCursorPoint;

            if (!p.isInBounds(x, y, viewWidth, viewHeight))
                return true;
        }

        if (underCursorPoint instanceof HandlePoint) {
            HandlePoint hp = (HandlePoint) underCursorPoint;
            float oldX = hp.getX();
            float oldY = hp.getY();
            hp.move(x - hp.getX(), y - hp.getY());

            for (HandlePoint h : hp.getControlPoint().getHandlePoints()) {
                if (h != null) {
                    if (h.getX() < 0 || h.getX() > viewWidth || h.getY() < 0 || h.getY() > viewHeight) {
                        hp.move(oldX - hp.getX(), oldY - hp.getY());
                        return true;
                    }
                }
            }
            return true;
        }

        if (underCursorPoint instanceof BoundingBox.HorizontallyMovingPoint ||
                underCursorPoint instanceof BoundingBox.VerticallyMovingPoint) {
            if (!wasBoundMovedPrev) {
                updateLetterRect();
                boundingBox.setLetterBounds(letterRect.l, letterRect.r, letterRect.b, letterRect.t);
                wasBoundMovedPrev = true;
            }
        } else {
            wasBoundMovedPrev = false;
        }

        underCursorPoint.move(x - underCursorPoint.getX(), y - underCursorPoint.getY());

        return true;
    }

    boolean wasBoundMovedPrev = false;

    /**
     * Finds nearest point to cursor, returns true if it exists and
     * mark that point as a underCursorPoint.
     */
    public Point setCurrentCursorPos(float x, float y) {
        Point p = findNearestPoint(x, y);

        if (p == null) {
            // check bounding box
            for (Point point : boundingBox) {
                if (PointUtils.getSquaredDist(x, y,
                        point.getX(), point.getY()) < Constants.BOUNDING_BOX_PNTS_EPS) {
                    p = point;
                    break;
                }
            }
        }

        setUnderCursorPoint(p);
        return p;
    }

    public Point getUnderCursorPoint() {
        return underCursorPoint;
    }

    int cnt = 0;
    public List<Spline> getSplines() {
        return letter.getSplines();
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
        }

        modelListener.activePointChanged(point);

        if (point == null || point instanceof ControlPoint) {
            modelListener.controlPointTypeChanged((ControlPoint) point);
        }
    }

    public void activateUnderCursorPoint() {
        this.activePoint = underCursorPoint;
        activeSpline = getPointSpline(activePoint);

        modelListener.activePointChanged(underCursorPoint);

        if (underCursorPoint == null || underCursorPoint instanceof ControlPoint) {
            modelListener.controlPointTypeChanged((ControlPoint) underCursorPoint);
        }

    }

    public void startMovingUnderCursorPoint() {
        if (!isUnderCursorPointMoving) {
            currentlyMovingPoint = underCursorPoint;
            isUnderCursorPointMoving = true;
            lastDx = underCursorPoint.getX();
            lastDy = underCursorPoint.getY();
        }
    }

    public boolean isUnderCursorPointMoving() {
        return isUnderCursorPointMoving;
    }


    public void endCurrentSplineAct() {
        currentSpline.addControlPoint(currentSpline.getControlPoints().get(0));
        currentSpline = null;
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

        if (underCursorPoint == null || underCursorPoint == currentSpline.getControlPoints().get(0)) {
            endCurrentSplineAct();

            actionStack.addAction(new FinishSplineAction(this));

            return true;
        }
        return false;
    }

    @RedoUndo
    public void endMovingUnderCursorPoint() {
        if (isUnderCursorPointMoving) {
            isUnderCursorPointMoving = false;
            lastDx = currentlyMovingPoint.getX() - lastDx;
            lastDy = currentlyMovingPoint.getY() - lastDy;

            actionStack.addAction(new PointMoveAction(currentlyMovingPoint, lastDx, lastDy));

            currentlyMovingPoint = null;

            wasBoundMovedPrev = false;
        }
    }

    @RedoUndo
    public void changeActivePointType(PointType newPointType) {
        if (activePoint == null || activeSpline == null)
            throw new NullPointerException();

        Spline pointSpline = getPointSpline(activePoint);
        int activePointIndex = getPointIndex(activePoint, pointSpline);

        if (!activePoint.getType().equals(newPointType)) {
            actionStack.addAction(new ChangeTypeAction(this, modelListener, activeSpline,
                    activePointIndex, activePoint.getType(), newPointType));

            activeSpline.changePointType(activePointIndex, newPointType);

            modelListener.controlPointTypeChanged((ControlPoint) activePoint);
        }
    }

    @RedoUndo
    public void addControlPointAt(int x, int y) {
        if (!boundingBox.isIn(x, y))
            return;

        if (currentSpline == null) {
            activateAndAddNewSpline();
        }

        ControlPoint point = new ControlPoint(x, y);
        addControlPoint(point);

        actionStack.addAction(new AddLastAction(this, point));
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

        if (activePoint.getWeight() == weight) {
            return;
        }

        actionStack.addAction(new WeightChangeAction(this, modelListener, activePoint, activePoint.getWeight(), weight));

        Spline pointSpline = getPointSpline(activePoint);
        int activePointIndex = getPointIndex(activePoint, pointSpline);
        activeSpline.changePointWeight(activePointIndex, weight);

    }

    /**
     * Renewing coordinates
     * @param width new view width
     * @param height new view height
     */
    public void setViewSize(float width, float height) {
        float sx = width / viewWidth;
        float sy = height / viewHeight;

        viewHeight = height;
        viewWidth = width;
        boundingBox.resize(width, height);

        for (Spline s : letter.getSplines()) {
            s.scale(sx, sy);
        }
    }

    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    public void setActiveSplineType(boolean isExternal) {
        if (activeSpline != null)
            activeSpline.setIsExternal(isExternal);
    }

    private static class MyRect {
        float l, r, t, b;
    }

    MyRect letterRect = new MyRect();

    /**
     * O(n)...
     */
    private void updateLetterRect() {
        boolean isSignificant = false;
        letterRect.r = letterRect.b = -1;
        letterRect.l = letterRect.t = 1000000000f;

        for (Spline s : letter.getSplines()) {
            for (ControlPoint p : s) {
                isSignificant= true;
                if (p.getX() < letterRect.l)
                    letterRect.l = p.getX();
                if (p.getX() > letterRect.r)
                    letterRect.r = p.getX();
                if (p.getY() < letterRect.t)
                    letterRect.t = p.getY();
                if (p.getY() > letterRect.b)
                    letterRect.b = p.getY();
            }
        }

        if (!isSignificant) {
            letterRect.l = letterRect.r = letterRect.b = letterRect.t = -1;
        }
    }

    public Letter getLetter() {
        return letter;
    }

    public Character getLetterAlias() {
        if (letter != null) {
            return letter.getAlias();
        }
        return null;
    }

    public void updateLetterBoundingBox() {
        letter.setBoundingBox(boundingBox.getLeft(), boundingBox.getRight(),
                boundingBox.getTop(), boundingBox.getBottom());
    }
}
