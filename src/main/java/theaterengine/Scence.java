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
import theaterengine.script.statement.RoleMoveStatement;
import theaterengine.script.statement.ScreenCreateStatement;
import theaterengine.script.statement.ScreenPairMoveStatement;

/**
 *
 * @author hundun
 * Created on 2019/04/01
 */
public class Scence extends Timer{
	private static final Logger logger = LoggerFactory.getLogger(Scence.class);

	List<MovementAction> movementActions = new ArrayList<>();
	
	public Scence(StagePanel stagePanel, List<String> docs, Parser parser) {
		
		RoleFactory.registerAndAddToStage(new Role("成濑", Stage.positionZeroX, Stage.positionZeroY), stagePanel);
		
		int delay = 0;
        for (int i = 0; i < docs.size(); i++) {
        	String line = docs.get(i);
        	String[] items = getItemsByScript(line);
        	if (items == null || items.length == 0) {
        		continue;
        	}
        	StatementType type = parser.parse(items);
        	if (type == null) {
        		logger.warn("第{}行 语法错误：{}", i, line);
        		continue;
        	}
        	switch (type) {
        	case ROLE_MOVE:
				RoleMoveStatement roleMoveStatement = new RoleMoveStatement(items);
		        addMovementActions(roleMoveStatement.toMoveActions(delay, stagePanel));
				break;
			case SCREEN_PAIR_MOVE:
				ScreenPairMoveStatement screenPairStatement = new ScreenPairMoveStatement(items);
		        addMovementActions(screenPairStatement.toMoveActions(delay, stagePanel));
				break;
			case DELAY:
				DelayStatement delayStatement = new DelayStatement(items);
				delay += delayStatement.getDelay();
				break;
			case SCREEN_CTEATE:
				ScreenCreateStatement screenCreateStatement = new ScreenCreateStatement(items);
				screenCreateStatement.work(stagePanel);
				break;
			
			default:
				logger.error("未定义语句行为：{}", type);
				break;
			}
        }
	}
	
	private String[] getItemsByScript(String line) {
		line.trim();
		if(line.startsWith(Keyword.COMMENT.getWord())) {
			return null;
		}
		String[] items = line.split(" ");
		List<String> list = new ArrayList<>();
		for (String item : items) {
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
			schedule(action, action.delay, MovementAction.period);
		}
	}

}

	
