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


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import theaterengine.entity.Role;
import theaterengine.entity.Screen;
import theaterengine.gui.MainFrame;
import theaterengine.gui.StagePanel;
import theaterengine.script.StatementType;
import theaterengine.script.Token;
import theaterengine.script.tool.ScalaTool;

public class RoleOneDirectionMoveStatement extends Statement {
	private static final Logger logger = LoggerFactory.getLogger(RoleOneDirectionMoveStatement.class);
	
	
	public static List<List<TokenType>> syntaxs = new ArrayList<>();
	static {
		TokenType[] directions = new TokenType[] {TokenType.FORWARD, TokenType.BACKWARD, TokenType.LEFT, TokenType.RIGHT};

			for (TokenType direction : directions) {
				// （等待 开幕） 哈姆雷特 移速 4 向前 2
			    syntaxs.add(TokenType.asList(TokenType.USE_ROLE_ID, TokenType.MOVE_WITH_SPEED, TokenType.LITERAL_VALUE, direction, TokenType.LITERAL_VALUE));
			}

	}
	

	
	
	public String argName;
	public double argSpeed;
	
	public Double targetX;
	public Double targetY;
	
	public RoleOneDirectionMoveStatement(List<Token> args) {
		super(args, StatementType.ROLE_ONE_DIRECTION_MOVE);
		
		argName = args.get(0).text;
		argSpeed = Double.parseDouble(args.get(2).text);
		Token argDirection = args.get(3);
		double argDistance = Double.parseDouble(args.get(4).text);
		
		switch (argDirection.type) {
        case FORWARD:
        case BACKWARD:
            targetX = null;
            targetY = ScalaTool.meterToPixel(argDistance);
            break;
        case LEFT:
        case RIGHT:
            targetX = ScalaTool.meterToPixel(argDistance);
            targetY = null;
            break;  
        default:
            targetX = null;
            targetY = null;
            logger.error("argDirection.type = {} 未定义行为", argDirection);
        }
		
	}
	
	
	

}
