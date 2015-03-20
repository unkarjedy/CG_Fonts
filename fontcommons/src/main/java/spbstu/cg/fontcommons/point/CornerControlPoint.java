package spbstu.cg.fontcommons.point;

/**
 * Created by Egor on 05.03.2015.
 * Email: egor_mailbox@ya.ru
 * Github username: egorbunov
 *
 * Simple point without any handle points
 */
public class CornerControlPoint extends ControlPoint {

    public CornerControlPoint(float x, float y) {
        super(x, y);
    }

    /**
     *
     */
    @Override
    public void addHandlePoint(Point point) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void handlePointMoved(int index) {}
}
