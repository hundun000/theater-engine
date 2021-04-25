package theaterengine.script;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import theaterengine.script.statement.RoleCreateStatement;
import theaterengine.script.statement.Statement;

/**
 * @author hundun
 * Created on 2021/04/07
 */
public class ParserTest {

    @Test
    public void test() {
        Parser parser = new Parser();
        Statement result = parser.parse(Arrays.asList("创建角色 哈姆雷特 向前 0 向左 1".split(" ")));
        assertEquals(StatementType.ROLE_CTEATE, result.type);
    }

}
