package spbstu.cg.fontcommons.point;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Dima Naumenko on 28.03.2015.
 */
public enum PointType {
    NO_TYPE,
    CUSP,
    SMOOTH,
    SYMMETRIC,
    HANDLER,
    BOUNDING_RECT_POINT;

    public boolean isControlPointType(){
        return this != NO_TYPE &&
                this != HANDLER;
    }

    public String getName(){
        return pointTypeNamesMap.get(this);
    }

    private final static Map<PointType, String > pointTypeNamesMap;

    static {
        pointTypeNamesMap = new LinkedHashMap<>(5);
        pointTypeNamesMap.put(PointType.CUSP,  "Cusp");
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
