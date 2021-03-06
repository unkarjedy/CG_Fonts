package spbstu.cg.fontcommons.font;

import spbstu.cg.fontcommons.point.ControlPoint;
import spbstu.cg.fontcommons.point.HandlePoint;
import spbstu.cg.fontcommons.spline.Spline;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 28.02.2015.
 */

/**
 * Class describes a vector font letter.
 *
 * TODO: add conversion to polygon, colouring, and a lot of other stuff...
 */
public class Letter implements Serializable{
    private Character alias;

    public float getLeft() {
        return left;
    }

    public float getRight() {
        return right;
    }

    public float getTop() {
        return top;
    }

    public float getBottom() {
        return bottom;
    }

    // letter bounding box
    private float left;
    private float right;
    private float top;
    private float bottom;

    private ArrayList<Spline> splines;

    public Letter(Character alias) {
        this.alias = alias;
        splines = new ArrayList<>();
        left = right = top = bottom = -1;
    }

    public void setBoundingBox(float left, float right, float top, float bottom) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
    }

    /**
     * Adds new contour of the letter.
     * @param spline new part of the letter represented as spline
     */
    public void addSpline(Spline spline) {
        splines.add(spline);
    }

    public Character getAlias() {
        return alias;
    }

    public List<Spline> getSplines() {
        return splines;
    }

    public void normalize() {
        float wid = right - left;
        float height = bottom - top;

        for (Spline s : splines) {
            s.translateAndScale(-left, -top, 1 / height, 1 / height);
        }

        bottom = (bottom - top) / height;
        right = (right - left) / height;
        left = 0;
        top = 0;
    }

    public float getHeight() {
        return (bottom - top);
    }

    public float getWidth() {
        return (right - left);
    }

    public void removeLastSpline() {
        splines.remove(splines.size() - 1);
    }

    /**
     * @return number of splines in letter
     */
    public int size() {
        return splines.size();
    }
}
