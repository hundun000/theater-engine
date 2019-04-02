package theaterengine.script.statement;

import java.util.ArrayList;
import java.util.List;

import theaterengine.Screen;
import theaterengine.ScreenFactory;
import theaterengine.Stage;
import theaterengine.script.tool.ScalaTool;

/**
 *
 * @author hundun
 * Created on 2019/04/02
 */
public class ScreenCreateStatement extends Statement {

	public static List<List<String>> grammars = new ArrayList<>();
	static {
		// 创建幕布 A 宽 1.5 前 0.8 左 0.75
		grammars.add(Keyword.getWords(Keyword.CREATE_SCREEN, Keyword.VAR, Keyword.WIDTH, Keyword.VAR, Keyword.FORWARD, Keyword.VAR, Keyword.LEFT, Keyword.VAR));
		grammars.add(Keyword.getWords(Keyword.CREATE_SCREEN, Keyword.VAR, Keyword.WIDTH, Keyword.VAR, Keyword.FORWARD, Keyword.VAR, Keyword.RIGHT, Keyword.VAR));
		grammars.add(Keyword.getWords(Keyword.CREATE_SCREEN, Keyword.VAR, Keyword.WIDTH, Keyword.VAR, Keyword.BACKWARD, Keyword.VAR, Keyword.LEFT, Keyword.VAR));
		grammars.add(Keyword.getWords(Keyword.CREATE_SCREEN, Keyword.VAR, Keyword.WIDTH, Keyword.VAR, Keyword.BACKWARD, Keyword.VAR, Keyword.RIGHT, Keyword.VAR));
	}
	
	@Override
	List<List<String>> getGrammers() {
		return grammars;
	}
	
	String[] args;
	public ScreenCreateStatement(String[] args) {
		this.args = args;
	}
	
	public void work() {
		String name = args[1];
		int screenWidth = ScalaTool.meterToPixel(Double.parseDouble(args[3]));
		int dy = Keyword.get(args[4]).getIntValue() * ScalaTool.meterToPixel(Double.parseDouble(args[5]));
		int dx = Keyword.get(args[6]).getIntValue() * ScalaTool.meterToPixel(Double.parseDouble(args[7]));
		
		int x = dx + Stage.positionZeroX;
		int y = dy + Stage.positionZeroY;
		
		
		ScreenFactory.registerAndAddToStage(new Screen(name, x, y, screenWidth));
	}

}
