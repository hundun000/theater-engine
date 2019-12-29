package theaterengine;

import java.net.Inet4Address;

import javax.sound.midi.Instrument;

/**
 *
 * @author hundun
 * Created on 2019/04/01
 */
public class MoveableItem {
	
	final String name;
	double x;
	double y;
	Double targetX;
	Double targetY;
    double speedPerClock;
	
	public MoveableItem(String name, double x, double y) {
		this.name = name;
		this.x = x;
		this.y = y;
	}
	
	public void setTarget(Double additionX, Double additionY, double speed) {
        this.targetX = additionX != null ? (x + additionX) : null;
        this.targetY = additionY != null ? (y + additionY) : null;
        this.speedPerClock = speed;
    }
	
	public boolean isMoving() {
	    return targetX != null || targetY != null;
	}
	
	
	public void moveClockArrive() {
	    boolean isXArrival;
	    boolean isYArrival;
	    
	    if (targetX != null) {
	        boolean isXIncreasing = x < targetX;
	        if (isXIncreasing) {
	            x += speedPerClock;
	            isXArrival = x >= targetX;
	        } else {
	            x -= speedPerClock;
	            isXArrival = x <= targetX;
	        }
	    } else {
	        isXArrival = true;
	    }
	    
	    if (targetY != null) {
	        boolean isYIncreasing = y < targetY;
            if (isYIncreasing) {
                y += speedPerClock;
                isYArrival = y >= targetY;
            } else {
                y -= speedPerClock;
                isYArrival = y <= targetY;
            }
        } else {
            isYArrival = true;
        }
	    
	    if (isXArrival && isYArrival) {
	        x = targetX != null ? targetX : x;
	        y = targetY != null ? targetY : y;
	        targetX = null;
	        targetY = null;
	    }
	}
	
	
	
	protected void move(double speedX, double speedY) {
		x += speedX;
		y += speedY;
	}
	
}
