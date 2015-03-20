package spbstu.cg.fontcommons;

import spbstu.cg.fontcommons.point.ControlPoint;
import spbstu.cg.fontcommons.point.Point;

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
    private static final short POINT_AVERAGE_CAPACITY = 20;
    private ArrayList<ControlPoint> controlPoints;


    public Spline() {
        controlPoints = new ArrayList<ControlPoint>(POINT_AVERAGE_CAPACITY);
    }

    public void addControlPoint(ControlPoint point) {
        controlPoints.add(point);
    }

    @Override
    public Iterator<ControlPoint> iterator() {
        return controlPoints.iterator();
    }
}
