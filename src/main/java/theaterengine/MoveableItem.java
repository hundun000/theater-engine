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
	int x;
	int y;
	
	public MoveableItem(String name, int x, int y) {
		this.name = name;
		this.x = x;
		this.y = y;
	}
	
	void move(int dx, int dy) {
		x += dx;
		y += dy;
	}
	
}
