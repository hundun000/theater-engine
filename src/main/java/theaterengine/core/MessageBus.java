package theaterengine.core;
/**
 * @author hundun
 * Created on 2019/12/26
 */

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MessageBus {
    
    public static final String START_EVENT_ID = "开幕";
    
    private static Set<String> happenedEventIds = new HashSet<>();

    
    
    public static void addFlag(List<String> doneFlag) {
        happenedEventIds.addAll(doneFlag);
    }
    
    public static boolean hasEvent(String flag) {
        return happenedEventIds.contains(flag);
    }

    public static void restart() {
        happenedEventIds.clear();
        happenedEventIds.add(START_EVENT_ID);
    }

}
