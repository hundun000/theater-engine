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
public abstract class Statement {
	
	
	public final StatementType type;
	public int lineNumber;
    public List<String> doneEvents;
    public String condition;
    protected List<Token> tokens;
	
	public Statement(List<Token> tokens, StatementType type) {
	    this.type = type;
		this.tokens = tokens;

	}

	
	public List<Token> getTokens() {
        return tokens;
    }
	
	
	
	
	
	
	
}
