package theaterengine.script;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import theaterengine.script.statement.Keyword;
import theaterengine.script.statement.Statement;




/**
 *
 * @author hundun
 * Created on 2019/04/01
 */
public class Parser {
	
	private static final Logger logger = LoggerFactory.getLogger(Parser.class);
    
	DFANode root = new DFANode();
	
	
	public StatementType parse(String[] inputs) {
		return root.parse(inputs);
	}
	
	public void registerGrammars(List<List<String>> grammars, StatementType type) {
		for (List<String> grammar : grammars) {
			registerGrammar(grammar, type);
		}
	}
	
	public void registerGrammar(List<String> grammar, StatementType type) {
        DFANode nowNode = root;

    	for(int i = 0; i < grammar.size(); i++) {
    		String word = grammar.get(i);
    		//库中获取关键字
        	DFANode nextNode = nowNode.get(word);
            //如果存在该key，直接赋值，用于下一个循环获取
            if (nextNode == null) {
            	nextNode = new DFANode();
                //不是最后一个
                nowNode.put(word, nextNode);
            }
            nowNode = nextNode;
            
            if (i == grammar.size() - 1) {
                //最后一个
                nowNode.endType = type;
            }
    	}

        
    }
	
	class DFANode {
		
		
		private HashMap<String, DFANode> data;
		StatementType endType;
		
		public DFANode() {
			this.data = new HashMap<>();
			this.endType = null;
		}

		public DFANode get(String input) {
			return data.get(input);
		}

		public void put(String input, DFANode node) {
			if (data.containsKey(input)) {
				logger.error("DFA node {} 已存在");
			}
			data.put(input, node);
		}
		
		public StatementType parse(String[] inputs) {
			if (inputs.length > 0) {
				String first = inputs[0];
				String[] remain = Arrays.copyOfRange(inputs, 1, inputs.length);
				DFANode nextNode = data.getOrDefault(first, data.get(Keyword.VAR.getWord()));
				if (nextNode == null) {
					return null;
				}
				return nextNode.parse(remain);
			} else {
				return endType;
			}
		}

		public int size() {
			return data.size();
		}
		
		public  Set<String> getKeySet(){ 
			Set<String> set;
			set = data.keySet();
			return set;
		}
		
		@Override
		public String toString() {
			return data.toString();
		}
	}

}
