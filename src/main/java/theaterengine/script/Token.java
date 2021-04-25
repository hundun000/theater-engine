package theaterengine.script;
/**
 * @author hundun
 * Created on 2021/04/07
 */

import com.alibaba.fastjson.JSON;

import theaterengine.script.statement.TokenType;

public class Token {
    public String text;
    public TokenType type;
    public Token(String text, TokenType type) {
        super();
        this.text = text;
        this.type = type;
    }
    
    
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
    
}
