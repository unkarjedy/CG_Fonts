package spbstu.cg.fonteditor.draw;

import spbstu.cg.fontcommons.spline.Spline;
import spbstu.cg.fontcommons.point.*;
import spbstu.cg.fontcommons.point.Point;

import java.awt.*;
import java.awt.geom.Path2D;

/**
 * Created by user on 27.03.2015.
 */
public class SplineDrawer {
    private static final Stroke DASHED_STROKE = new BasicStroke(
            0.8f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{5}, 0);

    private static final Stroke SIMPLE_SOLID_STROKE = new BasicStroke(
            1.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 10.0f);


    public void drawSpline(Spline spline, Graphics2D g2D, boolean drawControlPoints, Point pointUnderCursor) {
        ControlPoint prev = null;
        HandlePoint h1;
        HandlePoint h2;

        for (ControlPoint curr : spline) {
            if(prev == null) {
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

        // drawing control points to be above segments...
        if(drawControlPoints)
            for (ControlPoint point : spline)
                PointDrawer.draw(point, g2D);
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
