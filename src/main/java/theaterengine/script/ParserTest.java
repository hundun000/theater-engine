package theaterengine.script;

import static org.junit.Assert.*;

import org.junit.Test;

import theaterengine.script.statement.ScreenPairStatement;

/**
 *
 * @author hundun
 * Created on 2019/04/01
 */
public class ParserTest {

	@Test
	public void test() {
		Parser parser = new Parser();
		parser.registerGrammars(ScreenPairStatement.grammars, StatementType.SCREEN_PAIR_MOVE);
		System.out.println(parser.parse("A B 大 开".split(" ")));
	}

}
