package theaterengine.script.statement;
/**
 *
 * @author hundun
 * Created on 2019/04/01
 */

import java.awt.RenderingHints.Key;
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

public class RoleMoveStatement extends Statement{
	private static final Logger logger = LoggerFactory.getLogger(RoleMoveStatement.class);
	
	
	public static List<List<String>> grammars = new ArrayList<>();
	static {
		Keyword[] speeds = new Keyword[] {Keyword.ROLE_FAST_SPEED, Keyword.ROLE_MID_SPEED, Keyword.ROLE_SLOW_SPEED};
		Keyword[] directions = new Keyword[] {Keyword.FORWARD, Keyword.BACKWARD, Keyword.LEFT, Keyword.RIGHT};
		for (Keyword speed : speeds) {
			for (Keyword direction : directions) {
				// 哈姆雷特 中速 向前 2
				grammars.add(Keyword.getWords(Keyword.VAR, speed, direction, Keyword.VAR));
			}
		}
	}
	
	@Override
	List<List<String>> getGrammers() {
		return grammars;
	}
	
	
	String argName;
	String argSpeed;
	String argDirection;
	String argDistance;
	
	public RoleMoveStatement(String[] args) {
		argName = args[0];
		argSpeed = args[1];
		argDirection = args[2];
		argDistance = args[3];
	}
	
	public List<MovementAction> toMoveActions(int delay, StagePanel stagePanel) {
		List<MovementAction> moveActions = new ArrayList<>();
				
		Role role = RoleFactory.get(argName);
		
		double speedX;
		double speedY;
		int times;
		
		double displacement = Keyword.get(argDirection).getIntValue() * ScalaTool.meterToPixel(Double.parseDouble(argDistance));
		switch (Keyword.get(argDirection)) {
		case FORWARD:
		case BACKWARD:
			speedX = 0;
			speedY = Keyword.get(argDirection).getIntValue() * ScalaTool.meterToPixel(Keyword.get(argSpeed).getDoubleValue()) / MovementAction.frequency;
			times = (int) (displacement / speedY);
			break;
		case LEFT:
		case RIGHT:
			speedY = 0;
			speedX = Keyword.get(argDirection).getIntValue() * ScalaTool.meterToPixel(Keyword.get(argSpeed).getDoubleValue()) / MovementAction.frequency;
			times = (int) (displacement / speedX);
			break;	
		default:
			logger.error("Keyword.get({})未定义行为", argDirection);
			return null;
		}
		
		
		moveActions.add(new MovementAction(stagePanel, role, speedX, speedY, times, delay));
		return moveActions;
	}

}
