package theaterengine.script.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import theaterengine.MessageBus;
import theaterengine.Role;
import theaterengine.RoleFactory;
import theaterengine.Scene;
import theaterengine.script.statement.Keyword;
import theaterengine.script.statement.RoleOneDirectionMoveStatement;
import theaterengine.script.statement.RoleSpeakStatement;
import theaterengine.script.tool.ScalaTool;

/**
 * @author hundun
 * Created on 2019/12/29
 */
public class RoleOneDirectionMoveTask extends ConditionTask {

    private static final Logger logger = LoggerFactory.getLogger(RoleOneDirectionMoveStatement.class);
    
    
    
    Role role;
    private Double targetX;
    private Double targetY;
    private double speed;
    
    public RoleOneDirectionMoveTask(RoleOneDirectionMoveStatement statement, Scene parent, String condition, String doneFlag) {
        super(parent, condition, doneFlag);
        this.role = RoleFactory.get(statement.argName);
        this.speed = Keyword.get(statement.argSpeed).getDoubleValue();
        String argDirection = statement.argDirection1;
        String argDistance = statement.argDistance1;
        
        
        switch (Keyword.get(argDirection)) {
        case FORWARD:
        case BACKWARD:
            targetX = null;
            targetY = ScalaTool.meterToPixel(Double.valueOf(argDistance));
            break;
        case LEFT:
        case RIGHT:
            targetX = ScalaTool.meterToPixel(Double.valueOf(argDistance));
            targetY = null;
            break;  
        default:
            targetX = null;
            targetY = null;
            logger.error("Keyword.get({})未定义行为", argDirection);
        }
    }

    @Override
    public void clockArrive() {
        if (waiting && isReady()) {
            int frameMs = 100; 
            double speedPerClock = ScalaTool.meterToPixel(speed/ (1000 / frameMs));
            role.setTarget(targetX, targetY, speedPerClock);
            System.out.println("role.setTarget");
            timer.scheduleAtFixedRate(this, 0, frameMs);
            waiting = false;
        }
    }

    @Override
    public void run() {
        if (role.isMoving()) {
            role.moveClockArrive();
            if (!role.isMoving()) {
                if (doneFlag != null) {
                    MessageBus.addFlag(doneFlag);
                }
            }
        }

    }

}
