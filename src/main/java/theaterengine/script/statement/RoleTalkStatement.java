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
public class RoleTalkStatement extends Statement {
    public static List<List<TokenType>> syntaxs = new ArrayList<>();
    static {
        syntaxs.add(TokenType.asList(TokenType.USE_ROLE_ID, TokenType.SAY, TokenType.LITERAL_VALUE));
    }
    
    public String fullWord;
    public String roleName;
    
	public RoleTalkStatement(List<Token> args) {
        super(args, StatementType.ROLE_TALK);
        this.roleName = args.get(0).text;
        this.fullWord = args.get(2).text;
    }
    

	
	
}
