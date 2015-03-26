package spbstu.cg.fonteditor.view;

import spbstu.cg.fontcommons.Spline;
import spbstu.cg.fontcommons.point.ControlPoint;
import spbstu.cg.fontcommons.point.HandlePoint;
import spbstu.cg.fontcommons.point.Point;
import spbstu.cg.fonteditor.draw.PointDrawer;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.util.List;


public class LetterEditorView extends JComponent {
    private static final Stroke DASHED_STROKE = new BasicStroke(0.8f, BasicStroke.CAP_BUTT,
            BasicStroke.JOIN_BEVEL, 0, new float[]{5}, 0);
    private static final Stroke SIMPLE_SOLID_STROKE = new BasicStroke(1.0f, BasicStroke.CAP_SQUARE,
            BasicStroke.JOIN_MITER, 10.0f);

    private Rectangle bounds;
    private Graphics2D g2D;

    // state
    private Point pointUnderCursor;
    private List<Spline> splines;
    private Point activePoint;

    public LetterEditorView(){
        bounds = null;
        g2D = null;
        pointUnderCursor = null;
        splines = null;
        activePoint = null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g2D = (Graphics2D) g;
        setupGraphics(g2D);
        g2D.setColor(Color.white);
        bounds = g2D.getClipBounds();
        g2D.fill(bounds);

        if (activePoint != null) {
            PointDrawer.drawActivePointCircle(activePoint, g2D);
        }
        if (pointUnderCursor != null) {
            PointDrawer.drawUnderCursorCircle(pointUnderCursor, g2D);
        }
        if (splines != null) {
            drawSplines();
        }
    }

    public void drawSplines() {
        for (Spline spline : splines) {
            ControlPoint prev = null;
            Point l = null, r = null;

            // drawing handle points and segments (curves...)
            for (ControlPoint point : spline) {
                HandlePoint[] hps = point.getHandlePoints();
                if (hps != null) {
                    for (int i = 0; i < hps.length; ++i) {
                        if (hps[i] == null)
                            continue;
                        if (pointUnderCursor == hps[i]) {
                            if (i == 0) {
                                l = prev;
                                r = point;
                            } else {
                                l = point;
                            }
                        }
                        g2D.setColor(Color.black);
                        g2D.setStroke(DASHED_STROKE);
                        g2D.drawLine((int) point.getX(), (int) point.getY(),
                                (int) hps[i].getX(), (int) hps[i].getY());
                        PointDrawer.draw(hps[i], g2D);
                    }
                }

                if (prev != null) {
                    g2D.setStroke(SIMPLE_SOLID_STROKE);
                    g2D.setColor(Color.black);
                    if ((l == prev && r == point) || prev == l) {
                        g2D.setColor(Color.red);
                    }

                    // TEMP CODE TODO: DELETE
                    Path2D.Float path = new Path2D.Float();
                    path.moveTo(prev.getX(), prev.getY());
                    HandlePoint h1 = null;
                    if (prev.getHandlePoints() != null)
                        h1 = prev.getHandlePoints()[1];
                    HandlePoint h2 = point.getHandlePoints()[0];
                    if (point.getHandlePoints() != null) {
                        h2 = point.getHandlePoints()[0];
                    }
                    if (h1 != null && h2 != null) {
                        path.curveTo(h1.getX(), h1.getY(), h2.getX(), h2.getY(), point.getX(), point.getY());
                        g2D.draw(path);
                    } else {
                        g2D.drawLine((int) point.getX(), (int) point.getY(),
                                (int) prev.getX(), (int) prev.getY());
                    }
                }
                prev = point;
            }

            // drawing control points to be above segments...
            for (ControlPoint point : spline) {
                PointDrawer.draw(point, g2D);
            }
        }
    }

    public void setPointUnderCursor(Point p) {
        pointUnderCursor = p;
    }

    public void setSplines(List<Spline> splines) {
        this.splines = splines;
    }

    public void setActivePoint(Point activePoint) {
        this.activePoint = activePoint;
    }

    private void setupGraphics(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
    }


}
