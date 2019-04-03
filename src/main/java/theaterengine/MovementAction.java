package theaterengine;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import theaterengine.script.statement.RoleOneDirectionMoveStatement;

/**
 *
 * @author hundun
 * Created on 2019/04/01
 */
public class MovementAction extends TimerTask{
	private static final Logger logger = LoggerFactory.getLogger(MovementAction.class);
	
	public static final int period = 10;
	public static final int frequency = 1000 / period;
	
	MoveableItem mover;
	int times;
	double speedX;
	double speedY;
	private StagePanel stagePanel;
	private boolean work = true;
	int delay;
	Timer timer;
	
	public MovementAction(StagePanel stagePanel, MoveableItem mover, double speedX, double speedY, int times, int delay) {
		this.stagePanel = stagePanel;
		this.mover = mover;
		this.speedX = speedX;
		this.speedY = speedY;
		this.times = times;
		this.delay = delay;
	}
	
	@Override
	public String toString() {
		return "mover=" + mover.name + ",speedX=" + speedX + ",speedY=" + speedY + ",times=" + times;
	}

	@Override
	public void run() {
		if(work) {
			double x0 = mover.x;
			double y0 = mover.y;
			mover.move(speedX, speedY);
			double x1 = mover.x;
			double y1 = mover.y;
			//logger.info("{} {}从({},{})移动到({},{})", new Object[]{System.currentTimeMillis(), mover.name, String.valueOf(x0), String.valueOf(y0), String.valueOf(x1), String.valueOf(y1)});
			times --;
			work = times > 0;
		} else {
			if (timer != null) {
				timer.cancel();
				System.out.println("最后一个action执行完毕");
			}
		}
		stagePanel.updateUI();
	}
	

}
