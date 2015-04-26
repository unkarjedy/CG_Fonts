package spbstu.cg.fonteditor.model.action;

import java.util.LinkedList;

/**
 * Created by Egor Gorbunov on 26.04.2015.
 * Email: egor-mailbox@ya.ru
 * Github username: egorbunov
 */
public class ActionStack {
    LinkedList<ModelAction> undoStack;
    LinkedList<ModelAction> redoStack;

    public ActionStack() {
        undoStack = new LinkedList<>();
        redoStack = new LinkedList<>();
    }

    public void addAction(ModelAction action) {
        undoStack.push(action);
    }

    public void undo() {
        if (undoStack.size() > 0) {
            ModelAction action = undoStack.pop();
            action.undo();
            redoStack.push(action);
        }
    }

    public void redo() {
        if (redoStack.size() > 0) {
            ModelAction action = redoStack.pop();
            action.redo();
            undoStack.push(action);
        }
    }
}
