package spbstu.cg.fonteditor.model;

import spbstu.cg.fontcommons.point.Point;
import spbstu.cg.fontcommons.point.PointType;

import java.awt.*;
import java.util.Iterator;

/**
 * Created by Egor Gorbunov on 27.04.2015.
 * Email: egor-mailbox@ya.ru
 * Github username: egorbunov
 *
 *  Bounding box -- 4 lines, every line goes from one border of the Editor Panel to another
 *
 *  Actually, the bounding box is that part between that 4 lines
 */
public class BoundingBox implements Iterable<Point> {

    private float l;
    private float r;
    private float b;
    private float t; // constratings

    public void setLetterBounds(float l, float r, float b, float t) {
        this.l = l;
        this.r = r;
        this.b = b;
        this.t = t;
    }

    public class HorizontallyMovingPoint extends Point {
        HorizontallyMovingPoint pairPoint;

        public HorizontallyMovingPoint(float x, float y) {
            super(x, y);
            type = PointType.NO_TYPE;
        }

        public void setPair(HorizontallyMovingPoint pair) {
            pairPoint = pair;
        }

        @Override
        public void move(float dx, float dy) {
            if (BoundingBox.this.checkHorizontalMove(this, dx)) {
                super.move(dx, 0);
                if (pairPoint != null)
                    pairPoint.set(pairPoint.getX() + dx, pairPoint.getY());
            }
        }
    }

    public class VerticallyMovingPoint extends Point {
        VerticallyMovingPoint pairPoint;

        public VerticallyMovingPoint(float x, float y) {
            super(x, y);
            type = PointType.NO_TYPE;

        }

        public void setPair(VerticallyMovingPoint pair) {
            pairPoint = pair;
        }


        @Override
        public void move(float dx, float dy) {
            /*if (BoundingBox.this.checkVerticalMove(this, dy)) {
                super.move(0, dy);
                if (pairPoint != null)
                    pairPoint.set(pairPoint.getX(), pairPoint.getY() + dy);
            }*/
        }
    }

    private HorizontallyMovingPoint topLeft, topRight;
    private HorizontallyMovingPoint bottomLeft, bottomRight;

    private VerticallyMovingPoint leftTop, leftBottom;
    private VerticallyMovingPoint rightTop, rightBottom;

    private Point[] allPoints;

    float w, h;

    /**
     * @param w -- width of the canvas (field to draw into)
     * @param h -- height of the canvas
     */
    public BoundingBox(float w, float h) {
        l = 0; b = 0;
        r = w; t = h;

        this.w = w;
        this.h = h;

        final float ratio = 0.1f;

        leftTop = new VerticallyMovingPoint(0, h * ratio);
        leftBottom = new VerticallyMovingPoint(0, (1 - ratio) * h);
        rightTop = new VerticallyMovingPoint(w, h * ratio);
        rightBottom = new VerticallyMovingPoint(w, (1 - ratio) * h);

        float r = ((w - leftBottom.getY() + leftTop.getY()) / 2f) / h;
        topLeft = new HorizontallyMovingPoint(w * r, 0);
        topRight = new HorizontallyMovingPoint((1 - r) * w, 0);
        bottomLeft = new HorizontallyMovingPoint(w * r, h);
        bottomRight = new HorizontallyMovingPoint( (1 - r) * w, h);



        allPoints = new Point[] {topLeft, topRight, bottomLeft, bottomRight,
                leftTop, leftBottom, rightTop, rightBottom};

        topLeft.setPair(bottomLeft);
        bottomLeft.setPair(topLeft);
        topRight.setPair(bottomRight);
        bottomRight.setPair(topRight);
        leftBottom.setPair(rightBottom);
        rightBottom.setPair(leftBottom);
        leftTop.setPair(rightTop);
        rightTop.setPair(leftTop);
    }

    /**
     * @param p -- can only be one of current baounding box points
     * @return true if move not breaking rectangle structure, i.e. left segment is lefter than right one
     */
    private boolean checkHorizontalMove(HorizontallyMovingPoint p, float dx) {
        float lower = (r == -1) ? bottomLeft.getX() :  Math.max(bottomLeft.getX(), r);
        float upper  = (l == -1) ? bottomRight.getX() :  Math.min(bottomRight.getX(), l);


        if (p.equals(bottomLeft) || p.equals(topLeft)) {
            float newWidth = bottomRight.getX() - p.getX() - dx;

            if (newWidth > leftBottom.getY() - leftTop.getY())
                return false;

            if (p.getX() + dx < upper && p.getX() + dx > 0)
                return true;
        } else if (p.equals(bottomRight) || p.equals(topRight)) {
            float newWidth = p.getX() + dx - bottomLeft.getX();

            if (newWidth > leftBottom.getY() - leftTop.getY())
                return false;

            if (p.getX() + dx > lower && p.getX() + dx < w)
                return true;
        }
        return false;
    }

    /**
     * @param p -- can only be one of current baounding box points
     * @return true if move not breaking rectangle structure, i.e. top segment is upper than bottom one
     */
    private boolean checkVerticalMove(VerticallyMovingPoint p, float dy) {
        float upper = (t == -1) ? leftBottom.getY() :  Math.min(leftBottom.getY(), t);
        float lower  = (b == -1) ? leftTop.getY() :  Math.max(leftTop.getY(), b);

        if (p.equals(leftTop) || p.equals(rightTop)) {
            if (p.getY() + dy < upper)
                return true;
        } else if (p.equals(leftBottom) || p.equals(rightBottom)) {
            if (p.getY() + dy > lower)
                return true;
        }
        return false;
    }

    @Override
    public Iterator<Point> iterator() {
        return new Iterator<Point>() {
            int cur = 0;
            @Override
            public boolean hasNext() {
                return cur < allPoints.length;
            }

            @Override
            public Point next() {
                return allPoints[cur++];
            }
        };
    }

    public void resize(float w, float h) {
        l = 0; b = 0;
        r = w; t = h;

        if (w <= 0 || h <= 0)
            return;

        for (Point p : allPoints) {
            float x = p.getX();
            float y = p.getY();

            p.set((x / this.w) * w, (y / this.h) * h);
        }

        this.w = w;
        this.h = h;
    }

    public boolean isIn(float x, float y) {
        return x < bottomRight.getX() &&
                x > bottomLeft.getX() &&
                y < rightBottom.getY() &&
                y > rightTop.getY();
    }


    public void draw(Graphics2D g2D) {
        final Color BOUND_COLOR = new Color(0.6392157f, 0.6156863f, 0.6313726f);
        final Color RECT_COLOR = new Color(0.7647059f, 0.14901961f, 0.011764706f, 0.3f);
        final Stroke stroke = new BasicStroke(2);
        g2D.setStroke(stroke);
        g2D.setColor(BOUND_COLOR);
        g2D.drawLine((int) leftBottom.getX(), (int) leftBottom.getY(),
                (int) rightBottom.getX(), (int) rightBottom.getY());

        g2D.drawLine((int) leftTop.getX(), (int) leftTop.getY(),
                (int) rightTop.getX(), (int) rightTop.getY());

        g2D.drawLine((int) topLeft.getX(), (int) topLeft.getY(),
                (int) bottomLeft.getX(), (int) bottomLeft.getY());

        g2D.drawLine((int) topRight.getX(), (int) topRight.getY(),
                (int) bottomRight.getX(), (int) bottomRight.getY());

        int rH =(int) (leftBottom.getY() - leftTop.getY());

        g2D.setColor(RECT_COLOR);
        g2D.fillRect(0, 0, (int) rightTop.getX(), (int) leftTop.getY());
        g2D.fillRect((int) leftBottom.getX(), (int) leftBottom.getY(), (int) rightTop.getX(),
                (int) (h - leftBottom.getY()));

        g2D.fillRect((int) leftTop.getX(), (int) leftTop.getY(), (int) topLeft.getX(), rH);
        g2D.fillRect((int) bottomRight.getX(), (int) leftTop.getY(), (int) (w - topRight.getX()), rH);

    }

    public float getLeft() {
        return topLeft.getX();
    }

    public float getRight() {
        return topRight.getX();
    }

    public float getTop() {
        return leftTop.getY();
    }

    public float getBottom() {
        return leftBottom.getY();
    }

}
