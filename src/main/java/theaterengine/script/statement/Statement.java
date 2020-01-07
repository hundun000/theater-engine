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
	
	protected List<String> args;
	private String string;
	public Statement(List<String> args) {
	    this.string = args.toString();
		this.args = args;
	}
	
	public Statement(String[] args2) {
        // TODO Auto-generated constructor stub
    }

    @Override
	public String toString() {
		return string;
	}
	
	public List<String> getArgs() {
        return args;
    }
}
