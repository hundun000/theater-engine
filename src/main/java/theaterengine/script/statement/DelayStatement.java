package theaterengine.script.statement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import theaterengine.script.StatementType;
import theaterengine.script.Token;

/**
 *
 * @author hundun
 * Created on 2019/04/01
 */
public class DelayStatement extends Statement {
    public int delay;
    
	public DelayStatement(List<Token> args) {
        super(args, StatementType.DELAY);
        this.delay = (int) (Integer.parseInt(args.get(1).text));
    }
    public static List<List<TokenType>> syntaxs = new ArrayList<>();
	static {
		syntaxs.add(TokenType.asList(TokenType.DELAY, TokenType.LITERAL_VALUE));
	}

	
	
}
