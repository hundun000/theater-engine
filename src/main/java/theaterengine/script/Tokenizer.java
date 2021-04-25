package theaterengine.script;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import theaterengine.script.statement.TokenType;


/**
 * @author hundun
 * Created on 2021/04/07
 */
public class Tokenizer {
    
    Map<String, TokenType> keywords = new HashMap<>();
    List<String> roleIds = new ArrayList<>();
    List<String> screenIds = new ArrayList<>();
    
    
    public Tokenizer() {
        init();
    }
    
    /*
     * 
     *  CREATE_ROLE_ID("创建角色", null),
    
    CREATE_SCREEN_ID("创建幕布", null),
    WIDTH("宽", null),
    
    NO_DIRECTION("原地", Integer.valueOf(0)),
    
    FORWARD("向前", Integer.valueOf(-1)),
    BACKWARD("向后", Integer.valueOf(1)),
    
    LEFT("向左", Integer.valueOf(-1)),
    RIGHT("向右", Integer.valueOf(1)),
    
    // m/s
    ROLE_FAST_SPEED("快速", Double.valueOf(4)),
    ROLE_MID_SPEED("中速", Double.valueOf(2)),
    ROLE_SLOW_SPEED("慢速", Double.valueOf(1)),
    

    
    BIG_OPEN("大开", Double.valueOf(-1.5)),
    MID_OPEN("中开", Double.valueOf(-1)),
    SMALL_OPEN("小开", Double.valueOf(-0.5)),
    
    BIG_CLOSE("大关", Double.valueOf(1.5)),
    MID_CLOSE("中关", Double.valueOf(1)),
    SMALL_CLOSE("小关", Double.valueOf(0.5)),
    
    ROLE_DELAY("等待", null),
    DELAY("延时", null),
    
    
    USE_ROLE_ID(null, null),
    USE_SCREEN_ID(null, null),
    TEXT_VAR("", null),
    
    SAY("说", null),
    
    THEN_SET("然后宣布", null),
    WHEN("等到", null),
     * 
     */
    private void init() {
        registerKeyword(TokenType.CREATE_ROLE);
        registerKeyword(TokenType.CREATE_SCREEN);
        registerKeyword(TokenType.CREATE_MACRO);
        registerKeyword(TokenType.WIDTH);
        registerKeyword(TokenType.FORWARD);
        registerKeyword(TokenType.BACKWARD);
        registerKeyword(TokenType.LEFT);
        registerKeyword(TokenType.RIGHT);
        registerKeyword(TokenType.MOVE_WITH_SPEED);
        registerKeyword(TokenType.ROLE_DELAY);
        registerKeyword(TokenType.DELAY);
        registerKeyword(TokenType.SAY);
        registerKeyword(TokenType.THEN_SET);
        registerKeyword(TokenType.WHEN);
    }
    
    public void registerKeyword(TokenType type) {
        keywords.put(type.getWord(), type);
    }
    
    public void registerRoleId(String id) {
        roleIds.add(id);
    }
    
    public void registerScreenId(String id) {
        screenIds.add(id);
    }
    
    
    public List<Token> tokenize(List<String> inputs) {
        List<Token> tokens = new ArrayList<>(inputs.size());
        for (String input : inputs) {
            TokenType type = this.calculateTokenType(input);
            Token token = new Token(input, type);
            tokens.add(token);
        }
        return tokens;
    }

    private TokenType calculateTokenType(String input) {
        if (roleIds.contains(input)) {
            return TokenType.USE_ROLE_ID;
        }
        
        if (screenIds.contains(input)) {
            return TokenType.USE_SCREEN_ID;
        }
        
        if (keywords.containsKey(input)) {
            return keywords.get(input);
        }
        
        return TokenType.LITERAL_VALUE;
    }


    
    
    
    

}
