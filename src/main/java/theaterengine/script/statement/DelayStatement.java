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
    public int delay;
    
	public DelayStatement(List<String> args) {
        super(args);
        this.delay = (int) (Integer.parseInt(args.get(1)));
    }
    public static List<List<String>> grammars = new ArrayList<>();
	static {
		grammars.add(Keyword.getWords(Keyword.DELAY, Keyword.VAR));
	}

	
	
}
