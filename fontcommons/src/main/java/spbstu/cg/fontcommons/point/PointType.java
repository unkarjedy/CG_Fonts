package spbstu.cg.fontcommons.point;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Dima Naumenko on 28.03.2015.
 */
public enum PointType {
    UNSUPPORTED_TYPE,
    CORNER,
    CUSP,
    SMOOTH,
    SYMMETRIC,
    HANDLER;

    public boolean isControlPointType(){
        return this != UNSUPPORTED_TYPE &&
                this != HANDLER;
    }

    public String getName(){
        return pointTypeNamesMap.get(this);
    }

    private final static Map<PointType, String > pointTypeNamesMap;
    static {
        pointTypeNamesMap = new LinkedHashMap<PointType, String>(5);
        pointTypeNamesMap.put(PointType.CUSP,  "Corner");
        pointTypeNamesMap.put(PointType.CORNER, "Cusp");
        pointTypeNamesMap.put(PointType.SMOOTH, "Smooth");
        pointTypeNamesMap.put(PointType.SYMMETRIC, "Symmetrical");
    }

}
