package theaterengine.script.statement;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hundun
 * Created on 2019/04/01
 */
public enum TokenType {

	
	CREATE_ROLE("创建角色", null),
	CREATE_SCREEN("创建幕布", null),
	CREATE_MACRO("创建宏", null),
	
	ENTITY_SET_POSITION("设置位置", null),
	WIDTH("宽", null),
	
	
	FORWARD("向前", Integer.valueOf(-1)),
	BACKWARD("向后", Integer.valueOf(1)),
	
	LEFT("向左", Integer.valueOf(-1)),
	RIGHT("向右", Integer.valueOf(1)),
	
	// m/s
	MOVE_WITH_SPEED("移速", null),

	
	
	ROLE_DELAY("等待", null),
	DELAY("延时", null),
	
	
	USE_ROLE_ID(null, null),
	USE_SCREEN_ID(null, null),
	LITERAL_VALUE("", null),
	
	SAY("说", null),
	
	THEN_SET("然后宣布", null),
	WHEN("等到", null),
	;
	
	private String word;
	private Object value;
	
	private TokenType(String word, Object value) {
		this.word = word;
		this.value = value;
	}
	
	public String getWord() {
		return word;
	}
	
	public int getIntValue() {
		return (Integer) value;
	}
	
	public double getDoubleValue() {
		return (double) value;
	}
	
	
	
	
	public static List<TokenType> asList(TokenType... keywords) {
		List<TokenType> words = new ArrayList<>();
		for (TokenType tokenType : keywords) {
			words.add(tokenType);
		}
		return words;
	}

}
