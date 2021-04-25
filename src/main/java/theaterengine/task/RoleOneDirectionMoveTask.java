package theaterengine.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import theaterengine.core.MessageBus;
import theaterengine.core.World;
import theaterengine.entity.Role;
import theaterengine.script.statement.TokenType;
import theaterengine.script.statement.RoleOneDirectionMoveStatement;
import theaterengine.script.statement.RoleTalkStatement;
import theaterengine.script.tool.ScalaTool;
import theaterengine.task.ConditionTask.State;

/**
 * @author hundun
 * Created on 2019/12/29
 */
public class RoleOneDirectionMoveTask extends ConditionTask {

    private static final Logger logger = LoggerFactory.getLogger(RoleOneDirectionMoveStatement.class);
    
    
    
    Role role;
    private Double targetX;
    private Double targetY;
    private double speedOfMeterPerSecond;
    
    public RoleOneDirectionMoveTask(RoleOneDirectionMoveStatement statement, World parent) {
        super(parent, statement.lineNumber, statement.condition, statement.doneEvents);
        this.role = parent.getRole(statement.argName);
        this.speedOfMeterPerSecond = statement.argSpeed;
        this.targetX = statement.targetX;
        this.targetY = statement.targetY;
        
        
        
    }

    @Override
    public void clockArrive() {
        switch (state) {
            case WAITING:
                if (isReadyForStart()) {
                    double speedOfMeterPerFrame = speedOfMeterPerSecond / parent.updateFrameNumPerSecond;
                    double speedOfPixelPerFrame = ScalaTool.meterToPixel(speedOfMeterPerFrame);
                    role.moveComponent.setTarget(targetX, targetY, speedOfPixelPerFrame);
                    
                    stateTransition(State.RUNNING, "speedOfPixelPerFrame = " + speedOfPixelPerFrame);     
                }
                break;
            case RUNNING:
                if (role.moveComponent.needMove()) {
                    role.moveComponent.moveClockArrive();
                } else {
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
//            int frameMs = 100; 
//            double speedPerClock = ScalaTool.meterToPixel(speed/ (1000 / frameMs));
//            role.setTarget(targetX, targetY, speedPerClock);
//            logger.debug("role.setTarget");
//            timer.scheduleAtFixedRate(this, 0, frameMs);
//            waiting = false;
//        }
//    }
//
//    @Override
//    public void run() {
//        if (role.isMoving()) {
//            role.moveClockArrive();
//            if (!role.isMoving()) {
//                if (doneFlag != null) {
//                    MessageBus.addFlag(doneFlag);
//                }
//            }
//        }
//
//    }

}
