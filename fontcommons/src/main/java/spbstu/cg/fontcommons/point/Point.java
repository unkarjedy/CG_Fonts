package spbstu.cg.fontcommons.point;

import java.io.Serializable;

/**
 * Created by Egor Gorbunov on 05.03.2015.
 * Email: egor_mailbox@ya.ru
 * Github username: egorbunov
 */
public class Point implements Serializable{
    public static final int WEIGHT_MIN = 0;
    public static final int WEIGHT_MAX = 10;
    public static final int WEIGHT_STANDART = 1;
    public static float WEIGHT_MIN_PRACTICE = 0.1f;
    protected float x;
    protected float y;
    protected PointType type;

    public float getWeight() {
        return w;
    }

    public void setWeight(float w) {
        this.w = w;
    }

    protected float w = 1; // weight

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void move(float dx, float dy) {
        x += dx;
        y += dy;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }


    public PointType getType() {
        return type;
    }

    public void setType(PointType newType) {
        type = newType;
    }

    public Point newTransform(int dx, int dy, int scale) {
        float x = this.x * scale + dx;
        float y = this.y * scale + dy;
        return new Point(x, y);
    }
}
