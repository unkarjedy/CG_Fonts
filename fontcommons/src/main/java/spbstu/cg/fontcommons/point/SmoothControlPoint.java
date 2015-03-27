package spbstu.cg.fontcommons.point;

import spbstu.cg.fontcommons.PointUtils;

/**
 * Created by Egor Gorbunov on 05.03.2015.
 * Email: egor_mailbox@ya.ru
 * Github username: egorbunov
 *
 * Control points with 2 handle points, which positioned on the same line
 * and on different sides of corner point
 */
public class SmoothControlPoint extends ControlPoint {
    public SmoothControlPoint(float x, float y) {
        super(x, y);
        type = PointType.SMOOTH;
    }

    @Override
    public void handlePointMoved(int index) {
        HandlePoint hp1 = handlePoints[index]; // changed point
        HandlePoint hp2 = handlePoints[1 - index];

        // TODO: actually this can happen only if controll point is side point of spline
        if(hp1 == null || hp2 == null)
            return;

        float len1 = PointUtils.getSquaredDist(hp1, this);
        float len2 = PointUtils.getSquaredDist(hp2, this);

        hp2.x = -len2 *(hp1.x - this.x) / len1 + this.x;
        hp2.y = -len2 *(hp1.y - this.y) / len1 + this.y;
    }
}
