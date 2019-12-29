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
    String doneFlag;
    
    public RoleOneDirectionMoveTask(RoleOneDirectionMoveStatement statement, Scene parent) {
        super(parent, statement.getArgs()[1]);
        this.role = RoleFactory.get(statement.getArgs()[2]);
        this.speed = Keyword.get(statement.getArgs()[3]).getDoubleValue();
        String argDirection = statement.getArgs()[4];
        String argDistance = statement.getArgs()[5];
        this.doneFlag = statement.getArgs().length > 7 ? statement.getArgs()[7] : null;
        
        
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
