package theaterengine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.checkerframework.checker.units.qual.m;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import theaterengine.Stage.StagePanel;
import theaterengine.script.Parser;
import theaterengine.script.StatementType;
import theaterengine.script.statement.DelayStatement;
import theaterengine.script.statement.ScreenCreateStatement;
import theaterengine.script.statement.ScreenPairStatement;

/**
 *
 * @author hundun
 * Created on 2019/04/01
 */
public class Scence extends Timer{
	private static final Logger logger = LoggerFactory.getLogger(Scence.class);

	List<MovementAction> movementActions = new ArrayList<>();
	long delay = 0;
	
	boolean done = false;
	int times = 0;
	private long period = 100;
	
	public Scence(List<String> docs, Parser parser) {
		
		int delay = 0;
        for (int i = 0; i < docs.size(); i++) {
        	String line = docs.get(i);
        	String[] items = getItemsByScript(line);
        	StatementType type = parser.parse(items);
        	switch (type) {
			case SCREEN_PAIR_MOVE:
				ScreenPairStatement screenPairStatement = new ScreenPairStatement(items);
		        addMovementActions(screenPairStatement.toMoveActions(delay));
				break;
			case DELAY:
				DelayStatement delayStatement = new DelayStatement(items);
				delay += delayStatement.getDelay();
				break;
			case SCREEN_CTEATE:
				ScreenCreateStatement screenCreateStatement = new ScreenCreateStatement(items);
				screenCreateStatement.work();
				break;
			default:
				logger.warn("第{}行 语法错误：{}", i, line);
				break;
			}
        }
	}
	
	private String[] getItemsByScript(String line) {
		line.trim();
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
			schedule(action, action.delay, period);
		}
	}

}

	
