package spbstu.cg.fonteditor.controller;

import spbstu.cg.fontcommons.point.ControlPoint;
import spbstu.cg.fontcommons.point.Point;

/**
 * Created by Egor Gorbunov on 26.04.2015.
 * Email: egor-mailbox@ya.ru
 * Github username: egorbunov
 */
public interface LetterEditorModelListener {
    void activePointChanged(Point activePoint);
    void controlPointTypeChanged(ControlPoint controlPoint);
}
