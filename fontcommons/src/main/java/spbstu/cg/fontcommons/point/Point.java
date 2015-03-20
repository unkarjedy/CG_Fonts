package spbstu.cg.fontcommons.point;

/**
 * Created by Egor Gorbunov on 05.03.2015.
 * Email: egor_mailbox@ya.ru
 * Github username: egorbunov
 */
public class Point {
    private float x;
    private float y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void move(Point moveVector) {
        x += moveVector.getX();
        y += moveVector.getY();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
