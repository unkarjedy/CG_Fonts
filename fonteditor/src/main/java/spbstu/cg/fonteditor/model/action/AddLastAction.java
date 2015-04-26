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
    final Spline spline;
    final ControlPoint point;

    public AddLastAction(LetterEditorModel model, Spline spline, ControlPoint point) {
        this.model = model;
        this.spline = spline;
        this.point = point;
    }

    public void undo() {
        spline.deleteLastControlPoint();
        model.activatePoint(null);
    }

    public void redo() {
        spline.addControlPoint(point);
        model.activatePoint(point);
    }
}
