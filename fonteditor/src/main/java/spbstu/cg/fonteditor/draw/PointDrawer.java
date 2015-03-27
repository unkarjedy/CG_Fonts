package spbstu.cg.fonteditor.draw;

import spbstu.cg.fontcommons.point.*;
import spbstu.cg.fontcommons.point.Point;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Egor Gorbunov on 20.03.2015.
 * Email: egor-mailbox@ya.ru
 * Github username: egorbunov
 */
public class PointDrawer {
    private static final Color MY_PURPLE = new Color(0.64f, 0.0f, 0.25f);

    private static final Map<Class<? extends Point>, Integer> RADIUS_MAP;
    private static final Map<Class<? extends Point>, Color> COLOR_MAP;
    private static final int ACTIVE_CIRCLE_RADIUS_ADD = 4;
    private static final Color ACTIVE_CIRCLE_COLOR = Color.green;

    private static final Color UNDER_CURSOR_CIRCLE_COLOR = Color.gray;
    private static final int ACTIVE_HANDLE_CIRCLE_RADIUS_ADD = 3;

    private static final int CONTROL_POINT_RADIUS = 4;
    private static final int HANDLE_POINT_RADIUS = 3;

    private static final Color CONTROL_POINT_COLOR = Color.black;
    private static final Color HANDLE_POINT_COLOR = MY_PURPLE;

    static {
        RADIUS_MAP = new HashMap<Class<? extends Point>, Integer>(10);
        COLOR_MAP = new HashMap<Class<? extends Point>, Color>(10);

        RADIUS_MAP.put(CurveControlPoint.class, CONTROL_POINT_RADIUS);
        RADIUS_MAP.put(CornerControlPoint.class, CONTROL_POINT_RADIUS);
        RADIUS_MAP.put(SmoothControlPoint.class, CONTROL_POINT_RADIUS);
        RADIUS_MAP.put(SymmetricControlPoint.class, CONTROL_POINT_RADIUS);
        RADIUS_MAP.put(HandlePoint.class, HANDLE_POINT_RADIUS);

        COLOR_MAP.put(CurveControlPoint.class, CONTROL_POINT_COLOR);
        COLOR_MAP.put(CornerControlPoint.class, CONTROL_POINT_COLOR);
        COLOR_MAP.put(SmoothControlPoint.class, CONTROL_POINT_COLOR);
        COLOR_MAP.put(SymmetricControlPoint.class, CONTROL_POINT_COLOR);
        COLOR_MAP.put(HandlePoint.class, HANDLE_POINT_COLOR);


    }


    public static void draw(final Point point, Graphics2D g2D) {
        if (RADIUS_MAP.get(point.getClass()) == null) {
            throw new IllegalArgumentException("Point to draw must be control or handle!");
        }

        g2D.setColor(COLOR_MAP.get(point.getClass()));
        g2D.fillOval(((int) point.getX()) - RADIUS_MAP.get(point.getClass()),
                ((int) point.getY()) - RADIUS_MAP.get(point.getClass()),
                RADIUS_MAP.get(point.getClass()) * 2,
                RADIUS_MAP.get(point.getClass()) * 2);
    }

    public static void drawUnderCursorCircle(final Point point, Graphics2D g2D) {
        g2D.setColor(UNDER_CURSOR_CIRCLE_COLOR);
        int addRadius = ACTIVE_CIRCLE_RADIUS_ADD;
        if (point instanceof HandlePoint) {
            addRadius = ACTIVE_HANDLE_CIRCLE_RADIUS_ADD;
        }


        int radius = RADIUS_MAP.get(point.getClass()) + addRadius;
        g2D.fillOval(((int) point.getX()) - radius,
                ((int) point.getY()) - radius, radius * 2, radius * 2);
    }

    public static void drawActivePointCircle(final Point point, Graphics2D g2D) {
        g2D.setColor(ACTIVE_CIRCLE_COLOR);

        int radius = RADIUS_MAP.get(point.getClass()) + ACTIVE_CIRCLE_RADIUS_ADD;
        g2D.fillOval(((int) point.getX()) - radius,
                ((int) point.getY()) - radius, radius * 2, radius * 2);
    }
}