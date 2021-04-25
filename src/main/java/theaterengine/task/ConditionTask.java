package theaterengine.task;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import theaterengine.core.MessageBus;
import theaterengine.core.World;
import theaterengine.script.statement.RoleOneDirectionMoveStatement;

/**
 * @author hundun
 * Created on 2019/12/26
 */
public abstract class ConditionTask {
    private static final Logger logger = LoggerFactory.getLogger(ConditionTask.class);
    
    
    public enum State {
        WAITING,
        RUNNING,
        DONE
        ;
    }
    
    protected State state = State.WAITING;
    
    /**
     * 该事件发生时，执行本task
     */
    private String conditionEventId;
    /**
     * 本task结束时，抛出这些事件
     */
    protected List<String> doneEventIds;
    protected World parent;
    protected int fromLineNumber;
    
    public ConditionTask(World parent, int fromLineNumber, String condition, List<String> doneFlag) {
        this.parent = parent;
        this.fromLineNumber = fromLineNumber;
        this.conditionEventId = condition;
        this.doneEventIds = doneFlag;
    }
    
    public boolean isReadyForStart() {
        if (conditionEventId == null) {
            return true;
        } else {
            return MessageBus.hasEvent(conditionEventId);
        }
    }


    public abstract void clockArrive();

    
    protected void stateTransition(State newState, String logMessage) {
        this.state = newState;
        logger.info("[line{}task] state change to {}. {}", this.fromLineNumber, newState, logMessage);      
        if (state == State.DONE) {
            if (doneEventIds != null) {
                MessageBus.addFlag(doneEventIds);
            }
            parent.removeTaskToHistory(this);
        }
    }
}
