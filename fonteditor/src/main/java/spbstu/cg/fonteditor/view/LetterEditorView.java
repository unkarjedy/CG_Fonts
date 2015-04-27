package spbstu.cg.fonteditor.view;

import spbstu.cg.fontcommons.spline.Spline;
import spbstu.cg.fontcommons.point.ControlPoint;
import spbstu.cg.fontcommons.point.Point;
import spbstu.cg.fonteditor.draw.PointDrawer;
import spbstu.cg.fonteditor.draw.SplineDrawer;
import spbstu.cg.fonteditor.model.BoundingBox;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class LetterEditorView extends JComponent {
    private static final Stroke DASHED_STROKE = new BasicStroke(0.8f, BasicStroke.CAP_BUTT,
            BasicStroke.JOIN_BEVEL, 0, new float[]{5}, 0);
    private static final Stroke SIMPLE_SOLID_STROKE = new BasicStroke(1.0f, BasicStroke.CAP_SQUARE,
            BasicStroke.JOIN_MITER, 10.0f);

    private Rectangle bounds;
    private Graphics2D g2D;
    private final SplineDrawer splineDrawer = new SplineDrawer();

    // === state ===
    private Point pointUnderCursor;
    private List<Spline> splines;
    private Point activePoint;
    private boolean drawSplineMetaData = true;
    private boolean fillSpline = true;
    private BoundingBox boundingBox;

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

        if (boundingBox != null) {
            boundingBox.draw(g2D);
        }
    }

    public void drawSplines() {
        for (Spline spline : splines) {
            splineDrawer.drawSpline(spline, g2D);

            if(drawSplineMetaData){
                splineDrawer.drawHandlePointsSegments(spline, g2D);
                splineDrawer.drawControlPoints(spline, g2D);
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

    public void setBoundingBox(BoundingBox box) {
        boundingBox = box;
    }

    private void setupGraphics(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
    }


}
