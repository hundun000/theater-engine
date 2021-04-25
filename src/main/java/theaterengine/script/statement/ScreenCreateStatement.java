package theaterengine.script.statement;

import java.util.ArrayList;
import java.util.List;

import theaterengine.StageApplication;
import theaterengine.core.World;
import theaterengine.entity.Screen;
import theaterengine.gui.MainFrame;
import theaterengine.gui.StagePanel;
import theaterengine.script.StatementType;
import theaterengine.script.Token;
import theaterengine.script.tool.ScalaTool;

/**
 *
 * @author hundun
 * Created on 2019/04/02
 */
public class ScreenCreateStatement extends Statement {

	public static List<List<TokenType>> syntaxs = new ArrayList<>();
	static {
		// 创建幕布 A 宽 1.5 向前 0.8 向左 0.75
		syntaxs.add(TokenType.asList(TokenType.CREATE_SCREEN, TokenType.LITERAL_VALUE, TokenType.WIDTH, TokenType.LITERAL_VALUE, TokenType.FORWARD, TokenType.LITERAL_VALUE, TokenType.LEFT, TokenType.LITERAL_VALUE));
		syntaxs.add(TokenType.asList(TokenType.CREATE_SCREEN, TokenType.LITERAL_VALUE, TokenType.WIDTH, TokenType.LITERAL_VALUE, TokenType.FORWARD, TokenType.LITERAL_VALUE, TokenType.RIGHT, TokenType.LITERAL_VALUE));
		syntaxs.add(TokenType.asList(TokenType.CREATE_SCREEN, TokenType.LITERAL_VALUE, TokenType.WIDTH, TokenType.LITERAL_VALUE, TokenType.BACKWARD, TokenType.LITERAL_VALUE, TokenType.LEFT, TokenType.LITERAL_VALUE));
		syntaxs.add(TokenType.asList(TokenType.CREATE_SCREEN, TokenType.LITERAL_VALUE, TokenType.WIDTH, TokenType.LITERAL_VALUE, TokenType.BACKWARD, TokenType.LITERAL_VALUE, TokenType.RIGHT, TokenType.LITERAL_VALUE));
	}
	
	public String id;
	
	double dy;
	double dx;
	int screenWidth;
	
	public ScreenCreateStatement(List<Token> args2) {
		super(args2, StatementType.SCREEN_CTEATE);
		this.id = tokens.get(1).text;
		
		this.screenWidth = (int) ScalaTool.meterToPixel(Double.parseDouble(tokens.get(3).text));
        this.dy = tokens.get(4).type.getIntValue() * ScalaTool.meterToPixel(Double.parseDouble(tokens.get(5).text));
        this.dx = tokens.get(6).type.getIntValue() * ScalaTool.meterToPixel(Double.parseDouble(tokens.get(7).text));
        
	}
	
	public void work(World world) {
		
		double x = dx + StageApplication.positionZeroX;
		double y = dy + StageApplication.positionZeroY;
		
		
		world.addScreen(new Screen(id, x, y, screenWidth));
	}

}
