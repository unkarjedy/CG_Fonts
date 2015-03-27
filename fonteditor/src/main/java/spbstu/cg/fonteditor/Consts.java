package spbstu.cg.fonteditor;

import spbstu.cg.fontcommons.point.*;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Egor Gorbunov on 24.03.2015.
 * Email: egor-mailbox@ya.ru
 * Github username: egorbunov
 */
public class Consts {
    public final static float DISTANCE_EPS = 10.0f;
    public final static Map<PointType, String > pointTypeNamesMap;

    static {
        pointTypeNamesMap = new LinkedHashMap<PointType, String>(5);
        pointTypeNamesMap.put(PointType.CUSP,  "Corner");
        pointTypeNamesMap.put(PointType.CORNER, "Cusp");
        pointTypeNamesMap.put(PointType.SMOOTH, "Smooth");
        pointTypeNamesMap.put(PointType.SYMMETRIC, "Symmetrical");
    }
}
