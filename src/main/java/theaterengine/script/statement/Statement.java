package theaterengine.script.statement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author hundun
 * Created on 2019/04/01
 */
public abstract class Statement {
	abstract List<List<String>> getGrammers();
	
	protected String[] args;
	private String string;
	public Statement(String[] args) {
	    this.string = Arrays.toString(args);
		this.args = args;
	}
	
	@Override
	public String toString() {
		return string;
	}
}
