package theaterengine;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.checkerframework.checker.units.qual.m;

import theaterengine.Stage.StagePanel;

/**
 *
 * @author hundun
 * Created on 2019/04/01
 */
public class Scence extends Timer{

	List<MovementAction> movementActions = new ArrayList<>();
	long delay = 0;
	
	boolean done = false;
	int times = 0;
	private long period = 100;
	
	public Scence() {
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

	
