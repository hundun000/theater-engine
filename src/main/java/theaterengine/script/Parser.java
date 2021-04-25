package theaterengine.script;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import theaterengine.script.statement.TokenType;
import theaterengine.task.DelayTask;
import theaterengine.task.RoleOneDirectionMoveTask;
import theaterengine.task.RoleSpeakTask;
import theaterengine.entity.Role;
import theaterengine.script.statement.DelayStatement;
import theaterengine.script.statement.ErrorLine;
import theaterengine.script.statement.MacroDefine;
import theaterengine.script.statement.RoleCreateStatement;
import theaterengine.script.statement.RoleOneDirectionMoveStatement;
import theaterengine.script.statement.RoleTalkStatement;
import theaterengine.script.statement.ScreenCreateStatement;
import theaterengine.script.statement.Statement;




/**
 *
 * @author hundun
 * Created on 2019/04/01
 */
public class Parser {
	
	private static final Logger logger = LoggerFactory.getLogger(Parser.class);
    
	DFANode root = new DFANode();
	Map<String, Macro> macros = new HashMap<>();
    
	Tokenizer tokenizer = new Tokenizer();
	int autoFlagIndex = 0;
	
	public Parser() {
	    init();
    }
	
	
	
	private String removeFlag(List<Token> args) {
        int size = args.size();
        if (size > 2 && args.get(size - 2).type == TokenType.THEN_SET && args.get(size - 1).type == TokenType.LITERAL_VALUE) {
            args.remove(size - 2);
            size--;
            return args.remove(size - 1).text;
        } else {
            return null;
        }
    }
    
    private String removeCondition(List<Token> args) {
        if (args.size() > 2 && args.get(0).type == TokenType.WHEN && args.get(1).type == TokenType.LITERAL_VALUE) {
            args.remove(0);
            return args.remove(0).text;
        } else {
            return null;
        }
    }
	
	public Statement parse(List<String> inputs) {
	    List<Token> tokens = tokenizer.tokenize(inputs);
	    
	    Macro macro = checkMacroDefine(tokens);
	    if (macro != null) {
	        return new MacroDefine(tokens, macro);
	    }
	    
	    try {
            expandMacro(tokens);
        } catch (Exception e) {
            return new ErrorLine(tokens, e.getMessage());
        }
	    
	    String condition = removeCondition(tokens); 
        if (condition == null) {
            condition = "Auto" + autoFlagIndex;
        }
        
        List<String> doneFlags = new LinkedList<>();
        String userDoneFlag = removeFlag(tokens);
        if (userDoneFlag != null) {
            doneFlags.add(userDoneFlag);
        }
        autoFlagIndex ++;
        String autoDoneFlag = "Auto" + autoFlagIndex;
        doneFlags.add(autoDoneFlag);
	    
	    
	    List<Token> comsumingtTokens = new ArrayList<>(tokens);
	    StatementType type = root.accept(comsumingtTokens);

	    
	    Statement statement = null;
	    
        switch (type) {
        case ROLE_ONE_DIRECTION_MOVE:
        {
            RoleOneDirectionMoveStatement roleMoveStatement = new RoleOneDirectionMoveStatement(tokens);
            statement = roleMoveStatement;
            break;
        }
        case ROLE_TWO_DIRECTION_MOVE:
            
            break;
        case SCREEN_PAIR_MOVE:
            
            break;
        case DELAY:
            DelayStatement delayStatement = new DelayStatement(tokens);
            statement = delayStatement;
            break;
        case ROLE_TALK:
        {
            RoleTalkStatement roleTalkStatement = new RoleTalkStatement(tokens);
            statement = roleTalkStatement;
            break;
        }  
        case SCREEN_CTEATE:
            ScreenCreateStatement screenCreateStatement = new ScreenCreateStatement(tokens);
            statement = screenCreateStatement;
            tokenizer.registerScreenId(screenCreateStatement.id);
            break;
        case ROLE_CTEATE:
            RoleCreateStatement roleCreateStatement = new RoleCreateStatement(tokens);
            statement = roleCreateStatement;
            tokenizer.registerRoleId(roleCreateStatement.argId);
            break;

        case SYNTAX_ERROR:
            ErrorLine errorLine = new ErrorLine(tokens);
            statement = errorLine;
            break;
        default:
            
            break;
        }
        
        statement.doneEvents = doneFlags;
        statement.condition = condition;
        
	    return statement;
	}
	
	private void expandMacro(List<Token> tokens) throws Exception {
        boolean done = false;
        int maxExpandTime = 0;
        while (!done) {
            for (int i = 0; i < tokens.size(); i++) {
                if (maxExpandTime > 1000) {
                    throw new Exception("macro expand too many times");
                }
                Token token = tokens.get(i);
                Macro macro = macros.get(token.text);
                if (macro != null) {
                    tokens.remove(i);
                    tokens.addAll(i, macro.values);
                    logger.info("macro {} expands to {} at index = {}", macro.id, macro.values, i);
                    maxExpandTime++;
                    done = false;
                    break;
                }
            }
            done = true;
        }
    }



    private Macro checkMacroDefine(List<Token> tokens) {
	    if (tokens.size() > 2 && tokens.get(0).type == TokenType.CREATE_MACRO && tokens.get(1).type == TokenType.LITERAL_VALUE) {
            String macroId = tokens.get(1).text;
            List<Token> tokenValues = tokens.subList(2, tokens.size());
            Macro macro = new Macro();
            macro.id = macroId;
            macro.values = tokenValues;
            this.registerMacro(macro);
            return macro;
	    }
	    return null;
    }


    public void registerMacro(Macro macro) {
        this.macros.put(macro.id, macro);
    }
    
    private void init() {

        this.registerSyntaxs(DelayStatement.syntaxs, StatementType.DELAY);
        this.registerSyntaxs(ScreenCreateStatement.syntaxs, StatementType.SCREEN_CTEATE);
        this.registerSyntaxs(RoleCreateStatement.syntaxs, StatementType.ROLE_CTEATE);
        this.registerSyntaxs(RoleOneDirectionMoveStatement.syntaxs, StatementType.ROLE_ONE_DIRECTION_MOVE);
        this.registerSyntaxs(RoleTalkStatement.syntaxs, StatementType.ROLE_TALK);
    }



    private void registerSyntaxs(List<List<TokenType>> grammars, StatementType type) {
		for (List<TokenType> grammar : grammars) {
			registerSyntax(grammar, type);
		}
	}
	
    private void registerSyntax(List<TokenType> grammar, StatementType type) {
        DFANode nowNode = root;

    	for(int i = 0; i < grammar.size(); i++) {
    		TokenType word = grammar.get(i);
    		
        	DFANode nextNode = nowNode.getChildNode(word);
            
            if (nextNode == null) {
            	nextNode = new DFANode();
                nowNode.put(word, nextNode);
            }
            nowNode = nextNode;
            
            if (i == grammar.size() - 1) {
                nowNode.endType = type;
            }
    	}

        
    }
	
	class DFANode {
		
		
		private HashMap<TokenType, DFANode> children;
		StatementType endType;
		
		public DFANode() {
			this.children = new HashMap<>();
			this.endType = null;
		}

		public DFANode getChildNode(TokenType input) {
			return children.get(input);
		}

		public void put(TokenType input, DFANode node) {
			if (children.containsKey(input)) {
				logger.error("DFA node {} 已存在");
			}
			children.put(input, node);
		}
		
		public StatementType accept(List<Token> tokens) {
			if (tokens == null) {
				return null;
			}
			if (tokens.size() > 0) {
			    Token top = tokens.remove(0);
				DFANode nextNode = getChildNode(top.type);
				if (nextNode == null) {
					return StatementType.SYNTAX_ERROR;
				}
				return nextNode.accept(tokens);
			} else {
				return endType;
			}
		}

		public int size() {
			return children.size();
		}
		
		public  Set<TokenType> getKeySet(){ 
			Set<TokenType> set;
			set = children.keySet();
			return set;
		}
		
		@Override
		public String toString() {
			return children.toString();
		}
	}

}
