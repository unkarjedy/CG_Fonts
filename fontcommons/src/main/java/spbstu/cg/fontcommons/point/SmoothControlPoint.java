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
    }

    @Override
    public void handlePointMoved(int index) {
        // TODO: implement
    }
}
