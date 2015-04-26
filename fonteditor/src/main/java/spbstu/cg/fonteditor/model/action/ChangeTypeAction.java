package spbstu.cg.fonteditor.model.action;

import spbstu.cg.fontcommons.point.PointType;
import spbstu.cg.fontcommons.spline.Spline;
import spbstu.cg.fonteditor.controller.LetterEditorModelListener;
import spbstu.cg.fonteditor.model.LetterEditorModel;

/**
 * Created by Egor Gorbunov on 26.04.2015.
 * Email: egor-mailbox@ya.ru
 * Github username: egorbunov
 */
public class ChangeTypeAction extends ModelAction {
    private final Spline spline;
    int index;
    PointType newType;
    PointType oldType;
    private LetterEditorModelListener listener;

    public ChangeTypeAction(LetterEditorModelListener listener, Spline activeSpline, int index, PointType oldType, PointType newType) {
        this.listener = listener;

        this.spline = activeSpline;
        this.index = index;
        this.oldType = oldType;
        this.newType = newType;
    }

    @Override
    public void undo() {
        spline.changePointType(index, oldType);
        listener.controlPointTypeChanged(spline.getControlPoints().get(index));
    }

    @Override
    public void redo() {
        spline.changePointType(index, newType);
        listener.controlPointTypeChanged(spline.getControlPoints().get(index));
    }
}
