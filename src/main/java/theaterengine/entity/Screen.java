package theaterengine.entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import theaterengine.core.World;
import theaterengine.gui.StagePanel;
import theaterengine.script.tool.ScalaTool;

/**
 *
 * @author hundun
 * Created on 2019/04/01
 */
public class Screen extends BaseItem implements IGameObject {
	
	public Screen(String name, double x, double y, int width) {
		super(name, x, y);
		this.width = width;
	}
	
	public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    private final int height = (int) ScalaTool.meterToPixel(0.2);
	private final int width;
    @Override
    public void updateGame(World world) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void renderGame(Graphics2D g2d, World world) {
        Screen stageScreen = this;
        Font fontItemName = StagePanel.fontItemName;
        
        g2d.setColor(Color.GRAY);
        g2d.drawRect((int)stageScreen.getX() - stageScreen.getWidth()/2, (int)stageScreen.getY(), stageScreen.getWidth(), stageScreen.getHeight());
        
        g2d.setColor(Color.BLACK);
        g2d.fillRect((int)stageScreen.getX() - stageScreen.getWidth()/2, (int)stageScreen.getY(), stageScreen.getWidth(), stageScreen.getHeight());

        g2d.setColor(Color.BLACK);
        g2d.setFont(fontItemName);
        g2d.drawString(stageScreen.getName(), (int)stageScreen.getX() - fontItemName.getSize(), (int)stageScreen.getY() + stageScreen.getHeight() + fontItemName.getSize());
        
    } 
	


}
