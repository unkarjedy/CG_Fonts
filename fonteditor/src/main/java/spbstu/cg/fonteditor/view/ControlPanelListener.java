package spbstu.cg.fonteditor.view;

import spbstu.cg.fontcommons.point.Point;
import spbstu.cg.fontcommons.point.PointType;

/**
 * Created by Egor Gorbunov on 26.03.2015.
 * Email: egor-mailbox@ya.ru
 * Github username: egorbunov
 */
public interface ControlPanelListener {
    void pointTypeChanged(PointType newType);
    void pointWeightChanged(float weight);
}
