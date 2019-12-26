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
    String doneFlag;
    Timer timer;
    Scene parent;
    
    public DelayTask(DelayStatement delayStatement, Scene parent) {
        this.timer = new Timer();
        this.parent = parent;
        
        super.condition = delayStatement.getArgs()[1];
        this.delay = (int) (Integer.parseInt(delayStatement.getArgs()[3]));
        this.doneFlag = delayStatement.getArgs().length > 5 ? delayStatement.getArgs()[5] : null;
        
        
    }

    @Override
    public void run() {
        counter++;
        System.out.println("DelayTask run counter = " + counter);
        if (counter == delay) {
            MessageBus.addFlag(doneFlag);
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
