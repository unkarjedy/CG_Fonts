package spbstu.cg.fontcommons.point;

import java.io.Serializable;

/**
 * Created by Egor Gorbunov on 05.03.2015.
 * Email: egor_mailbox@ya.ru
 * Github username: egorbunov
 */
public class ControlPoint extends Point implements Serializable  {
    private static final int MAX_HANDLE_POINT_NUMBER = 2;

    protected HandlePoint[] handlePoints;

    /**
     * Creates SYMMETRIC control point
     */
    public ControlPoint(float x, float y) {
        super(x, y);
        handlePoints = new HandlePoint[MAX_HANDLE_POINT_NUMBER];
        this.type = PointType.SYMMETRIC;
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
     * @param index 0 or 1 -- index of handle point
     */
    public void handlePointMoved(int index) {
        switch (type) {

            case SYMMETRIC: {
                HandlePoint hp1 = handlePoints[index]; // changed point
                HandlePoint hp2 = handlePoints[1 - index];

                // this can happen only if control point is side point of spline
                if (hp1 == null || hp2 == null)
                    return;

                hp2.x = -(hp1.x - this.x) + this.x;
                hp2.y = -(hp1.y - this.y) + this.y;

                break;
            }

            case SMOOTH: {
                HandlePoint hp1 = handlePoints[index]; // changed point
                HandlePoint hp2 = handlePoints[1 - index];

                //  this can happen only if control point is side point of spline
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

    /**
     * Checks is control point moved at given coord (x, y) all handle point and control point will be
     * inside [0..w]x[0..h]
     */
    public boolean isInBounds(float x, float y, float w, float h) {
        if (x < 0 || y < 0 || x > w || y > h)
            return false;
        float dx = x - this.x;
        float dy = y - this.y;
        for (HandlePoint hp : handlePoints) {
            if (hp == null)
                continue;
            float hx = hp.getX() + dx;
            float hy = hp.getY() + dy;

            if (hx < 0 || hy < 0 || hx > w || hy > h)
                return false;
        }

        return true;

    }
}

