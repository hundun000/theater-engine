package theaterengine;

import theaterengine.script.tool.ScalaTool;

/**
 *
 * @author hundun
 * Created on 2019/04/01
 */
public class Screen extends MoveableItem{
	
	public Screen(String name, double x, double y, int width) {
		super(name, x, y);
		this.width = width;
	}
	
	final int height = (int) ScalaTool.meterToPixel(0.2);
	final int width; 
	


}
