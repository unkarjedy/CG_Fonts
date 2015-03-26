package spbstu.cg.fonteditor.model;

import spbstu.cg.font.Letter;
import spbstu.cg.spline.Spline;
import spbstu.cg.spline.point.ControlPoint;
import spbstu.cg.spline.point.Point;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Egor on 06.03.2015.
 * Email: egor_mailbox@ya.ru
 * Github username: egorbunov
 *
 * Model, which stores and manipulates all data connected to currently edited letter
 *
 * TODO: add constructor
 */
public class LetterEditorModel {

    /**
     * Active letter for editing
     */
    private Letter currentLetter;

    /**
     * Not yet completed spline
     */
    private Spline activeSpline;

    /**
     * Currently active point TODO: DO WE NEED IT?
     */
    private Point activePoint = null;

    public void moveActivePoint(Point moveVector) {
        if (activePoint == null)
            return; // TODO: new NullPointerException() would be better I think, but...

        activePoint.move(moveVector);
    }

    /**
     * Method, which tries to find a point near given coordinates
     * and activates is (sets as an active point)
     * @param point
     */
    public void activatePoint(Point point) {
        // TODO: implement
    }


    /**
     * Simply changes class of the active point
     *
     * @param newPointType new type - the Class which must extend {@link spbstu.cg.spline.point.ControlPoint}.
     */
    public void changeActivePointType(Class<? extends ControlPoint> newPointType) {
        if (activePoint == null)
            throw new NullPointerException();

        // TODO: Oh yeah, baby. Love reflection. Do we need this?
        try {
            activePoint = newPointType.getDeclaredConstructor(int.class, int.class).
                    newInstance(activePoint.getX(), activePoint.getY());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
