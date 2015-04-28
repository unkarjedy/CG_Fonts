package spbstu.cg.fonteditor.model.action;

import spbstu.cg.fontcommons.point.Point;
import spbstu.cg.fontcommons.point.PointType;
import spbstu.cg.fontcommons.spline.Spline;
import spbstu.cg.fonteditor.controller.LetterEditorModelListener;

/**
 * Created by Egor Gorbunov on 28.04.2015.
 * Email: egor-mailbox@ya.ru
 * Github username: egorbunov
 */
public class WeightChangeAction extends ModelAction {
    private final Point point;
    float newWeight;
    float oldWeight;
    private LetterEditorModelListener listener;

    public WeightChangeAction(LetterEditorModelListener listener, Point point,
                              float oldWeight,
                              float newWeight) {

        this.listener = listener;
        this.point = point;
        this.oldWeight = oldWeight;
        this.newWeight = newWeight;
    }

    @Override
    public void undo() {
        point.setWeight(oldWeight);
        listener.pointWeightChanged(point);
    }

    @Override
    public void redo() {
        point.setWeight(newWeight);
    }
}
