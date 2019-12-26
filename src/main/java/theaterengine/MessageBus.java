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
        flags.add("第一幕开幕");
    }
    
    
    public static void addFlag(String flag) {
        flags.add(flag);
    }
    
    public static boolean hasFlag(String flag) {
        return flags.contains(flag);
    }

}
