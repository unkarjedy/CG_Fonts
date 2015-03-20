package spbstu.cg.fonteditor.view;

import spbstu.cg.fontcommons.Spline;
import spbstu.cg.fontcommons.point.ControlPoint;
import spbstu.cg.fontcommons.point.Point;
import spbstu.cg.fonteditor.draw.PointDrawer;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class LetterEditorView extends JComponent {
    Rectangle bounds;
    public LetterEditorView(){
        bounds = null;
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

        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(Color.white);
        bounds = g2D.getClipBounds();
        g2D.fill(bounds);
    }

    public void drawPoint(ControlPoint point) {
        PointDrawer.draw(point, (Graphics2D) this.getGraphics());
    }

    public void activate(Point p) {
        if (p == null)
            return;
        PointDrawer.drawActiveCircle(p, (Graphics2D) this.getGraphics());
    }

    public void reDrawSplines(List<Spline> splines) {
        super.update(getGraphics());

        for (Spline spline : splines) {
            Point prev = null;
            for (ControlPoint point : spline) {
                PointDrawer.draw(point, (Graphics2D) super.getGraphics());
                if (prev != null) {
                    (getGraphics()).drawLine((int) point.getX(),
                            (int) point.getY(), (int) prev.getX(), (int) prev.getY());
                }
                prev = point;
            }
        }
    }
}
