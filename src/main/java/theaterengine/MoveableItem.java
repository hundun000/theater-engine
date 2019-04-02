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
	
	public MoveableItem(String name, double x, double y) {
		this.name = name;
		this.x = x;
		this.y = y;
	}
	
	void move(double speedX, double speedY) {
		x += speedX;
		y += speedY;
	}
	
}
