package theaterengine.entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import theaterengine.StageApplication;
import theaterengine.core.World;
import theaterengine.entity.component.MoveComponent;
import theaterengine.gui.StagePanel;
import theaterengine.script.tool.ScalaTool;
import theaterengine.task.ConditionTask;
import theaterengine.task.DelayTask;

/**
 *
 * @author hundun
 * Created on 2019/04/01
 */
public class Role extends BaseItem implements IGameObject{
    private static final Logger logger = LoggerFactory.getLogger(Role.class);

    private String speaking = "";
    private int currentSpeakEndIndex;
    
    public static final int radius = (int) ScalaTool.meterToPixel(0.3);

    private static BufferedImage talkBubbleImage;

    static {
        try {
            talkBubbleImage = ImageIO.read(new File("./data/images/talk_bubble.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
    }
    
	public Role(String name, double x, double y) {
		super(name, x, y);
		this.moveComponent = new MoveComponent(this);
	}
	
	public void setSpeaking(String speaking) {
	    this.currentSpeakEndIndex = 0;
        this.speaking = speaking;
        logger.debug("{}.speaking set to {}", name, speaking);
    }
	
	public void setCurrentSpeakEndIndex(int currentSpeakEndIndex) {
        this.currentSpeakEndIndex = currentSpeakEndIndex;
    }
	
	public String getCompletedSpeaking() {
        return speaking;
    }
	
	public String getCurrentSpeaking() {
        return speaking.substring(0, Math.min(currentSpeakEndIndex, speaking.length()));
    }

    public void moveToTarget(double additionX, double additionY) {
        // TODO Auto-generated method stub
        super.move(additionX, additionY);
    }

	

    @Override
    public void updateGame(World world) {
        for (ConditionTask conditionTask : this.conditionTasks) {
            conditionTask.clockArrive();
        }
    }

    @Override
    public void renderGame(Graphics2D g2d, World world) {
        Role role = this;
        Font fontItemName = StagePanel.fontItemName;
        Font speakFont = StagePanel.speakFont;
        
        g2d.setColor(Color.BLACK);
        g2d.drawOval((int)role.getX() - Role.radius, (int)role.getY() - Role.radius, 2 * Role.radius, 2 * Role.radius);

        g2d.setColor(Color.GRAY);
        g2d.fillOval((int)role.getX() - Role.radius, (int)role.getY() - Role.radius, 2 * Role.radius, 2 * Role.radius);

        
        int roleX = (int) (role.getX() - Role.radius + 0.2 * fontItemName.getSize());
        int roleY = (int) (role.getY() + 0.5 * fontItemName.getSize());
        
        
        
        
        if (role.getCompletedSpeaking().length() != 0) {
            String text = role.getName() + " : " + role.getCurrentSpeaking();
            g2d.setColor(Color.BLACK);
            g2d.setFont(speakFont);
            int speakX = StageApplication.stagePanelWidth / 2 - text.length() * speakFont.getSize() / 2;
            int speakY = StageApplication.stagePanelHeight - speakFont.getSize() + 20;
            g2d.drawString(text, speakX, speakY);
            g2d.drawImage(talkBubbleImage, roleX + Role.radius, roleY - Role.radius - talkBubbleImage.getHeight(), null);
        }
        
        g2d.setColor(Color.BLACK);
        g2d.setFont(fontItemName);
        g2d.drawString(role.getName(), roleX, roleY);
    }

    public void clearSpeaking() {
        this.speaking = "";
        logger.debug("{}.speaking cleared", name);
    }

    
	
	

	
}
