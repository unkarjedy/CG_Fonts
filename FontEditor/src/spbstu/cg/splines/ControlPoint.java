package spbstu.cg.splines;

/**
 * Created by Egor Gorbunov on 05.03.2015.
 * Email: egor_mailbox@ya.ru
 * Github username: egorbunov
 */
public class ControlPoint extends Point {
    private static int MAX_HANDLE_POINT_NUMBER = 2;

    public static enum Type {
        CORNER,     // simple corner point without handle points (direction points)
        CUSP,       // control point with 2 free handle points
        SMOOTH,     // c. p. with 2 handle points, which lie on same line and on different sides of control point
        SYMMETRICAL // same as SMOOTH, but also handle points has same distance from control point
    }

    HandlePoint[] handlePoints;
    Type type;

    public ControlPoint(float x, float y) {
        super(x, y);
        handlePoints = null;
        type = Type.CORNER;
    }

    public void changePointType(Type newType) {
        type = newType;
        if (newType.equals(Type.CORNER))
            handlePoints = null;
        else
            handlePoints = new HandlePoint[MAX_HANDLE_POINT_NUMBER];
    }

    public void addHandlePoint(Point point) {
        if (type.equals(Type.CORNER))
            throw new IllegalStateException("Corner point can't have handle points!");

        assert handlePoints.length == MAX_HANDLE_POINT_NUMBER;
        assert handlePoints[1] == null; // first point may not be null, but second must, because it's bad to
                                        // overwrite already existing one, i think

        if (handlePoints[0] == null)
            handlePoints[0] = new HandlePoint(point.getX(), point.getY(), this, 0);
        else
            handlePoints[1] = new HandlePoint(point.getX(), point.getY(), this, 1);
    }

    public void handlePointMoved(int index) {
        switch (this.type) {
            case SMOOTH:
                break;
            case SYMMETRICAL:
                break;
        }
    }

}
