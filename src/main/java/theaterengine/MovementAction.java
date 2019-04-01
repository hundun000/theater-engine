package theaterengine;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import theaterengine.Stage.StagePanel;

/**
 *
 * @author hundun
 * Created on 2019/04/01
 */
public class MovementAction extends TimerTask{
	
	MoveableItem mover;
	int times;
	int speedX;
	int speedY;
	private StagePanel stagePanel;
	private boolean work = true;
	int delay;
	Timer timer;
	
	public MovementAction(StagePanel stagePanel, MoveableItem mover, int speedX, int speedY, int times, int delay) {
		this.stagePanel = stagePanel;
		this.mover = mover;
		this.speedX = speedX;
		this.speedY = speedY;
		this.times = times;
		this.delay = delay;
	}

	@Override
	public void run() {
		if(work) {
			mover.move(speedX, speedY);
			times --;
			work = times > 0;
			stagePanel.updateUI();
		} else {
			if (timer != null) {
				timer.cancel();
				System.out.println("最后一个action执行完毕");
			}
		}
	}
	

}
