package theaterengine.script.statement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author hundun
 * Created on 2019/04/01
 */
public class DelayStatement extends Statement{
	
	public static List<List<String>> grammars = new ArrayList<>();
	static {
		grammars.add(Arrays.asList(Keyword.DELAY.getWord(), Keyword.VAR.getWord()));
	}
	
	@Override
	List<List<String>> getGrammers() {
		return grammars;
	}
	
	String[] args;
	public DelayStatement(String[] args) {
		super(args);
		this.args = args;
	}
	
	public int getDelay() {
		return (int) (Double.parseDouble(args[1]) * 1000);
	}
}
