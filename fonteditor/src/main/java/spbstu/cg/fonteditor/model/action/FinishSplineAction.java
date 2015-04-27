package spbstu.cg.fonteditor.model.action;

import spbstu.cg.fonteditor.model.LetterEditorModel;

/**
 * Created by Egor Gorbunov on 26.04.2015.
 * Email: egor-mailbox@ya.ru
 * Github username: egorbunov
 */
public class FinishSplineAction extends ModelAction {

    private LetterEditorModel model;

    public FinishSplineAction(LetterEditorModel model) {
        this.model = model;
    }

    @Override
    public void undo() {
        model.deleteLastSplineAndActivatePrev();
        model.getCurrentSpline().getControlPoints().remove(
                model.getCurrentSpline().getControlPoints().size() - 1
        );

    }

    @Override
    public void redo() {
        model.endCurrentSplineAct();
    }
}
