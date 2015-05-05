package spbstu.cg.fonteditor.model.action;

import spbstu.cg.fontcommons.point.Point;

/**
 * Created by Egor Gorbunov on 26.04.2015.
 * Email: egor-mailbox@ya.ru
 * Github username: egorbunov
 */
public class PointMoveAction extends ModelAction {

    private final Point point;
    private float dx;
    private float dy;

    private float beforeX;
    private float beforeY;


    public PointMoveAction(Point point, float dx, float dy) {
        this.point = point;

        beforeX = point.getX() - dx;
        beforeY = point.getY() - dy;
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public String name() {
        return "Point move action";
    }

    @Override
    public void undo() {
        point.move(beforeX - point.getX(), beforeY - point.getY());
    }

    @Override
    public void redo() {
        point.move(dx, dy);
    }
}
