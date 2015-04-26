package spbstu.cg.fonteditor.model.action;

/**
 * Created by Egor Gorbunov on 26.04.2015.
 * Email: egor-mailbox@ya.ru
 * Github username: egorbunov
 */
public abstract class ModelAction {
    public abstract void undo();
    public abstract void redo();
}
