package theaterengine.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.checkerframework.checker.units.qual.m;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import theaterengine.gui.StagePanel;
import theaterengine.script.Parser;
import theaterengine.script.StatementType;
import theaterengine.script.statement.DelayStatement;
import theaterengine.script.statement.Keyword;
import theaterengine.script.statement.RoleCreateStatement;
import theaterengine.script.statement.RoleOneDirectionMoveStatement;
import theaterengine.script.statement.RoleSpeakStatement;
import theaterengine.script.statement.ScreenCreateStatement;
import theaterengine.script.task.ConditionTask;
import theaterengine.script.task.DelayTask;
import theaterengine.script.task.RoleOneDirectionMoveTask;
import theaterengine.script.task.RoleSpeakTask;


/**
 *
 * @author hundun
 * Created on 2019/04/01
 */
public class Scene extends TimerTask {
	private static final Logger logger = LoggerFactory.getLogger(Scene.class);

	List<MovementAction> movementActions = new ArrayList<>();
	public List<ConditionTask> conditionTasks = new LinkedList<>();
	
	private StagePanel stagePanel;
	
	private String removeCondition(List<String> args) {
	    if (args.size() > 2 && Keyword.isMatch(args.get(0), Keyword.WHEN) &&  Keyword.isMatch(args.get(1), Keyword.VAR)) {
	        args.remove(0);
	        return args.remove(0);
	    } else {
	        return null;
	    }
	}
	
	private String removeFlag(List<String> args) {
	    int size = args.size();
        if (size > 2 && Keyword.isMatch(args.get(size - 2), Keyword.THEN_SET) &&  Keyword.isMatch(args.get(size - 1), Keyword.VAR)) {
            args.remove(size - 2);
            size--;
            return args.remove(size - 1);
        } else {
            return null;
        }
    }
	
	public Scene(StagePanel stagePanel, List<String> docs, Parser parser) {
		this.stagePanel = stagePanel;
	    
		int autoFlagIndex = 0;
		for (int i = 0; i < docs.size(); i++) {
        	String line = docs.get(i);
        	List<String> args = getArgsItemsByScript(line);
        	if (args == null || args.size() == 0) {
        		continue;
        	}
        	
        	String condition = removeCondition(args);
        	String doneFlag = removeFlag(args);
        	
        	if (condition == null) {
        	    condition = "Auto" + autoFlagIndex;
        	}
        	autoFlagIndex ++;
        	if (doneFlag == null) {
        	    doneFlag = "Auto" + autoFlagIndex;
        	} else {
        	    doneFlag += ",Auto" + autoFlagIndex;
        	}
        	logger.info("line {}: condition={}, doneFlag={}, remain={}", i, condition, doneFlag, args);
        	
        	
        	StatementType type = parser.parse(args.toArray(new String[args.size()]));
        	
        	
        	
        	if (type == null) {
        		logger.warn("第{}行 语法错误：{}", i, line);
        		continue;
        	}
        	switch (type) {
        	case ROLE_ONE_DIRECTION_MOVE:
				RoleOneDirectionMoveStatement roleMoveStatement = new RoleOneDirectionMoveStatement(args);
				RoleOneDirectionMoveTask roleOneDirectionMoveTask = new RoleOneDirectionMoveTask(roleMoveStatement, this, condition, doneFlag);
                conditionTasks.add(roleOneDirectionMoveTask);
				break;
        	case ROLE_TWO_DIRECTION_MOVE:
				
				break;
			case SCREEN_PAIR_MOVE:
				
				break;
			case DELAY:
				DelayStatement delayStatement = new DelayStatement(args);
				DelayTask delayTask = new DelayTask(delayStatement, this, condition, doneFlag);
				conditionTasks.add(delayTask);
				break;
			case ROLE_SPEAK:
			    RoleSpeakStatement roleSpeakStatement = new RoleSpeakStatement(args);
			    RoleSpeakTask roleSpeakTask = new RoleSpeakTask(roleSpeakStatement, this, condition, doneFlag);
			    conditionTasks.add(roleSpeakTask);
			    break;
			case SCREEN_CTEATE:
				ScreenCreateStatement screenCreateStatement = new ScreenCreateStatement(args);
				screenCreateStatement.work(stagePanel);
				break;
			case ROLE_CTEATE:
				RoleCreateStatement roleCreateStatement = new RoleCreateStatement(args);
				roleCreateStatement.work(stagePanel);
				break;
			default:
				logger.error("未定义语句行为：{}", type);
				break;
			}
        }
	}
	
	private List<String> getArgsItemsByScript(String line) {
		line.trim();
		if(line.startsWith(Keyword.COMMENT.getWord())) {
			return null;
		}
		String[] args= line.split(" ");
		List<String> list = new LinkedList<>();
		for (String item : args) {
			if (item.length() > 0) {
				list.add(item);
			}
		}
		return list;
	}
	


    @Override
    public void run() {
        for (ConditionTask conditionTask : conditionTasks) {
            conditionTask.clockArrive();
        }
        stagePanel.repaint();
    }

}

	
