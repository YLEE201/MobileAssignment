package org.yuhonglee.gcu.mobileassignment.MapModules;
//Name.Yu Hong Lee
//StD No.S1620580

import java.util.List;

public interface  DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}
