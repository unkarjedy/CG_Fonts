package spbstu.cg.fontcommons;

import spbstu.cg.fontcommons.point.Point;

/**
 * Created by Egor Gorbunov on 20.03.2015.
 * Email: egor-mailbox@ya.ru
 * Github username: egorbunov
 */
public class PointUtils {

    public static double getSquaredDist(Point x, Point y) {
        return (x.getX() - y.getX()) * (x.getX() - y.getX()) + (x.getY() - y.getY()) * (x.getY() - y.getY());
    }
}
