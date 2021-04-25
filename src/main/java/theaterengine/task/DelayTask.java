package theaterengine.task;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import theaterengine.core.MessageBus;
import theaterengine.core.World;
import theaterengine.gui.MainFrame;
import theaterengine.script.statement.DelayStatement;

/**
 * @author hundun
 * Created on 2019/12/26
 */
public class DelayTask extends ConditionTask{
    private static final Logger logger = LoggerFactory.getLogger(DelayTask.class);

    int delay;
    protected int countDown;
    
    
    public DelayTask(DelayStatement delayStatement, World parent) {
        super(parent, delayStatement.lineNumber, delayStatement.condition, delayStatement.doneEvents);

        this.delay = delayStatement.delay;
        
    }



//    @Override
//    public void run() {
//        counter++;
//        logger.debug("DelayTask run counter = " + counter);
//        if (counter == delay) {
//            if (doneFlag != null) {
//                MessageBus.addFlag(doneFlag);
//            }
//            logger.debug("done, add flag : " + doneFlag);
//            timer.cancel();
//            parent.conditionTasks.remove(this);
//        }
//    }
//
    @Override
    public void clockArrive() {
        
        switch (state) {
            case WAITING:
                if (isReadyForStart()) {
                    this.countDown = this.delay;
                    stateTransition(State.RUNNING, "this.delay = " + this.delay);
                }
                break;
            case RUNNING:
                if (countDown > 0) {
                    countDown--;
                } else {
                    stateTransition(State.DONE, "doneFlag = " + doneEventIds);     
                }
                break;
            default:
                break;
        }
    }


}
