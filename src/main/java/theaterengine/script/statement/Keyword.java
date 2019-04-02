package theaterengine.script.statement;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hundun
 * Created on 2019/04/01
 */
public enum Keyword {
	
	CREATE_SCREEN("创建幕布", null),
	WIDTH("宽", null),
	
	FORWARD("向前", new Integer(-1)),
	BACKWARD("向后", new Integer(1)),
	
	LEFT("向左", new Integer(-1)),
	RIGHT("向右", new Integer(1)),
	
	BIG("大", new Integer(30)),
	MID("中", new Integer(20)),
	SMALL("小", new Integer(10)),
	
	OPEN("开", new Integer(-1)),
	CLOSE("关", new Integer(1)),
	
	DELAY("延时", null),
	VAR("var", null);
	
	private String word;
	private Object value;
	
	private Keyword(String word, Object value) {
		this.word = word;
		this.value = value;
	}
	
	public String getWord() {
		return word;
	}
	
	public int getIntValue() {
		return (Integer) value;
	}
	
	public static Keyword get(String word) {
		for (Keyword keyword : values()){
			if (keyword.getWord().equals(word)) {
				return keyword;
			}
		}
		return null;
	}
	
	public static List<String> getWords(Keyword... keywords) {
		List<String> words = new ArrayList<>();
		for (Keyword keyword : keywords) {
			words.add(keyword.getWord());
		}
		return words;
	}

}
