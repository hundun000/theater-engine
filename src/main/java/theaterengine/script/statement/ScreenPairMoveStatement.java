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

import theaterengine.MovementAction;
import theaterengine.Screen;
import theaterengine.ScreenFactory;
import theaterengine.Stage;
import theaterengine.StagePanel;
import theaterengine.script.tool.ScalaTool;

public class ScreenPairMoveStatement extends Statement{
	
	
	public static List<List<String>> grammars = new ArrayList<>();
	static {
		Keyword[] speeds = new Keyword[] {Keyword.FAST_SPEED, Keyword.MID_SPEED, Keyword.SLOW_SPEED};
		Keyword[] operations = new Keyword[] {Keyword.BIG_OPEN, Keyword.MID_OPEN, Keyword.SMALL_OPEN, Keyword.BIG_CLOSE, Keyword.MID_CLOSE, Keyword.SMALL_CLOSE};
		for (Keyword speed : speeds) {
			for (Keyword operation : operations) {
				// A B 中速 大开
				grammars.add(Keyword.getWords(Keyword.VAR, Keyword.VAR, speed, operation));
			}
		}
	}
	
	@Override
	List<List<String>> getGrammers() {
		return grammars;
	}
	
	String[] args;
	public ScreenPairMoveStatement(String[] args) {
		super(args);
		this.args = args;
	}
	
	public List<MovementAction> toMoveActions(int delay, StagePanel stagePanel) {
		List<MovementAction> moveActions = new ArrayList<>();
		
		Screen screenL = ScreenFactory.get(args[0]);
		Screen screenR = ScreenFactory.get(args[1]);
		
		double speedX;
		double leftDx;
		leftDx = ScalaTool.meterToPixel(Keyword.get(args[3]).getDoubleValue());
		speedX = (leftDx > 0 ? 1 : -1) * ScalaTool.meterToPixel(Keyword.get(args[2]).getDoubleValue()) / MovementAction.frequency;
		
		
		int times = (int) (leftDx / speedX);
		
		moveActions.add(new MovementAction(stagePanel, screenL, speedX, 0, times, delay));
		moveActions.add(new MovementAction(stagePanel, screenR, -speedX, 0, times, delay));
		return moveActions;
	}

}
