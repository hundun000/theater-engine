package theaterengine.script.statement;

import java.util.ArrayList;
import java.util.List;

import theaterengine.Screen;
import theaterengine.ScreenFactory;
import theaterengine.Stage;
import theaterengine.StagePanel;
import theaterengine.script.tool.ScalaTool;

/**
 *
 * @author hundun
 * Created on 2019/04/02
 */
public class ScreenCreateStatement extends Statement {

	public static List<List<String>> grammars = new ArrayList<>();
	static {
		// 创建幕布 A 宽 1.5 向前 0.8 向左 0.75
		grammars.add(Keyword.getWords(Keyword.CREATE_SCREEN, Keyword.VAR, Keyword.WIDTH, Keyword.VAR, Keyword.FORWARD, Keyword.VAR, Keyword.LEFT, Keyword.VAR));
		grammars.add(Keyword.getWords(Keyword.CREATE_SCREEN, Keyword.VAR, Keyword.WIDTH, Keyword.VAR, Keyword.FORWARD, Keyword.VAR, Keyword.RIGHT, Keyword.VAR));
		grammars.add(Keyword.getWords(Keyword.CREATE_SCREEN, Keyword.VAR, Keyword.WIDTH, Keyword.VAR, Keyword.BACKWARD, Keyword.VAR, Keyword.LEFT, Keyword.VAR));
		grammars.add(Keyword.getWords(Keyword.CREATE_SCREEN, Keyword.VAR, Keyword.WIDTH, Keyword.VAR, Keyword.BACKWARD, Keyword.VAR, Keyword.RIGHT, Keyword.VAR));
	}
	

	
	public ScreenCreateStatement(List<String> args2) {
		super(args2);
	}
	
	public void work(StagePanel stagePanel) {
		String name = args.get(1);
		int screenWidth = (int) ScalaTool.meterToPixel(Double.parseDouble(args.get(3)));
		double dy = Keyword.get(args.get(4)).getIntValue() * ScalaTool.meterToPixel(Double.parseDouble(args.get(5)));
		double dx = Keyword.get(args.get(6)).getIntValue() * ScalaTool.meterToPixel(Double.parseDouble(args.get(7)));
		
		double x = dx + Stage.positionZeroX;
		double y = dy + Stage.positionZeroY;
		
		
		ScreenFactory.registerAndAddToStage(new Screen(name, x, y, screenWidth), stagePanel);
		stagePanel.updateUI();
	}

}
