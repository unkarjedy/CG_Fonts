package spbstu.cg.fontcommons.point;

/**
 * Created by Egor Gorbunov on 05.03.2015.
 * Email: egor_mailbox@ya.ru
 * Github username: egorbunov
 */
public class ControlPoint extends Point {
    private static final int MAX_HANDLE_POINT_NUMBER = 2;

    protected HandlePoint[] handlePoints;

    public ControlPoint(float x, float y, PointType type) {
        super(x, y);
        handlePoints = new HandlePoint[MAX_HANDLE_POINT_NUMBER];
        this.type = type;
    }

    public void addHandlePoint(Point point, int index) {
        if(point != null) {
            handlePoints[index] = new HandlePoint(point.getX(), point.getY(), this, index);
            handlePointMoved(index);
        }
    }

    @Override
    public void move(float dx, float dy) {
        super.move(dx, dy);

        if (handlePoints != null) {
            for (HandlePoint hp : handlePoints) {
                if (hp == null)
                    continue;
                hp.moveSafe(dx, dy);
            }
        }
    }

    /**
     * Method is invoked if one of handle points, connected to this control one, is moved, so
     * we can decide how we need to change other one.
     * @param index
     */
    public void handlePointMoved(int index) {
        switch (type) {

            case SYMMETRIC: {
                HandlePoint hp1 = handlePoints[index]; // changed point
                HandlePoint hp2 = handlePoints[1 - index];

                // this can happen only if controll point is side point of spline
                if (hp1 == null || hp2 == null)
                    return;

                hp2.x = -(hp1.x - this.x) + this.x;
                hp2.y = -(hp1.y - this.y) + this.y;

                break;
            }

            case SMOOTH: {
                HandlePoint hp1 = handlePoints[index]; // changed point
                HandlePoint hp2 = handlePoints[1 - index];

                //  this can happen only if controll point is side point of spline
                if (hp1 == null || hp2 == null)
                    return;

                float lenSquare1 = PointUtils.getSquaredDist(hp1, this);
                float lenSquare2 = PointUtils.getSquaredDist(hp2, this);

                float k = (float) Math.sqrt(lenSquare2 / lenSquare1);
                hp2.x = -k * (hp1.x - this.x) + this.x;
                hp2.y = -k * (hp1.y - this.y) + this.y;

                break;
            }
        }

    }

    public HandlePoint[] getHandlePoints() {
        return handlePoints;
    }

    public void convertToType(PointType newType) {
        type = newType;
        handlePointMoved(0);
        handlePointMoved(1); // hope it's always ok...
    }
}

