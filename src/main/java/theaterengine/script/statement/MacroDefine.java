package theaterengine.script.statement;

import java.util.List;

import theaterengine.script.Macro;
import theaterengine.script.StatementType;
import theaterengine.script.Token;

/**
 * @author hundun
 * Created on 2021/04/07
 */
public class MacroDefine extends Statement {
    public Macro macro;
    public MacroDefine(List<Token> args, Macro macro) {
        super(args, StatementType.MACRO_CREATE);
        this.macro = macro;
    }

}
