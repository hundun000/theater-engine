package theaterengine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.checkerframework.checker.units.qual.m;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import theaterengine.script.Parser;
import theaterengine.script.StatementType;
import theaterengine.script.statement.DelayStatement;
import theaterengine.script.statement.Keyword;
import theaterengine.script.statement.RoleCreateStatement;
import theaterengine.script.statement.RoleOneDirectionMoveStatement;
import theaterengine.script.statement.RoleTwoDirectionMoveStatement;
import theaterengine.script.statement.ScreenCreateStatement;
import theaterengine.script.statement.ScreenPairMoveStatement;

/**
 *
 * @author hundun
 * Created on 2019/04/01
 */
public class Scene extends Timer{
	private static final Logger logger = LoggerFactory.getLogger(Scene.class);

	List<MovementAction> movementActions = new ArrayList<>();
	
	public Scene(StagePanel stagePanel, List<String> docs, Parser parser) {
		
		int delay = 0;
        for (int i = 0; i < docs.size(); i++) {
        	String line = docs.get(i);
        	String[] args = getArgsItemsByScript(line);
        	if (args == null || args.length == 0) {
        		continue;
        	}
        	StatementType type = parser.parse(args);
        	if (type == null) {
        		logger.warn("第{}行 语法错误：{}", i, line);
        		continue;
        	}
        	switch (type) {
        	case ROLE_ONE_DIRECTION_MOVE:
				RoleOneDirectionMoveStatement roleMoveStatement = new RoleOneDirectionMoveStatement(args);
				addMovementActions(roleMoveStatement.toMoveActions(delay + roleMoveStatement.getDelay(), stagePanel));
				break;
        	case ROLE_TWO_DIRECTION_MOVE:
				RoleTwoDirectionMoveStatement roleTwoDirectionMoveStatement = new RoleTwoDirectionMoveStatement(args);
				addMovementActions(roleTwoDirectionMoveStatement.toMoveActions(delay + roleTwoDirectionMoveStatement.getDelay(), stagePanel));
				break;
			case SCREEN_PAIR_MOVE:
				ScreenPairMoveStatement screenPairStatement = new ScreenPairMoveStatement(args);
		        addMovementActions(screenPairStatement.toMoveActions(delay, stagePanel));
				break;
			case DELAY:
				DelayStatement delayStatement = new DelayStatement(args);
				delay += delayStatement.getDelay();
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
	
	private String[] getArgsItemsByScript(String line) {
		line.trim();
		if(line.startsWith(Keyword.COMMENT.getWord())) {
			return null;
		}
		String[] args= line.split(" ");
		List<String> list = new ArrayList<>();
		for (String item : args) {
			if (item.length() > 0) {
				list.add(item);
			}
		}
		return list.toArray(new String[list.size()]);
	}

	public void addMovementActions(List<MovementAction> movementActions) {
		for (MovementAction movementAction : movementActions) {
			addMovementAction(movementAction);
		}
	}
	
	public void addMovementAction(MovementAction movementAction) {
		movementActions.add(movementAction);
		movementAction.timer = this;
		if (movementActions.size() >= 2) {
			movementActions.get(movementActions.size() - 2).timer = null;
		}
	}
	
	
	public void start() {
		for (MovementAction action : movementActions) {
			scheduleAtFixedRate(action, action.delay, MovementAction.period);
		}
	}

}

	
