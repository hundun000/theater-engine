package theaterengine.script.statement;
/**
 *
 * @author hundun
 * Created on 2019/04/01
 */

import java.awt.RenderingHints.Key;
import java.nio.channels.ScatteringByteChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.checkerframework.checker.units.qual.s;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import theaterengine.MovementAction;
import theaterengine.Role;
import theaterengine.RoleFactory;
import theaterengine.Screen;
import theaterengine.ScreenFactory;
import theaterengine.Stage;
import theaterengine.StagePanel;
import theaterengine.script.tool.ScalaTool;

public class RoleTwoDirectionMoveStatement extends Statement{
	private static final Logger logger = LoggerFactory.getLogger(RoleTwoDirectionMoveStatement.class);
	
	
	public static List<List<String>> grammars = new ArrayList<>();
	static {
		Keyword[] speeds = new Keyword[] {Keyword.ROLE_FAST_SPEED, Keyword.ROLE_MID_SPEED, Keyword.ROLE_SLOW_SPEED};
		Keyword[] directionXs = new Keyword[] {Keyword.LEFT, Keyword.RIGHT};
		Keyword[] directionYs = new Keyword[] {Keyword.FORWARD, Keyword.BACKWARD};
		for (Keyword speed : speeds) {
			for (Keyword directionX : directionXs) {
				for (Keyword directionY : directionYs) {
					// 哈姆雷特 中速 向前 2 向左 2
					grammars.add(Keyword.getWords(Keyword.VAR, speed, directionY, Keyword.VAR, directionX, Keyword.VAR));
					grammars.add(Keyword.getWords(Keyword.ROLE_DELAY, Keyword.VAR, Keyword.VAR, speed, directionY, Keyword.VAR, directionX, Keyword.VAR));
				}
			}
		}
	}
	
	@Override
	List<List<String>> getGrammers() {
		return grammars;
	}
	
	
	String argName;
	String argSpeed;
	String argDirection1;
	String argDistance1;
	String argDirection2;
	String argDistance2;
	String argDelay;
	
	public RoleTwoDirectionMoveStatement(String[] args) {
		super(args);
		
		if (args.length == 8) {
			argName = args[2];
			argSpeed = args[3];
			argDirection1 = args[4];
			argDistance1 = args[5];
			argDirection2 = args[6];
			argDistance2 = args[7];
			argDelay = args[1];
		} else {
			argName = args[0];
			argSpeed = args[1];
			argDirection1 = args[2];
			argDistance1 = args[3];
			argDirection2 = args[4];
			argDistance2 = args[5];
			argDelay = "0";
		}
	}
	
	public List<MovementAction> toMoveActions(int delay, StagePanel stagePanel) {
		List<MovementAction> moveActions = new ArrayList<>();
				
		Role role = RoleFactory.get(argName);
		int times;
		double speedX;
		double speedY;
		double speedResultant = ScalaTool.meterToPixel(Keyword.get(argSpeed).getDoubleValue());
		

		double distanceX;
		double distanceY;
	
		// 分位移
		distanceX = ScalaTool.getSignedDistanceInPixel(argDirection2, argDistance2);
		distanceY = ScalaTool.getSignedDistanceInPixel(argDirection1, argDistance1);
		
		double angle = Math.atan(distanceY / distanceX);
		speedX = Math.cos(angle) * speedResultant / MovementAction.frequency;
		speedY = Math.sin(angle) * speedResultant / MovementAction.frequency;
		if (speedX > 0 ^ distanceX > 0) {
			speedX = -speedX;
			speedY = -speedY;
		}
		
		
		times = (int) Math.abs((distanceX / speedX));
		
		
		
		moveActions.add(new MovementAction(stagePanel, role, speedX, speedY, times, delay));
		return moveActions;
	}
	
	public int getDelay() {
		return (int) (Double.parseDouble(argDelay) * 1000);
	}

}
