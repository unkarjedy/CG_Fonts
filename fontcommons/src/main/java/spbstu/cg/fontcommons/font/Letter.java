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

    // letter bounding box
    private float left;
    private float right;
    private float top;
    private float bottom;

    private ArrayList<Spline> splines;

    public Letter(Character alias) {
        this.alias = alias;
        splines = new ArrayList<>();
        left = right = top = bottom = 0;
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
            for (ControlPoint p : s) {

                p.set((p.getX() - left) / height, (p.getY() - top) / height);

                for (HandlePoint hp : p.getHandlePoints()) {
                    if (hp != null) {
                        hp.set((hp.getX() - left) / height, (hp.getY() - top) / height);
                    }
                }
            }
        }
    }
}
