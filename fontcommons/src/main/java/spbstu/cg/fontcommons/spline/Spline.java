package spbstu.cg.fontcommons.spline;

import spbstu.cg.fontcommons.point.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Egor Gorbunov on 05.03.2015.
 * Email: egor_mailbox@ya.ru
 * Github username: egorbunov
 *
 * Class describes curve composed of control points. There is no a lot of abstraction
 * for now in that class definition so it uses ControlPoint class, which purpose is
 * to describe a point on the Bézier Curve.
 */
public class Spline implements Iterable<ControlPoint> {
    private static final short POINT_AVERAGE_CAPACITY = 30;

    /**
     * Main array. It contains control points {@link spbstu.cg.fontcommons.point.ControlPoint}.
     * There is a contract on how points are stored, because later we need to build a Bezier curves
     * on that points, so:
     *      1) array is ordered, so every Bezier curve must be built on two neighboring control points
     *      2) handle points of every control point are ordered too. Consider 2 control points (a) and (b), they
     *         has ordered pairs of handle points: (a): a_h1, a_h2; (b): b_h1, b_h2
     *         to build curve on (a) and (b) you need to use that ordered fourth: (a, a_h2, b_h1, b). But point a_h1
     *         is related to previous Bezier curve and b_h2 related to next one and so on.
     */
    private ArrayList<ControlPoint> controlPoints;


    public Spline() {
        controlPoints = new ArrayList<>(POINT_AVERAGE_CAPACITY);
    }

    public void addControlPoint(ControlPoint point) {
        final float COEF = 1 / 5.0f;
        controlPoints.add(point);

        // adding h1 and h2 handle points for the curve (prev, h1, h2, cur), cur - last added point.
        if (controlPoints.size() != 1) {
            ControlPoint prev = controlPoints.get(controlPoints.size() - 2);

            point.addHandlePoint(new Point(point.getX() + (prev.getX() - point.getX()) * COEF,
                    point.getY() + (prev.getY() - point.getY()) * COEF), 0);
            prev.addHandlePoint(new Point(prev.getX() + (point.getX() - prev.getX()) * COEF,
                    prev.getY() + (point.getY() - prev.getY()) * COEF), 1);
            point.handlePointMoved(0);
            prev.handlePointMoved(1);
        }
    }

    /**
     * Simply changes class of point at given index
     *
     * @param newPointType new type - the Class which must extend {@link spbstu.cg.fontcommons.point.ControlPoint}.
     */
    public void changePointType(int index, PointType newPointType) {
        if (index < 0 || index >= controlPoints.size()){
            throw new IllegalArgumentException();
        }

        ControlPoint point = controlPoints.get(index);
        if(point.getType() == newPointType)
            return;

        point.convertToType(newPointType);
    }

    @Override
    public Iterator<ControlPoint> iterator() {
        return controlPoints.iterator();
    }

    public List<ControlPoint> getControlPoints() {
        return controlPoints;
    }

<<<<<<< HEAD
    public void changePointWeight(int index, float weight) {
        if (index < 0 || index >= controlPoints.size()){
            throw new IllegalArgumentException();
        }

        controlPoints.get(index).setWeight(weight);
=======
    public void deleteLastControlPoint() {
        controlPoints.remove(controlPoints.size() - 1);
>>>>>>> Action system (undo / redo) added.
    }

    public void scale(float sx, float sy) {
        if (!controlPoints.isEmpty()) {

            int delta = controlPoints.get(0) == controlPoints.get(controlPoints.size() - 1) ? 1 : 0;
            if (controlPoints.size() == 1)
                delta = 0;

            for (int i = 0; i < controlPoints.size() - delta; ++i) {
                ControlPoint cp = controlPoints.get(i);
                cp.set(cp.getX() * sx, cp.getY() * sy);
                for (HandlePoint hp : cp.getHandlePoints()) {
                    if (hp != null) {
                        hp.set(hp.getX() * sx, hp.getY() * sy);
                    }
                }
            }
        }
    }
}
