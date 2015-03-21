package spbstu.cg.fonteditor.view;

import spbstu.cg.fontcommons.Spline;
import spbstu.cg.fontcommons.point.ControlPoint;
import spbstu.cg.fontcommons.point.Point;
import spbstu.cg.fonteditor.draw.PointDrawer;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class LetterEditorView extends JComponent {
    private Rectangle bounds;
    private Graphics2D g2D;

    // state
    private Point pointUnderCursor;
    private List<Spline> splines;

    public LetterEditorView(){
        bounds = null;
        g2D = null;
        pointUnderCursor = null;
        splines = null;

        JPanel verticalBoxPanel = new JPanel();
        verticalBoxPanel.setLayout(new BoxLayout(verticalBoxPanel, BoxLayout.Y_AXIS));

        JLabel projectNameLabel = new JLabel("Spline Editor");
        projectNameLabel.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 0));
        verticalBoxPanel.add(projectNameLabel);

        JSeparator separator = new JSeparator();
        separator.setForeground(Color.gray);
        verticalBoxPanel.add(separator);

        add(verticalBoxPanel, BorderLayout.NORTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g2D = (Graphics2D) g;
        setupGraphics(g2D);
        g2D.setColor(Color.white);
        bounds = g2D.getClipBounds();
        g2D.fill(bounds);

        if (pointUnderCursor != null) {
            PointDrawer.drawActiveCircle(pointUnderCursor, g2D);
        }
        if (splines != null) {
            drawSplines();
        }
    }

    public void setPointUnderCursor(Point p) {
        pointUnderCursor = p;
    }

    public void setSplines(List<Spline> splines) {
        this.splines = splines;
    }

    @Override
    public void repaint() {
        super.repaint();
    }

    public void drawSplines() {
        for (Spline spline : splines) {
            Point prev = null;
            for (ControlPoint point : spline) {
                PointDrawer.draw(point, g2D);
                if (prev != null) {
                    g2D.drawLine((int) point.getX(),
                            (int) point.getY(), (int) prev.getX(), (int) prev.getY());
                }
                prev = point;
            }
        }
    }

    private void setupGraphics(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
    }
}
