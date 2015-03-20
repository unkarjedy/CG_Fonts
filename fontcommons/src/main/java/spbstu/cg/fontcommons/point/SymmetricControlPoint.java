package spbstu.cg.fontcommons.point;

/**
 * Created by Egor Gorbunov on 05.03.2015.
 * Email: egor_mailbox@ya.ru
 * Github username: egorbunov
 *
 * The same as {@link spbstu.cg.spline.point.SmoothControlPoint} (maybe we need some INHERITANCE!!!! here)
 * but handle points are also have same distance to control point
 */

public class SymmetricControlPoint extends ControlPoint {
    public SymmetricControlPoint(float x, float y) {
        super(x, y);
    }

    @Override
    public void handlePointMoved(int index) {
        // TODO: implement me
    }
}
