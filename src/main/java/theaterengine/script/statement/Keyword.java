package theaterengine.script.statement;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hundun
 * Created on 2019/04/01
 */
public enum Keyword {
	COMMENT("//", null),
	
	CREATE_SCREEN("创建幕布", null),
	WIDTH("宽", null),
	
	FORWARD("向前", new Integer(-1)),
	BACKWARD("向后", new Integer(1)),
	
	LEFT("向左", new Integer(-1)),
	RIGHT("向右", new Integer(1)),
	
	// m/s
	ROLE_FAST_SPEED("快速", new Double(2)),
	ROLE_MID_SPEED("中速", new Double(1)),
	ROLE_SLOW_SPEED("慢速", new Double(0.5)),
	
	// m/s
	FAST_SPEED("快速", new Integer(4)),
	MID_SPEED("中速", new Integer(2)),
	SLOW_SPEED("慢速", new Integer(1)),
	
	BIG_OPEN("大开", new Integer(-300)),
	MID_OPEN("中开", new Integer(-200)),
	SMALL_OPEN("小开", new Integer(-100)),
	
	BIG_CLOSE("大关", new Integer(300)),
	MID_CLOSE("中关", new Integer(200)),
	SMALL_CLOSE("小关", new Integer(100)),
	
	DELAY("延时", null),
	VAR("", null);
	
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
	
	public double getDoubleValue() {
		return (double) value;
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
