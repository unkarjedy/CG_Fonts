package spbstu.cg.fontcommons.point;

import spbstu.cg.fontcommons.point.*;

/**
 * Created by Egor Gorbunov on 20.03.2015.
 * Email: egor-mailbox@ya.ru
 * Github username: egorbunov
 */
public class PointUtils {

    public static float getSquaredDist(Point x, Point y) {
        return (x.getX() - y.getX()) * (x.getX() - y.getX()) + (x.getY() - y.getY()) * (x.getY() - y.getY());
    }

    public static float getSquaredDist(float x1, float y1, float x2, float y2) {
        return (float) Math.hypot(x1 - x2, y1 - y2);
    }
}
