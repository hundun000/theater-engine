package theaterengine.script.statement;

import java.util.ArrayList;
import java.util.List;

import theaterengine.StageApplication;
import theaterengine.core.World;
import theaterengine.entity.Role;
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
public class RoleCreateStatement extends Statement {

	public static List<List<TokenType>> syntaxs = new ArrayList<>();
	static {
		TokenType[] directionXs = new TokenType[] {TokenType.LEFT, TokenType.RIGHT};
		TokenType[] directionYs = new TokenType[] {TokenType.FORWARD, TokenType.BACKWARD};
		for (TokenType directionX : directionXs) {
			for (TokenType directionY : directionYs) {
				// 创建角色 哈姆雷特 向前 0 向左 1
				syntaxs.add(TokenType.asList(TokenType.CREATE_ROLE, TokenType.LITERAL_VALUE, directionY, TokenType.LITERAL_VALUE, directionX, TokenType.LITERAL_VALUE));
			}
		}
	}
	
	
	
	
	
	
	public String argId;
	int argDirectionY;
	double argDistanceY;
	int argDirectionX;
	double argDistanceX;
	
	public RoleCreateStatement(List<Token> args) {
		super(args, StatementType.ROLE_CTEATE);
		argId = args.get(1).text;
		argDirectionY = args.get(2).type.getIntValue();
		argDistanceY = Double.parseDouble(args.get(3).text);
		argDirectionX = args.get(4).type.getIntValue();
		argDistanceX = Double.parseDouble(args.get(5).text);
	}
	
	public void work(World world) {
		
		double dy = argDirectionY * ScalaTool.meterToPixel(argDistanceY);
		double dx = argDirectionX * ScalaTool.meterToPixel(argDistanceX);
		
		double x = dx + StageApplication.positionZeroX;
		double y = dy + StageApplication.positionZeroY;
		
		
		world.addRole(new Role(argId, x, y));
	}

}
