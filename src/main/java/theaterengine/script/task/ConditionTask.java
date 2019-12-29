package theaterengine.script.task;

import java.util.Timer;
import java.util.TimerTask;

import theaterengine.MessageBus;
import theaterengine.Scene;

/**
 * @author hundun
 * Created on 2019/12/26
 */
public abstract class ConditionTask extends TimerTask {
    
    protected boolean waiting = true;
    
    private String condition;
    protected Scene parent;
    protected Timer timer;
    
    public ConditionTask(Scene parent, String condition) {
        this.parent = parent;
        this.timer = new Timer();
        this.condition = condition;
    }
    
    public boolean isReady() {
        if (condition == null) {
            return true;
        } else {
            return MessageBus.hasFlag(condition);
        }
    }

    public abstract void clockArrive();

}
