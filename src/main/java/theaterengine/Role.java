package theaterengine;

import theaterengine.script.tool.ScalaTool;

/**
 *
 * @author hundun
 * Created on 2019/04/01
 */
public class Role extends MoveableItem{
	
	public Role(String name, int x, int y) {
		super(name, x, y);
	}

	public static final int radius = (int) ScalaTool.meterToPixel(0.3);
	
	

	
}
