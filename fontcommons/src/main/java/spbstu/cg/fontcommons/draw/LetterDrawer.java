package spbstu.cg.fontcommons.draw;

import spbstu.cg.fontcommons.font.Letter;
import spbstu.cg.fontcommons.spline.Spline;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.util.List;

/**
 * Created by user on 28.04.2015.
 */
public class LetterDrawer {
    public static void draw(Letter letter, Graphics2D g2D, int x, int y, int fontsize){
        List<Spline> splines = letter.getSplines();

        SplineDrawer.drawLetterSplines(splines, g2D, x, y, fontsize);
    }
}
