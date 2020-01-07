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
    int currentLength = 0;
    
    Role role;
    
    public RoleSpeakTask(RoleSpeakStatement statement, Scene parent, String condition, String doneFlag) {
        super(parent, condition, doneFlag);
        
        this.role = RoleFactory.get(statement.roleName);
        this.fullWord = statement.fullWord;
        
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
        currentLength++;
        if (currentLength >= fullWord.length() * SPEAK_SPEED) {
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
