package theaterengine.script.task;

import java.util.TimerTask;

import theaterengine.MessageBus;

/**
 * @author hundun
 * Created on 2019/12/26
 */
public abstract class ConditionTask extends TimerTask {
    
    protected boolean waiting = true;
    
    protected String condition;
    
    public boolean isReady() {
        if (condition == null) {
            return true;
        } else {
            return MessageBus.hasFlag(condition);
        }
    }

    public abstract void clockArrive();

}
