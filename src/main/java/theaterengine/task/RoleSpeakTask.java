package theaterengine.task;

import java.util.List;
import java.util.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import theaterengine.core.MessageBus;
import theaterengine.core.World;
import theaterengine.entity.Role;
import theaterengine.script.statement.DelayStatement;
import theaterengine.script.statement.RoleOneDirectionMoveStatement;
import theaterengine.script.statement.RoleTalkStatement;
import theaterengine.task.ConditionTask.State;

/**
 * @author hundun
 * Created on 2019/12/26
 */
public class RoleSpeakTask extends ConditionTask{

    private static final Logger logger = LoggerFactory.getLogger(RoleSpeakTask.class);
    
    
    public static double SPEAK_SPEED_WORD_PER_SECONED = 8;
    public static double delaySecondAfterSpeakEnd = 1;
    String fullWord;
    double currentSpeakLength = 0;
    int delay;
    Role role;
    boolean quikShowSpeak = false;
    
    public RoleSpeakTask(RoleTalkStatement statement, World parent) {
        super(parent, statement.lineNumber, statement.condition, statement.doneEvents);
        
        this.role = parent.getRole(statement.roleName);
        this.fullWord = statement.fullWord;
        this.delay = (int) (delaySecondAfterSpeakEnd * parent.updateFrameNumPerSecond);
    }

    @Override
    public void clockArrive() {
        switch (state) {
            case WAITING:
                if (isReadyForStart()) {
                    role.setSpeaking(fullWord);
                    if (quikShowSpeak) {
                        role.setCurrentSpeakEndIndex(fullWord.length());
                    }
                    stateTransition(State.RUNNING, "fullWord = " + fullWord);    
                }
                break;
            case RUNNING:
                double speakSpeedWordPerFrame = SPEAK_SPEED_WORD_PER_SECONED / parent.updateFrameNumPerSecond;
                
                if (currentSpeakLength < fullWord.length()) {
                    currentSpeakLength += speakSpeedWordPerFrame;
                    if (!quikShowSpeak) {
                        role.setCurrentSpeakEndIndex((int)currentSpeakLength);
                    } 
                } else if (delay > 0) {
                    delay--;
                } else {
                    role.clearSpeaking();
                    stateTransition(State.DONE, "doneFlag = " + doneEventIds);
                }
                break;
            default:
                break;
        }
    }
    
//    @Override
//    public void clockArrive() {
//        if (waiting && isReady()) {
//            role.setSpeaking(fullWord);
//            timer.scheduleAtFixedRate(this, 0, 1000);
//            waiting = false;
//            
//        }
//    }
//
//    @Override
//    public void run() {
//        currentLength++;
//        if (currentLength >= fullWord.length() * SPEAK_SPEED) {
//            if (doneFlag != null) {
//                MessageBus.addFlag(doneFlag);
//            }
//            logger.debug("done, add flag : " + doneFlag);
//            role.setSpeaking("");
//            timer.cancel();
//            parent.conditionTasks.remove(this);
//        }
//    }

}
