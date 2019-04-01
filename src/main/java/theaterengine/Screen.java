package theaterengine;
/**
 *
 * @author hundun
 * Created on 2019/04/01
 */
public class Screen extends MoveableItem{
	
	public Screen(String name, int x, int y, int width) {
		super(name, x, y);
		this.width = width;
	}
	
	final int height = 5;
	final int width; 
	


}
