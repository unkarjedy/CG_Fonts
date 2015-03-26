package spbstu.cg.fonteditor.view;

import spbstu.cg.fontcommons.point.Point;

/**
 * Created by Egor Gorbunov on 26.03.2015.
 * Email: egor-mailbox@ya.ru
 * Github username: egorbunov
 */
public interface ControlPanelListener {
    void pointTypeChanged(Class<? extends Point> newType);
}
