package spbstu.cg.spline.point;

/**
 * Created by Egor Gorbunov on 05.03.2015.
 * Email: egor_mailbox@ya.ru
 * Github username: egorbunov
 */
public abstract class ControlPoint extends Point {
    private static final int MAX_HANDLE_POINT_NUMBER = 2;

    HandlePoint[] handlePoints;

    public ControlPoint(float x, float y) {
        super(x, y);
        handlePoints = null;
    }

    public void addHandlePoint(Point point) {
        if (handlePoints == null)
            handlePoints = new HandlePoint[MAX_HANDLE_POINT_NUMBER];

        assert handlePoints[1] == null; // first point may not be null, but second must, because it's bad to
                                        // overwrite already existing one, i think

        if (handlePoints[0] == null)
            handlePoints[0] = new HandlePoint(point.getX(), point.getY(), this, 0);
        else
            handlePoints[1] = new HandlePoint(point.getX(), point.getY(), this, 1);
    }

    /**
     * Method is invoked if one of handle points, connected to this control one, is moved, so
     * we can decide how we need to change other one.
     * @param index
     */
    public abstract void handlePointMoved(int index);

    public HandlePoint[] getHandlePoints() {
        return handlePoints;
    }
}
