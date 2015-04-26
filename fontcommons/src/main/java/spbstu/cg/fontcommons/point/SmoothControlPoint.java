package spbstu.cg.fontcommons.point;

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

        //  this can happen only if controll point is side point of spline
        if(hp1 == null || hp2 == null)
            return;

        float lenSquare1 = PointUtils.getSquaredDist(hp1, this);
        float lenSquare2 = PointUtils.getSquaredDist(hp2, this);

        float k = (float) Math.sqrt(lenSquare2 / lenSquare1);
        hp2.x = -k * (hp1.x - this.x) + this.x;
        hp2.y = -k * (hp1.y - this.y)+ this.y;
    }
}
