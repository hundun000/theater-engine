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

public class RoleOneDirectionMoveStatement extends Statement{
	private static final Logger logger = LoggerFactory.getLogger(RoleOneDirectionMoveStatement.class);
	
	
	public static List<List<String>> grammars = new ArrayList<>();
	static {
		Keyword[] speeds = new Keyword[] {Keyword.ROLE_FAST_SPEED, Keyword.ROLE_MID_SPEED, Keyword.ROLE_SLOW_SPEED};
		Keyword[] directions = new Keyword[] {Keyword.FORWARD, Keyword.BACKWARD, Keyword.LEFT, Keyword.RIGHT};
		for (Keyword speed : speeds) {
			for (Keyword direction : directions) {
				// 等待 开幕 哈姆雷特 中速 向前 2
				grammars.add(Keyword.getWords(Keyword.WHEN, Keyword.VAR, Keyword.VAR, speed, direction, Keyword.VAR));
				//grammars.add(Keyword.getWords(Keyword.ROLE_DELAY, Keyword.VAR, Keyword.VAR, speed, direction, Keyword.VAR));
			}
		}
	}
	

	
	
	String argName;
	String argSpeed;
	String argDirection1;
	String argDistance1;
	String argDelay;
	
	public RoleOneDirectionMoveStatement(String[] args) {
		super(args);
		
		if (args.length == 6) {
			argName = args[2];
			argSpeed = args[3];
			argDirection1 = args[4];
			argDistance1 = args[5];
			argDelay = args[1];
		} else {
			argName = args[0];
			argSpeed = args[1];
			argDirection1 = args[2];
			argDistance1 = args[3];
			argDelay = "0";
		}
	}
	
	public List<MovementAction> toMoveActions(int delay, StagePanel stagePanel) {
		List<MovementAction> moveActions = new ArrayList<>();
				
		Role role = RoleFactory.get(argName);
		int times;
		double speedX;
		double speedY;
		double speedResultant = ScalaTool.meterToPixel(Keyword.get(argSpeed).getDoubleValue()) / MovementAction.frequency;
		
		
		double distance = ScalaTool.getSignedDistanceInPixel(argDirection1, argDistance1);
		times = (int) Math.abs((distance / speedResultant));
		switch (Keyword.get(argDirection1)) {
		case FORWARD:
		case BACKWARD:
			speedX = 0;
			speedY = (distance > 0 ? 1 : -1) * speedResultant;
			break;
		case LEFT:
		case RIGHT:
			speedX = (distance > 0 ? 1 : -1) * speedResultant;
			speedY = 0;
			break;	
		default:
			speedX = 0;
			speedY = 0;
			logger.error("Keyword.get({})未定义行为", argDirection1);
		}

		
		moveActions.add(new MovementAction(stagePanel, role, speedX, speedY, times, delay));
		return moveActions;
	}
	
	public int getDelay() {
		return (int) (Double.parseDouble(argDelay) * 1000);
	}
	

}
