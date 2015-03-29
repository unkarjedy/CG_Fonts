package spbstu.cg.fontcommons;

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
        return (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
    }

    public static ControlPoint convertToType(ControlPoint point, PointType newPointType) {
        if(point.getType() == newPointType)
            return point;

        ControlPoint newPoint;
        if(point.getType() ==  PointType.SYMMETRIC) {
            if(newPointType == PointType.SMOOTH){
                newPoint = new SmoothControlPoint(point.getX(), point.getY());

                HandlePoint[] handlePoints = point.getHandlePoints();
                for(int i = 0; i < handlePoints.length; i++) {
                    newPoint.addHandlePoint(handlePoints[i], i);
                }

                return newPoint;
            }
        }


        return point;
    }
}
