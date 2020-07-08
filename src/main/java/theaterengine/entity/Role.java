package theaterengine.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import theaterengine.script.task.DelayTask;
import theaterengine.script.tool.ScalaTool;

/**
 *
 * @author hundun
 * Created on 2019/04/01
 */
public class Role extends MoveableItem{
    private static final Logger logger = LoggerFactory.getLogger(Role.class);

    private String speaking = "";
    
    
	public Role(String name, double x, double y) {
		super(name, x, y);
	}
	
	public void setSpeaking(String speaking) {
        this.speaking = speaking;
        logger.debug(name + ".speaking set to " + speaking);
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
