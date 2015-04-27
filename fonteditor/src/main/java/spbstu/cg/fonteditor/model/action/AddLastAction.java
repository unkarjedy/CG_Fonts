package spbstu.cg.fonteditor.model.action;

import spbstu.cg.fontcommons.point.ControlPoint;
import spbstu.cg.fontcommons.spline.Spline;
import spbstu.cg.fonteditor.model.LetterEditorModel;

/**
 * Created by Egor Gorbunov on 26.04.2015.
 * Email: egor-mailbox@ya.ru
 * Github username: egorbunov
 */
public class AddLastAction extends ModelAction {

    private LetterEditorModel model;
    final ControlPoint point;

    public AddLastAction(LetterEditorModel model, ControlPoint point) {
        this.model = model;
        this.point = point;
    }

    public void undo() {
        Spline activeSpline = model.getCurrentSpline();
        activeSpline.deleteLastControlPoint();
        model.activatePoint(null);
    }

    public void redo() {
        Spline activeSpline = model.getCurrentSpline();
        activeSpline.addControlPoint(point);
        model.activatePoint(point);
    }
}
