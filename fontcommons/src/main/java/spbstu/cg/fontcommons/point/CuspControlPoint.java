package spbstu.cg.fontcommons.point;

/**
 * Created by Dima Naumenko on 28.03.2015.
 */
public class CuspControlPoint extends ControlPoint {
    public CuspControlPoint(float x, float y) {
        super(x, y);
        type = PointType.CUSP;
    }

    @Override
    public void handlePointMoved(int index) {
    }
}