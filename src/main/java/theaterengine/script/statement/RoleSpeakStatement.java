package theaterengine.script.statement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author hundun
 * Created on 2019/04/01
 */
public class RoleSpeakStatement extends Statement {
    public static List<List<String>> grammars = new ArrayList<>();
    static {
        grammars.add(Keyword.getWords(Keyword.VAR, Keyword.SPEAK, Keyword.VAR));
    }
    
    public String fullWord;
    public String roleName;
    
	public RoleSpeakStatement(List<String> args) {
        super(args);
        this.roleName = args.get(0);
        this.fullWord = args.get(2);
    }
    

	
	
}
