package spbstu.cg.fontcommons.point;

/**
 * Created by Egor Gorbunov on 05.03.2015.
 * Email: egor_mailbox@ya.ru
 * Github username: egorbunov
 */
public abstract class ControlPoint extends Point {
    private static final int MAX_HANDLE_POINT_NUMBER = 2;

    protected HandlePoint[] handlePoints;

    public ControlPoint(float x, float y) {
        super(x, y);
        handlePoints = new HandlePoint[MAX_HANDLE_POINT_NUMBER];
        this.type = PointType.UNSUPPORTED_TYPE;
    }

    public void addHandlePoint(Point point, int index) {
        handlePoints[index] = new HandlePoint(point.getX(), point.getY(), this, index);
        handlePointMoved(index);
    }

    @Override
    public void move(float dx, float dy) {
        super.move(dx, dy);

        if (handlePoints != null) {
            for (HandlePoint hp : handlePoints) {
                if (hp == null)
                    continue;
                hp.move(dx, dy);
            }
        }
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

