package theaterengine.script.statement;

import java.util.ArrayList;
import java.util.List;

import theaterengine.Role;
import theaterengine.RoleFactory;
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
public class RoleCreateStatement extends Statement {

	public static List<List<String>> grammars = new ArrayList<>();
	static {
		Keyword[] directionXs = new Keyword[] {Keyword.LEFT, Keyword.RIGHT};
		Keyword[] directionYs = new Keyword[] {Keyword.FORWARD, Keyword.BACKWARD};
		for (Keyword directionX : directionXs) {
			for (Keyword directionY : directionYs) {
				// 创建角色 哈姆雷特 向前 0 向左 1
				grammars.add(Keyword.getWords(Keyword.CREATE_ROLE, Keyword.VAR, directionY, Keyword.VAR, directionX, Keyword.VAR));
			}
		}
	}
	
	
	
	
	
	
	String argName;
	String argDirectionY;
	String argDistanceY;
	String argDirectionX;
	String argDistanceX;
	
	public RoleCreateStatement(String[] args) {
		super(args);
		argName = args[1];
		argDirectionY = args[2];
		argDistanceY = args[3];
		argDirectionX = args[4];
		argDistanceX = args[5];
	}
	
	public void work(StagePanel stagePanel) {
		
		double dy = Keyword.get(argDirectionY).getIntValue() * ScalaTool.meterToPixel(Double.parseDouble(argDistanceY));
		double dx = Keyword.get(argDirectionX).getIntValue() * ScalaTool.meterToPixel(Double.parseDouble(argDistanceX));
		
		double x = dx + Stage.positionZeroX;
		double y = dy + Stage.positionZeroY;
		
		
		RoleFactory.registerAndAddToStage(new Role(argName, x, y), stagePanel);
		stagePanel.updateUI();
	}

}
