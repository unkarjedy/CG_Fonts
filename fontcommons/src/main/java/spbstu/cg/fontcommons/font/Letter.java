package spbstu.cg.fontcommons.font;

import spbstu.cg.fontcommons.spline.Spline;

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
public class Letter {
    private String alias;
    private float width, height;

    private ArrayList<Spline> splines;

    public Letter(String alias, float width, float height) {
        this.alias = alias;
        this.width = width;
        this.height = height;
        splines = new ArrayList<>();
    }

    /**
     * Adds new contour of the letter.
     * TODO: Maybe if we want to keep some order on splines we need to think about how ...
     *
     * @param spline new part of the letter represented as spline
     */
    public void addSpline(Spline spline) {
        splines.add(spline);
    }

    public String getAlias() {
        return alias;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeigth(float height) {
        this.height = height;
    }

    public List<Spline> getSplines() {
        return splines;
    }
}
