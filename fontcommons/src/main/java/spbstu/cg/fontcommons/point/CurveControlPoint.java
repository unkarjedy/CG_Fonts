package spbstu.cg.fontcommons.point;

/**
 * Created by Egor Gorbunov on 24.03.2015.
 * Email: egor-mailbox@ya.ru
 * Github username: egorbunov
 */
public class CurveControlPoint extends ControlPoint {
    public CurveControlPoint(float x, float y) {
        super(x, y);
    }

    @Override
    public void move(float dx, float dy) {
        super.move(dx, dy);

        if (handlePoints != null) {
            for (HandlePoint hp : handlePoints) {
                if (hp == null)
                    continue;
                hp.move(dx, dy);
            }
        }
    }

    @Override
    public void handlePointMoved(int index) {}


}
