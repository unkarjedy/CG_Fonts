package spbstu.cg.fontcommons.point;

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

    public void addHandlePoint(Point point, int index) {
        if (handlePoints == null)
            handlePoints = new HandlePoint[MAX_HANDLE_POINT_NUMBER];

        handlePoints[index] = new HandlePoint(point.getX(), point.getY(), this, index);
        handlePointMoved(index);
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
