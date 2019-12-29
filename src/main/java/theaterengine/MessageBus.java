package theaterengine;
/**
 * @author hundun
 * Created on 2019/12/26
 */

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MessageBus {
    
    private static Set<String> flags = ConcurrentHashMap.<String> newKeySet();
    static {
        reset();
    }
    
    
    public static void addFlag(String flag) {
        flags.add(flag);
    }
    
    public static boolean hasFlag(String flag) {
        return flags.contains(flag);
    }

    public static void reset() {
        flags.clear();
        flags.add("第一幕开幕");
    }

}
