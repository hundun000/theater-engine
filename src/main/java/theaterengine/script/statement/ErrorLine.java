package theaterengine.script.statement;

import java.util.List;

import theaterengine.script.StatementType;
import theaterengine.script.Token;

/**
 * @author hundun
 * Created on 2021/04/07
 */
public class ErrorLine extends Statement {

    public String message;
    
    public ErrorLine(List<Token> args) {
        super(args, StatementType.SYNTAX_ERROR);
        
    }

    public ErrorLine(List<Token> tokens, String message) {
        this(tokens);
        this.message = message;
    }

}
