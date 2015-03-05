package spbstu.cg.splines;

/**
 * Created by Egor Gorbunov on 05.03.2015.
 * Email: egor_mailbox@ya.ru
 * Github username: egorbunov
 */
public class HandlePoint extends Point {
    private ControlPoint controlPoint;
    private int index;

    /**
     * @param x abscissa coordinate
     * @param y ordinate coordinate
     * @param parent every handle point must have control point as it's holder
     * @param index maximal amount of handle points of every control point is 2, so
     *              it's not hard to keep an index (0 or 1) of every handle point
     *              to get rid of extra if statements
     */
    public HandlePoint(float x, float y, ControlPoint parent, int index) {
        super(x, y);

        if (parent == null)
            throw new NullPointerException();

        controlPoint = parent;
        this.index = index;
    }

    public void setControlPoint(ControlPoint controlPoint) {
        this.controlPoint = controlPoint;
    }

    @Override
    public void move(Point moveVec) {
        super.move(moveVec);
        controlPoint.handlePointMoved(index);
    }
}
