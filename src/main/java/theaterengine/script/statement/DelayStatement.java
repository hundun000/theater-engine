package theaterengine.script.statement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author hundun
 * Created on 2019/04/01
 */
public class DelayStatement extends Statement {
	
	public DelayStatement(String[] args) {
        super(args);
    }
    public static List<List<String>> grammars = new ArrayList<>();
	static {
		grammars.add(Keyword.getWords(Keyword.WHEN, Keyword.VAR, Keyword.DELAY, Keyword.VAR));
		grammars.add(Keyword.getWords(Keyword.WHEN, Keyword.VAR, Keyword.DELAY, Keyword.VAR, Keyword.THEN_SET, Keyword.VAR));
	}

	
	
}
