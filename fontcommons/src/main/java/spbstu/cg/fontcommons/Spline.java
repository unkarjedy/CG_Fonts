package spbstu.cg.fontcommons;

import spbstu.cg.fontcommons.point.ControlPoint;

import java.util.ArrayList;
import java.util.Iterator;

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

    ArrayList<ControlPoint> controlPoints;


    public Spline() {
        controlPoints = new ArrayList<ControlPoint>(POINT_AVERAGE_CAPACITY);
    }

    @Override
    public Iterator<ControlPoint> iterator() {
        return controlPoints.iterator();
    }
}
