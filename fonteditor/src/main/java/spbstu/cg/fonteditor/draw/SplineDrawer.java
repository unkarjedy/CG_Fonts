package spbstu.cg.fonteditor.draw;

import spbstu.cg.fontcommons.Spline;
import spbstu.cg.fontcommons.point.*;
import spbstu.cg.fontcommons.point.Point;

import java.awt.*;
import java.awt.geom.Path2D;
import java.util.*;

/**
 * Created by user on 27.03.2015.
 */
public class SplineDrawer {
    private static final Stroke DASHED_STROKE = new BasicStroke(
            0.8f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{5}, 0);

    private static final Stroke SIMPLE_SOLID_STROKE = new BasicStroke(
            1.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 10.0f);
    private static final double LEN_THRESHOLD = 1E-06;
    private static final double ANGLE_THRESHOLD = 1E-4;



    public void drawSpline(Spline spline, Graphics2D g2D, boolean drawControlPoints, Point pointUnderCursor) {
        //drawSplineStandardJava(spline, g2D, pointUnderCursor);
        drawSplineCasteljau(spline, g2D, pointUnderCursor);

        // drawing control points to be above segments...
        if (drawControlPoints)
            for (ControlPoint point : spline)
                PointDrawer.draw(point, g2D);
    }

    private void drawSplineCasteljau(Spline spline, Graphics2D g2D, Point pointUnderCursor) {
        java.util.List<ControlPoint> controlPoints = spline.getControlPoints();
        if(controlPoints.size() < 2)
            return;

        ControlPoint p1, p4;
        int curr = 0;

        g2D.setColor(Color.black);
        g2D.setStroke(SIMPLE_SOLID_STROKE);

        // TODO: implement corner control points draw
        while(true){
            p1 = controlPoints.get(curr);
            p4 = controlPoints.get(curr + 1);
            if(p1 == null && p4 == null)
                break;

            HandlePoint p2, p3;
            p2 = p1.getHandlePoints()[1];
            p3 = p4.getHandlePoints()[0];

            recursiveBezier(g2D, p1, p2, p3, p4);

            ++curr;
            if(curr == controlPoints.size() - 1)
                break;
        }
    }

    private void recursiveBezier(Graphics2D g2D, Point p1, Point p2, Point p3, Point p4) {
        recursiveBezier(g2D, p1.getX(), p1.getY(), p2.getX(), p2.getY(),
                p3.getX(), p3.getY(), p4.getX(), p4.getY());
    }

    void recursiveBezier(Graphics2D g2D, double x1, double y1, double x2, double y2,
                         double x3, double y3, double x4, double y4) {

        if (curveIsFlat(x1, y1, x2, y2, x3, y3, x4, y4)) {
            g2D.drawLine((int)x1, (int)y1, (int)x4, (int)y4);
        } else {
            double x12 = (x1 + x2) / 2;
            double y12 = (y1 + y2) / 2;
            double x23 = (x2 + x3) / 2;
            double y23 = (y2 + y3) / 2;
            double x34 = (x3 + x4) / 2;
            double y34 = (y3 + y4) / 2;
            double x123 = (x12 + x23) / 2;
            double y123 = (y12 + y23) / 2;
            double x234 = (x23 + x34) / 2;
            double y234 = (y23 + y34) / 2;
            double x1234 = (x123 + x234) / 2;
            double y1234 = (y123 + y234) / 2;

            recursiveBezier(g2D, x1, y1, x12, y12, x123, y123, x1234, y1234);
            recursiveBezier(g2D, x1234, y1234, x234, y234, x34, y34, x4, y4);
        }
    }

    // TODO: improve
    private boolean curveIsFlat(double x1, double y1, double x2, double y2,
                                double x3, double y3, double x4, double y4) {
        double dx1 = x2 - x1;
        double dx2 = x3 - x2;
        double dx3 = x4 - x3;
        double dy1 = y2 - y1;
        double dy2 = y3 - y2;
        double dy3 = y4 - y3;

        double seg1Len = Math.sqrt(dx1 * dx1 + dy1 * dy1);
        double seg2Len = Math.sqrt(dx2 * dx2 + dy2 * dy2);
        double seg3Len = Math.sqrt(dx3 * dx3 + dy3 * dy3);

//        double cos1 = dot(dx1, dy1, -dx2, -dy2) / (seg1Len * seg2Len);
//        double cos2 = dot(dx2, dy2, -dx3, -dy3) / (seg2Len * seg3Len);
//
//        return cos1 < -(1.0 - ANGLE_THRESHOLD) &&
//                cos2 < -(1.0 - ANGLE_THRESHOLD);
        return (dot(dx1, dy1, -dx2, -dy2) < -(1.0 - ANGLE_THRESHOLD) * seg1Len * seg2Len) &&
               (dot(dx2, dy2, -dx3, -dy3) < -(1.0 - ANGLE_THRESHOLD) * seg2Len * seg3Len);
    }

    double dot(double x1,double y1,double x2,double y2) {
        return x1 * x2 + y1 * y2;
    }


    private void drawSplineStandardJava(Spline spline, Graphics2D g2D, Point pointUnderCursor) {
        ControlPoint prev = null;
        HandlePoint h1;
        HandlePoint h2;

        for (ControlPoint curr : spline) {
            if (prev == null) {
                prev = curr;
                continue;
            }

            g2D.setStroke(SIMPLE_SOLID_STROKE);
            g2D.setColor(Color.black);

            // TEMP CODE TODO: implement drawing of Bezire curve
            Path2D.Float path = new Path2D.Float();
            path.moveTo(prev.getX(), prev.getY());

            h1 = prev.getHandlePoints()[1];
            h2 = curr.getHandlePoints()[0];


            if (h1 == pointUnderCursor || h2 == pointUnderCursor) {
                g2D.setColor(Color.red);
            }

            if (h1 != null && h2 != null) {
                path.curveTo(h1.getX(), h1.getY(), h2.getX(), h2.getY(), curr.getX(), curr.getY());
                g2D.draw(path);
            } else {
                g2D.drawLine((int) curr.getX(), (int) curr.getY(),
                        (int) prev.getX(), (int) prev.getY());
            }

            prev = curr;
        }
    }

    public void drawHandlePointsSegments(Spline spline, Graphics2D g2D) {
        for (ControlPoint point : spline) {
            HandlePoint[] hps = point.getHandlePoints();
            if (hps == null) {
                continue;
            }

            for (HandlePoint hp : hps) {
                if (hp == null)
                    continue;

                g2D.setColor(Color.black);
                g2D.setStroke(DASHED_STROKE);
                g2D.drawLine((int) point.getX(), (int) point.getY(),
                        (int) hp.getX(), (int) hp.getY());
                PointDrawer.draw(hp, g2D);
            }
        }
    }
}
