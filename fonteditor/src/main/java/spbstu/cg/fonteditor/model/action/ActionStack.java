package spbstu.cg.fonteditor.model.action;

import com.sun.javafx.sg.prism.NGShape;

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
        redoStack.clear();
    }

    /**
     *
     * @return name of the action, that was undone
     */
    public String undo() {
        if (undoStack.size() > 0) {
            ModelAction action = undoStack.pop();
            action.undo();
            redoStack.push(action);
            return action.name();
        }
        return "no action to undo";
    }

    /**
     * @return name of the action that was redone
     */
    public String redo() {
        if (redoStack.size() > 0) {
            ModelAction action = redoStack.pop();
            action.redo();
            undoStack.push(action);
            return action.name();
        }
        return "no action to redo";
    }
}
