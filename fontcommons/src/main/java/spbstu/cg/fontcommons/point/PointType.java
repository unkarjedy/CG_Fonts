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

    PointType get() { return CORNER; }

    public String getName(){
        return pointTypeNamesMap.get(this);
    }

    private final static Map<PointType, String > pointTypeNamesMap;

    static {
        pointTypeNamesMap = new LinkedHashMap<PointType, String>(5);
        pointTypeNamesMap.put(PointType.CUSP,  "Cusp");
        pointTypeNamesMap.put(PointType.CORNER, "Corner");
        pointTypeNamesMap.put(PointType.SMOOTH, "Smooth");
        pointTypeNamesMap.put(PointType.SYMMETRIC, "Symmetrical");
    }

    public static PointType toType(String name) {
        for(Map.Entry<PointType, String> entry : pointTypeNamesMap.entrySet()){
            if(entry.getValue().equals(name)){
                return entry.getKey();
            }
        }

        return null;
    }
}
