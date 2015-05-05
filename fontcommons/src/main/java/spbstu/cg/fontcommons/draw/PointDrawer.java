package spbstu.cg.fontcommons.draw;

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
    private static final Color MY_PURPLE = new Color(0.64f, 0.0f, 0.25f);
    private static final Color NO_TYPE_COLOR = new Color(1.0f, 0.32941177f, 0.05490196f);

    private static final int NO_TYPE_RAD = 8;

    private static final Map<PointType, Integer> RADIUS_MAP;
    private static final Map<PointType, Color> COLOR_MAP;
    private static final int ACTIVE_CIRCLE_RADIUS_ADD = 4;
    private static final Color ACTIVE_CIRCLE_COLOR = Color.green;

    private static final Color UNDER_CURSOR_CIRCLE_COLOR = Color.gray;
    private static final int ACTIVE_HANDLE_CIRCLE_RADIUS_ADD = 3;

    private static final int CONTROL_POINT_RADIUS = 4;
    private static final int HANDLE_POINT_RADIUS = 3;

    private static final Color CONTROL_POINT_COLOR = Color.black;
    private static final Color HANDLE_POINT_COLOR = MY_PURPLE;

    private static final Color BOUNDING_RECT_POINT_COLOR = new Color(0.1254902f, 0.36078432f, 0.6392157f, 0.7f);

    static {
        RADIUS_MAP = new HashMap<>(10);
        COLOR_MAP = new HashMap<>(10);

        RADIUS_MAP.put(PointType.BOUNDING_RECT_POINT, NO_TYPE_RAD);
        RADIUS_MAP.put(PointType.NO_TYPE, NO_TYPE_RAD);
        RADIUS_MAP.put(PointType.CUSP, CONTROL_POINT_RADIUS);
        RADIUS_MAP.put(PointType.SMOOTH, CONTROL_POINT_RADIUS);
        RADIUS_MAP.put(PointType.SYMMETRIC, CONTROL_POINT_RADIUS);
        RADIUS_MAP.put(PointType.HANDLER, HANDLE_POINT_RADIUS);

        COLOR_MAP.put(PointType.BOUNDING_RECT_POINT, BOUNDING_RECT_POINT_COLOR);
        COLOR_MAP.put(PointType.NO_TYPE, NO_TYPE_COLOR);
        COLOR_MAP.put(PointType.CUSP, CONTROL_POINT_COLOR);
        COLOR_MAP.put(PointType.SMOOTH, CONTROL_POINT_COLOR);
        COLOR_MAP.put(PointType.SYMMETRIC, CONTROL_POINT_COLOR);
        COLOR_MAP.put(PointType.HANDLER, HANDLE_POINT_COLOR);
    }


    public static void draw(final Point point, Graphics2D g2D) {

        if (RADIUS_MAP.get(point.getType()) == null) {
            throw new IllegalArgumentException("Point to draw must be control or handle!");
        }

        g2D.setColor(COLOR_MAP.get(point.getType()));
        g2D.fillOval(((int) point.getX()) - RADIUS_MAP.get(point.getType()),
                ((int) point.getY()) - RADIUS_MAP.get(point.getType()),
                RADIUS_MAP.get(point.getType()) * 2,
                RADIUS_MAP.get(point.getType()) * 2);
    }

    public static void drawUnderCursorCircle(final Point point, Graphics2D g2D) {
        g2D.setColor(UNDER_CURSOR_CIRCLE_COLOR);
        int addRadius = ACTIVE_CIRCLE_RADIUS_ADD;
        if (point instanceof HandlePoint) {
            addRadius = ACTIVE_HANDLE_CIRCLE_RADIUS_ADD;
        }

        if (point.getType().equals(PointType.BOUNDING_RECT_POINT)) {
            g2D.setColor(BOUNDING_RECT_POINT_COLOR);
        }

        int radius = RADIUS_MAP.get(point.getType()) + addRadius;
        g2D.fillOval(((int) point.getX()) - radius,
                ((int) point.getY()) - radius, radius * 2, radius * 2);
    }

    public static void drawActivePointCircle(final Point point, Graphics2D g2D) {
        g2D.setColor(ACTIVE_CIRCLE_COLOR);

        int radius = RADIUS_MAP.get(point.getType()) + ACTIVE_CIRCLE_RADIUS_ADD;
        g2D.fillOval(((int) point.getX()) - radius,
                ((int) point.getY()) - radius, radius * 2, radius * 2);
    }
}
