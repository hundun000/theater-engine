package theaterengine.script.task;

import java.util.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import theaterengine.core.MessageBus;
import theaterengine.core.Scene;
import theaterengine.entity.Role;
import theaterengine.entity.RoleFactory;
import theaterengine.script.statement.DelayStatement;
import theaterengine.script.statement.RoleOneDirectionMoveStatement;
import theaterengine.script.statement.RoleSpeakStatement;

/**
 * @author hundun
 * Created on 2019/12/26
 */
public class RoleSpeakTask extends ConditionTask{

    private static final Logger logger = LoggerFactory.getLogger(RoleSpeakTask.class);
    
    
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
            logger.debug("done, add flag : " + doneFlag);
            role.setSpeaking("");
            timer.cancel();
            parent.conditionTasks.remove(this);
        }
    }

}
