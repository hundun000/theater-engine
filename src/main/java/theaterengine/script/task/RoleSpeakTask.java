package theaterengine.script.task;

import java.util.Timer;

import theaterengine.MessageBus;
import theaterengine.Role;
import theaterengine.RoleFactory;
import theaterengine.Scene;
import theaterengine.script.statement.DelayStatement;
import theaterengine.script.statement.RoleSpeakStatement;

/**
 * @author hundun
 * Created on 2019/12/26
 */
public class RoleSpeakTask extends ConditionTask{

    public static double SPEAK_SPEED = 0.5;
    
    String fullWord;
    int delay = 0;
    String doneFlag;
    
    Role role;
    
    public RoleSpeakTask(RoleSpeakStatement statement, Scene parent) {
        super(parent, statement.getArgs()[1]);
        
        this.role = RoleFactory.get(statement.getArgs()[2]);
        this.fullWord = statement.getArgs()[4];
        this.doneFlag = statement.getArgs().length > 6 ? statement.getArgs()[6] : null;
        
        
    }
    
    @Override
    public void clockArrive() {
        if (waiting && isReady()) {
            role.setSpeaking(fullWord);
            timer.scheduleAtFixedRate(this, 0, 1000);
            waiting = false;
            
        }
    }

    @Override
    public void run() {
        delay++;
        if (delay >= fullWord.length() * SPEAK_SPEED) {
            if (doneFlag != null) {
                MessageBus.addFlag(doneFlag);
            }
            System.out.println("done, add flag : " + doneFlag);
            role.setSpeaking("");
            timer.cancel();
            parent.conditionTasks.remove(this);
        }
    }

}
