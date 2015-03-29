package spbstu.cg.fontcommons.point;

import spbstu.cg.fontcommons.PointUtils;

/**
 * Created by Egor Gorbunov on 05.03.2015.
 * Email: egor_mailbox@ya.ru
 * Github username: egorbunov
 *
 * The same as {@link spbstu.cg.fontcommons.point.SmoothControlPoint} (maybe we need some INHERITANCE!!!! here)
 * but handle points are also have same distance to control point
 */

public class SymmetricControlPoint extends ControlPoint {
    public SymmetricControlPoint(float x, float y) {
        super(x, y);
        type = PointType.SYMMETRIC;
    }

    @Override
    public void handlePointMoved(int index) {
        HandlePoint hp1 = handlePoints[index]; // changed point
        HandlePoint hp2 = handlePoints[1 - index];

        // this can happen only if controll point is side point of spline
        if(hp1 == null || hp2 == null)
            return;

        float len1 = PointUtils.getSquaredDist(hp1, this);

        hp2.x = -(hp1.x - this.x) + this.x;
        hp2.y = -(hp1.y - this.y) + this.y;
    }
}
