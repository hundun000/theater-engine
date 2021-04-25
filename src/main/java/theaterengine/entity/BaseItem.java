package theaterengine.entity;

import java.net.Inet4Address;
import java.util.LinkedList;
import java.util.List;

import javax.sound.midi.Instrument;

import theaterengine.core.World;
import theaterengine.entity.component.MoveComponent;
import theaterengine.task.ConditionTask;

/**
 *
 * @author hundun
 * Created on 2019/04/01
 */
public abstract class BaseItem implements IGameObject {
	
    
    public MoveComponent moveComponent;
    public List<ConditionTask> conditionTasks = new LinkedList<>();
    public List<ConditionTask> historyConditionTasks = new LinkedList<>();
    
    
	public final String name;
	public double x;
	public double y;

	
	public BaseItem(String name, double x, double y) {
		this.name = name;
		this.x = x;
		this.y = y;
	}
	
	
	
	
	public void move(double speedX, double speedY) {
	    this.x += speedX;
	    this.y += speedY;
	}
	
	public double getY() {
        return this.y;
    }

	public double getX() {
        return this.x;
    }
	

	
	public String getName() {
        return name;
    }
	
	public void removeTaskToHistory(ConditionTask conditionTask) {
        if (conditionTasks.contains(conditionTask)) {
            historyConditionTasks.add(conditionTask);
        }
    }
	
	@Override
	public void postUpdateGame(World world) {
	    conditionTasks.removeAll(historyConditionTasks);
	    historyConditionTasks.clear();
	}
	
}
