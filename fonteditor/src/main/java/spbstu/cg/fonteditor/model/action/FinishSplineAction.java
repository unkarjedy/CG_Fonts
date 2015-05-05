package spbstu.cg.fonteditor.model.action;

import spbstu.cg.fontcommons.spline.Spline;
import spbstu.cg.fonteditor.model.LetterEditorModel;

/**
 * Created by Egor Gorbunov on 26.04.2015.
 * Email: egor-mailbox@ya.ru
 * Github username: egorbunov
 */
public class FinishSplineAction extends ModelAction {

    private Spline spline;
    private LetterEditorModel model;

    public FinishSplineAction(Spline spline, LetterEditorModel model) {
        this.spline = spline;
        this.model = model;
    }

    @Override
    public String name() {
        return "End spline action";
    }

    @Override
    public void undo() {
        spline.getControlPoints().remove(spline.getControlPoints().size() - 1);
        if (spline.getSize() == 0)
            model.removeLastSpline();
    }

    @Override
    public void redo() {
        spline.addControlPoint(spline.getControlPoints().get(0));
    }
}
