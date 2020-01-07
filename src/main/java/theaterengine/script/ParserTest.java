package theaterengine.script;

import static org.junit.Assert.*;

import org.junit.Test;

import theaterengine.script.statement.RoleCreateStatement;

/**
 *
 * @author hundun
 * Created on 2019/04/01
 */
public class ParserTest {

	@Test
	public void test() {
		Parser parser = new Parser();
		parser.registerGrammars(RoleCreateStatement.grammars, StatementType.ROLE_CTEATE);
		System.out.println(parser.parse("A B 大 开".split(" ")));
	}

}
