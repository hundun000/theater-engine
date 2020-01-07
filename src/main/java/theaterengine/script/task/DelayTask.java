package theaterengine.script.task;

import java.util.Timer;
import java.util.TimerTask;

import theaterengine.MessageBus;
import theaterengine.Scene;
import theaterengine.script.statement.DelayStatement;

/**
 * @author hundun
 * Created on 2019/12/26
 */
public class DelayTask extends ConditionTask{
    
    int delay;
    int counter = 0;
    
    
    
    public DelayTask(DelayStatement delayStatement, Scene parent, String condition, String doneFlag) {
        super(parent, condition, doneFlag);

        this.delay = delayStatement.delay;
    }

    @Override
    public void run() {
        counter++;
        System.out.println("DelayTask run counter = " + counter);
        if (counter == delay) {
            if (doneFlag != null) {
                MessageBus.addFlag(doneFlag);
            }
            System.out.println("done, add flag : " + doneFlag);
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
