package theaterengine.core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import theaterengine.entity.Role;
import theaterengine.script.Parser;
import theaterengine.script.StatementType;
import theaterengine.script.statement.DelayStatement;
import theaterengine.script.statement.TokenType;
import theaterengine.script.statement.RoleCreateStatement;
import theaterengine.script.statement.RoleOneDirectionMoveStatement;
import theaterengine.script.statement.RoleTalkStatement;
import theaterengine.script.statement.ScreenCreateStatement;
import theaterengine.script.statement.Statement;
import theaterengine.task.DelayTask;
import theaterengine.task.RoleOneDirectionMoveTask;
import theaterengine.task.RoleSpeakTask;

/**
 * @author hundun
 * Created on 2021/04/06
 */
public class WorldLoader {
    private static final Logger logger = LoggerFactory.getLogger(WorldLoader.class);
    
    
    
    
    public void reloadScriptIntoEngine(World world, List<String> docs, Parser parser) {
        List<Statement> statements = new ArrayList<>();
        
        for (int lineNumber = 0; lineNumber < docs.size(); lineNumber++) {
            String line = docs.get(lineNumber);
            List<String> args = getArgsItemsByScript(line);
            if (args == null || args.size() == 0) {
                continue;
            }
            
            
            
            logger.info("line {}: remain={}", lineNumber, args);
            
            
            Statement statement = parser.parse(args);
            
            
            
            if (statement.type == StatementType.SYNTAX_ERROR) {
                logger.warn("语法错误：第{}行 {} \n tokens = {}", lineNumber, line, statement.getTokens());
                break;
            }
            
            statement.lineNumber = lineNumber;
            
            
            statements.add(statement);
        }
        
        // use statements
        for(Statement statement : statements) {
            switch (statement.type) {
            case ROLE_ONE_DIRECTION_MOVE:
            {
                RoleOneDirectionMoveStatement roleMoveStatement = (RoleOneDirectionMoveStatement) statement;
                RoleOneDirectionMoveTask roleOneDirectionMoveTask = new RoleOneDirectionMoveTask(roleMoveStatement, world);
                Role role = world.getRole(roleMoveStatement.argName);
                role.conditionTasks.add(roleOneDirectionMoveTask);
                break;
            }
            case ROLE_TWO_DIRECTION_MOVE:
                
                break;
            case SCREEN_PAIR_MOVE:
                
                break;
            case DELAY:
                DelayStatement delayStatement = (DelayStatement) statement;
                DelayTask delayTask = new DelayTask(delayStatement, world);
                world.conditionTasks.add(delayTask);
                break;
            case ROLE_TALK:
            {
                RoleTalkStatement roleTalkStatement = (RoleTalkStatement) statement;
                RoleSpeakTask roleSpeakTask = new RoleSpeakTask(roleTalkStatement, world);
                Role role = world.getRole(roleTalkStatement.roleName);
                role.conditionTasks.add(roleSpeakTask);
                break;
            }  
            case SCREEN_CTEATE:
                ScreenCreateStatement screenCreateStatement = (ScreenCreateStatement) statement;
                screenCreateStatement.work(world);
                break;
            case ROLE_CTEATE:
                RoleCreateStatement roleCreateStatement = (RoleCreateStatement) statement;
                roleCreateStatement.work(world);
                break;
            case MACRO_CREATE:
                break;
            default:
                logger.error("未定义语句行为：{}", statement.type);
                break;
            }
        }
        
        
    }
    
    private List<String> getArgsItemsByScript(String line) {
        line.trim();
        if(line.startsWith("//")) {
            return null;
        }
        String[] args= line.split(" ");
        List<String> list = new LinkedList<>();
        for (String item : args) {
            item.trim();
            if (item.length() > 0) {
                list.add(item);
            }
        }
        return list;
    }
}
