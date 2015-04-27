package spbstu.cg.fontcommons.draw;

import javafx.geometry.Point3D;
import spbstu.cg.fontcommons.spline.Spline;
import spbstu.cg.fontcommons.point.*;
import spbstu.cg.fontcommons.point.Point;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 27.03.2015.
 */
public class SplineDrawer {
    private static final Stroke DASHED_STROKE = new BasicStroke(
            0.8f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{5}, 0);

    private static final Stroke SIMPLE_SOLID_STROKE = new BasicStroke(
            1.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 10.0f);
    private static final double LEN_THRESHOLD = 1E-03;
    private static final double ANGLE_THRESHOLD = 1E-7;


    public static void drawSpline(Spline spline, Graphics2D g2D) {
        GeneralPath splinePath = getRationaleSplinePath(spline);

        if(splinePath != null) {
            g2D.setStroke(SIMPLE_SOLID_STROKE);
            g2D.setColor(Color.black);

            System.setProperty("awt.useSystemAAFontSettings", "on");
            g2D.draw(splinePath);
        }
    }

    public static void drawFilledSplines(List<Spline> splines, Graphics2D g2D) {
        if(splines == null)
            return;
        if(splines.size() == 0)
            return;

        // first fraw external splines
        for(Spline spline : splines){
            if(spline.isExternal()){
                fillSpline(spline, g2D, Color.black);
            }
        }

        // then draw internal splines
        for(Spline spline : splines){
            if(!spline.isExternal()){
                fillSpline(spline, g2D, Color.white);
            }
        }
    }

    public static void fillSpline(Spline spline, Graphics2D g2D, Color color) {
        GeneralPath splinePath = getRationaleSplinePath(spline);

        fillPath(splinePath, g2D, color);
    }

    public static void fillPath(GeneralPath path, Graphics2D g2D, Color color) {
        if(path != null) {
            g2D.setStroke(SIMPLE_SOLID_STROKE);
            g2D.setColor(color);
            g2D.fill(path);
        }
    }

    public static void drawLetterSplines(List<Spline> splines, Graphics2D g2D, int x, int y, double fontsize) {
        if(splines == null)
            return;
        if(splines.size() == 0)
            return;

        GeneralPath path;
        AffineTransform t = new AffineTransform();
        t.scale(fontsize, fontsize);
        t.translate(x, y);

        // first fraw external splines
        for(Spline spline : splines){
            if(spline.isExternal()){
                path = SplineDrawer.getRationaleSplinePath(spline);
                if(path == null) continue;
                path.transform(t);
                SplineDrawer.fillPath(path, g2D, Color.black);
            }
        }

        // then draw internal splines
        for(Spline spline : splines){
            if(!spline.isExternal()){
                path = SplineDrawer.getRationaleSplinePath(spline);
                if(path == null) continue;
                path.transform(t);
                SplineDrawer.fillPath(path, g2D, Color.white);
            }
        }
    }

    private static GeneralPath getSplinePath(Spline spline) {
        if(spline.getControlPoints().size() < 2)
            return null;

        ControlPoint prev = null;
        HandlePoint h1;
        HandlePoint h2;

        GeneralPath splinePath = new GeneralPath();
        ControlPoint first = spline.getControlPoints().get(0);
        splinePath.moveTo(first.getX(), first.getY());
        for (ControlPoint curr : spline) {
            if (prev == null) {
                prev = curr;
                continue;
            }

            h1 = prev.getHandlePoints()[1];
            h2 = curr.getHandlePoints()[0];

            if (h1 != null && h2 != null) {
                recursiveBezier(splinePath, prev, h1, h2, curr);
            } else {
                splinePath.lineTo(curr.getX(), curr.getY());
            }

            prev = curr;
        }

        return splinePath;
    }

    private static void recursiveBezier(GeneralPath path, Point p1, Point p2, Point p3, Point p4) {
        recursiveBezier(path, p1.getX(), p1.getY(), p2.getX(), p2.getY(),
                p3.getX(), p3.getY(), p4.getX(), p4.getY());
    }

    static void  recursiveBezier(GeneralPath path, double x1, double y1, double x2, double y2,
                         double x3, double y3, double x4, double y4) {

        if (curveIsFlat(x1, y1, x2, y2, x3, y3, x4, y4)) {
            path.lineTo(x4, y4);
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

            recursiveBezier(path, x1, y1, x12, y12, x123, y123, x1234, y1234);
            recursiveBezier(path, x1234, y1234, x234, y234, x34, y34, x4, y4);
        }
    }

    private static boolean curveIsFlat(double x1, double y1, double x2, double y2,
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

        return (dot(dx1, dy1, -dx2, -dy2) < -(1.0 - ANGLE_THRESHOLD) * seg1Len * seg2Len) &&
                (dot(dx2, dy2, -dx3, -dy3) < -(1.0 - ANGLE_THRESHOLD) * seg2Len * seg3Len);
    }

    static double dot(double x1,double y1,double x2,double y2) {
        return x1 * x2 + y1 * y2;
    }

    static double dot(double x1,double y1,double z1,double x2,double y2,double z2) {
        return x1 * x2 + y1 * y2 + z1 * z2;
    }

    public static void drawHandlePointsSegments(Spline spline, Graphics2D g2D) {
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

    public static void drawControlPoints(Spline spline, Graphics2D g2D) {
        for (ControlPoint point : spline)
            PointDrawer.draw(point, g2D);
    }

    public static  GeneralPath getRationaleSplinePath(Spline spline) {
        if(spline.getControlPoints().size() < 2)
            return null;

        ControlPoint prev = null;
        HandlePoint h1;
        HandlePoint h2;

        GeneralPath splinePath = new GeneralPath();
        ControlPoint first = spline.getControlPoints().get(0);
        splinePath.moveTo(first.getX(), first.getY());
        for (ControlPoint curr : spline) {
            if (prev == null) {
                prev = curr;
                continue;
            }

            h1 = prev.getHandlePoints()[1];
            h2 = curr.getHandlePoints()[0];

            if (h1 != null && h2 != null) {
                rationalBezier(splinePath, prev, h1, h2, curr);
            } else {
                splinePath.lineTo(curr.getX(), curr.getY());
            }

            prev = curr;
        }

        return splinePath;
    }

    private static void rationalBezier(GeneralPath path, Point p1, Point p2, Point p3, Point p4) {

        // Core CHANGES FROM SIMPLE BEZIER CURVE
        List<Point3D> points3d = new ArrayList<>();

        float w1 = p1.getWeight();
        float w2 = p2.getWeight();
        float w3 = p3.getWeight();
        float w4 = p4.getWeight();

        recursiveBezier3D(points3d,
                w1 * p1.getX(), w1 * p1.getY(), w1,
                w2 * p2.getX(), w2 * p2.getY(), w2,
                w3 * p3.getX(), w3 * p3.getY(), w3,
                w4 * p4.getX(), w4 * p4.getY(), w4
        );

        double w;
        for(Point3D p3d : points3d){
            w = p3d.getZ();
            path.lineTo(p3d.getX() / w,
                    p3d.getY() / w);
        }
    }

    static void recursiveBezier3D(List<Point3D> path,
                         double x1, double y1, double z1,
                         double x2, double y2, double z2,
                         double x3, double y3, double z3,
                         double x4, double y4, double z4) {

        if (curveIsFlat3D(x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4) ||
                len(x1, y1, z1, x4, y4, z4) < LEN_THRESHOLD)
        {
            path.add(new Point3D(x4, y4, z4));
        } else {
            double x12 = (x1 + x2) / 2;
            double y12 = (y1 + y2) / 2;
            double z12 = (z1 + z2) / 2;

            double x23 = (x2 + x3) / 2;
            double y23 = (y2 + y3) / 2;
            double z23 = (z2 + z3) / 2;

            double x34 = (x3 + x4) / 2;
            double y34 = (y3 + y4) / 2;
            double z34 = (z3 + z4) / 2;

            double x123 = (x12 + x23) / 2;
            double y123 = (y12 + y23) / 2;
            double z123 = (z12 + z23) / 2;

            double x234 = (x23 + x34) / 2;
            double y234 = (y23 + y34) / 2;
            double z234 = (z23 + z34) / 2;

            double x1234 = (x123 + x234) / 2;
            double y1234 = (y123 + y234) / 2;
            double z1234 = (z123 + z234) / 2;

            recursiveBezier3D(path, x1, y1, z1, x12, y12, z12, x123, y123, z123, x1234, y1234, z1234);
            recursiveBezier3D(path, x1234, y1234, z1234, x234, y234, z234, x34, y34, z34, x4, y4, z4);
        }
    }

    private static double len(double x1, double y1, double z1, double x4, double y4, double z4) {
        double dx = x4 - x1;
        double dy = y4 - y1;
        double dz = z4 - z1;
        return Math.sqrt(dx*dx + dy*dy + dz*dz);
    }

    private static boolean curveIsFlat3D(double x1, double y1, double z1, double x2, double y2, double z2, double x3, double y3, double z3, double x4, double y4, double z4) {
        double dx1 = x2 - x1;
        double dx2 = x3 - x2;
        double dx3 = x4 - x3;

        double dy1 = y2 - y1;
        double dy2 = y3 - y2;
        double dy3 = y4 - y3;

        double dz1 = z2 - z1;
        double dz2 = z3 - z2;
        double dz3 = z4 - z3;

        double seg1Len = Math.sqrt(dx1 * dx1 + dy1 * dy1 + dz1 * dz1);
        double seg2Len = Math.sqrt(dx2 * dx2 + dy2 * dy2 + dz2 * dz2);
        double seg3Len = Math.sqrt(dx3 * dx3 + dy3 * dy3 + dz3 * dz3);

        return (dot(dx1, dy1, dz1, -dx2, -dy2, -dz2) < -(1.0 - ANGLE_THRESHOLD) * seg1Len * seg2Len) &&
                (dot(dx2, dy2, dz2, -dx3, -dy3, -dz3) < -(1.0 - ANGLE_THRESHOLD) * seg2Len * seg3Len);
    }

}
