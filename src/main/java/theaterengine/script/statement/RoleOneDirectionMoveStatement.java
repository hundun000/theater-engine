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
				// （等待 开幕） 哈姆雷特 中速 向前 2
			    grammars.add(Keyword.getWords(Keyword.VAR, speed, direction, Keyword.VAR));
			}
		}
	}
	

	
	
	public String argName;
	public String argSpeed;
	public String argDirection1;
	public String argDistance1;
	
	public RoleOneDirectionMoveStatement(List<String> args) {
		super(args);
		
		argName = args.get(0);
		argSpeed = args.get(1);
		argDirection1 = args.get(2);
		argDistance1 = args.get(3);
	}
	
	
	

}
