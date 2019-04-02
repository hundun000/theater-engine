package theaterengine.script.statement;
/**
 *
 * @author hundun
 * Created on 2019/04/01
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import theaterengine.MovementAction;
import theaterengine.Screen;
import theaterengine.ScreenFactory;
import theaterengine.Stage;

public class ScreenPairStatement extends Statement{
	
	
	public static List<List<String>> grammars = new ArrayList<>();
	static {
		// A B 大 开
		grammars.add(Keyword.getWords(Keyword.VAR, Keyword.VAR, Keyword.BIG, Keyword.OPEN));
		grammars.add(Keyword.getWords(Keyword.VAR, Keyword.VAR, Keyword.MID, Keyword.OPEN));
		grammars.add(Keyword.getWords(Keyword.VAR, Keyword.VAR, Keyword.SMALL, Keyword.OPEN));
		grammars.add(Keyword.getWords(Keyword.VAR, Keyword.VAR, Keyword.BIG, Keyword.CLOSE));
		grammars.add(Keyword.getWords(Keyword.VAR, Keyword.VAR, Keyword.MID, Keyword.CLOSE));
		grammars.add(Keyword.getWords(Keyword.VAR, Keyword.VAR, Keyword.SMALL, Keyword.CLOSE));
	}
	
	@Override
	List<List<String>> getGrammers() {
		return grammars;
	}
	
	String[] args;
	public ScreenPairStatement(String[] args) {
		this.args = args;
	}
	
	public List<MovementAction> toMoveActions(int delay) {
		List<MovementAction> moveActions = new ArrayList<>();
		
		Screen screenL = ScreenFactory.get(args[0]);
		Screen screenR = ScreenFactory.get(args[1]);
		
		int degree = Keyword.get(args[2]).getIntValue();
		int leftSign = Keyword.get(args[3]).getIntValue();
		int speedX = 10 * leftSign;
		
		moveActions.add(new MovementAction(Stage.stagePanel, screenL, speedX, 0, degree, delay));
		moveActions.add(new MovementAction(Stage.stagePanel, screenR, -speedX, 0, degree, delay));
		return moveActions;
	}

}
