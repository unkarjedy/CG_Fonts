package spbstu.cg.fonteditor;

import spbstu.cg.fontcommons.point.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Egor Gorbunov on 24.03.2015.
 * Email: egor-mailbox@ya.ru
 * Github username: egorbunov
 */
public class Consts {
    public final static float DISTANCE_EPS = 10.0f;
    public final static Map<Class<? extends Point>, String > pointTypeMap;

    static {
        pointTypeMap = new LinkedHashMap<Class<? extends Point>, String>(5);
        pointTypeMap.put(CornerControlPoint.class, "Corner");
        pointTypeMap.put(CurveControlPoint.class, "Cusp");
        pointTypeMap.put(SmoothControlPoint.class, "Smooth");
        pointTypeMap.put(SymmetricControlPoint.class, "Symmetrical");
    }
}
