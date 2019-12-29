package theaterengine;

import theaterengine.script.tool.ScalaTool;

/**
 *
 * @author hundun
 * Created on 2019/04/01
 */
public class Role extends MoveableItem{
	
    private String speaking = "";
    
    
	public Role(String name, double x, double y) {
		super(name, x, y);
	}
	
	public void setSpeaking(String speaking) {
        this.speaking = speaking;
        System.out.println(name + ".speaking set to " + speaking);
    }
	
	public String getSpeaking() {
        return speaking;
    }
	

    public void moveToTarget(double additionX, double additionY) {
        // TODO Auto-generated method stub
        super.move(additionX, additionY);
    }

	public static final int radius = (int) ScalaTool.meterToPixel(0.3);
	
	

	
}
