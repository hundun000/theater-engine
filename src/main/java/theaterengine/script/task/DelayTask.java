package theaterengine.script.task;

import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import theaterengine.core.MessageBus;
import theaterengine.core.Scene;
import theaterengine.gui.MainFrame;
import theaterengine.script.statement.DelayStatement;

/**
 * @author hundun
 * Created on 2019/12/26
 */
public class DelayTask extends ConditionTask{
    private static final Logger logger = LoggerFactory.getLogger(DelayTask.class);

    int delay;
    int counter = 0;
    
    
    
    public DelayTask(DelayStatement delayStatement, Scene parent, String condition, String doneFlag) {
        super(parent, condition, doneFlag);

        this.delay = delayStatement.delay;
    }

    @Override
    public void run() {
        counter++;
        logger.debug("DelayTask run counter = " + counter);
        if (counter == delay) {
            if (doneFlag != null) {
                MessageBus.addFlag(doneFlag);
            }
            logger.debug("done, add flag : " + doneFlag);
            timer.cancel();
            parent.conditionTasks.remove(this);
        }
    }

    @Override
    public void clockArrive() {
        if (waiting && isReady()) {
            timer.scheduleAtFixedRate(this, 0, 1000);
            waiting = false;
        }
    }

}
