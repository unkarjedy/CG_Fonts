package spbstu.cg.fonteditor.draw;

import spbstu.cg.fontcommons.point.*;
import spbstu.cg.fontcommons.point.Point;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Egor Gorbunov on 20.03.2015.
 * Email: egor-mailbox@ya.ru
 * Github username: egorbunov
 */
public class PointDrawer {
    private static final Map<Class<? extends Point>, Integer> RADIUS_MAP;
    private static final Map<Class<? extends Point>, Color> COLOR_MAP;
    private static final int ACTIVE_CIRCLE_RADIUS_ADD = 3;
    private static final Color ACTIVE_CIRCLE_COLOE = Color.red;

    static {
        RADIUS_MAP = new HashMap<Class<? extends Point>, Integer>(5);
        COLOR_MAP = new HashMap<Class<? extends Point>, Color>(5);

        RADIUS_MAP.put(CornerControlPoint.class, 5);
        RADIUS_MAP.put(SmoothControlPoint.class, 5);
        RADIUS_MAP.put(SymmetricControlPoint.class, 5);
        RADIUS_MAP.put(HandlePoint.class, 3);

        COLOR_MAP.put(CornerControlPoint.class, Color.black);
        COLOR_MAP.put(SmoothControlPoint.class, Color.black);
        COLOR_MAP.put(SymmetricControlPoint.class, Color.black);
        COLOR_MAP.put(HandlePoint.class, Color.blue);
    }


    public static void draw(final Point point, Graphics2D g2D) {
        if (RADIUS_MAP.get(point.getClass()) == null) {
            throw new IllegalArgumentException("Point to draw must be control or handle!");
        }

        g2D.setColor(COLOR_MAP.get(point.getClass()));
        g2D.fillOval((int) point.getX() - RADIUS_MAP.get(point.getClass()),
                (int) point.getY() - RADIUS_MAP.get(point.getClass()),
                RADIUS_MAP.get(point.getClass()) * 2,
                RADIUS_MAP.get(point.getClass()) * 2);
    }

    public static void drawActiveCircle(final Point point, Graphics2D g2D) {
        g2D.setColor(ACTIVE_CIRCLE_COLOE);
        int radius = RADIUS_MAP.get(point.getClass()) + ACTIVE_CIRCLE_RADIUS_ADD;
        g2D.drawOval((int) point.getX() - radius, (int) point.getY() - radius, radius * 2, radius * 2);
    }
}
