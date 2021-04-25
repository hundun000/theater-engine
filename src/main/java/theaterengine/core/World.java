package theaterengine.core;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import theaterengine.entity.BaseItem;
import theaterengine.entity.IGameObject;
import theaterengine.entity.Role;
import theaterengine.entity.Screen;
import theaterengine.gui.StagePanel;
import theaterengine.script.Parser;
import theaterengine.script.StatementType;
import theaterengine.script.statement.DelayStatement;
import theaterengine.script.statement.TokenType;
import theaterengine.script.statement.RoleCreateStatement;
import theaterengine.script.statement.RoleOneDirectionMoveStatement;
import theaterengine.script.statement.RoleTalkStatement;
import theaterengine.script.statement.ScreenCreateStatement;
import theaterengine.task.ConditionTask;
import theaterengine.task.DelayTask;
import theaterengine.task.RoleOneDirectionMoveTask;
import theaterengine.task.RoleSpeakTask;


/**
 *
 * @author hundun
 * Created on 2019/04/01
 */
public class World extends BaseItem implements IGameObject {
	private static final Logger logger = LoggerFactory.getLogger(World.class);

	public final Map<String, Role> roles = new HashMap<>();
    public final Map<String, Screen> stageScreens = new HashMap<>();
    
    public int updateCounter;
    public int renderCounter;
    
	private StagePanel stagePanel;
	Parser parser;
	private WorldLoader loader = new WorldLoader();
	
	
	
	public World() {
	    super("world", 0, 0);
	    this.parser = new Parser();
	}
	
	
	
	
	
	


    @Override
    public void updateGame(World world) {
        for (ConditionTask conditionTask : conditionTasks) {
            conditionTask.clockArrive();
        }
        
        
        for (Role role : this.roles.values()) {
            role.updateGame(this);
        }
        
        for (Screen stageScreen : this.stageScreens.values()) {
            stageScreen.updateGame(this);
        }
    }

    @Override
    public void renderGame(Graphics2D g2d, World world) {
        stagePanel.repaint();
    }
    
    @Override
    public void postUpdateGame(World world) {
        super.postUpdateGame(world);
        
        for (Role role : this.roles.values()) {
            role.postUpdateGame(this);
        }
        
        for (Screen stageScreen : this.stageScreens.values()) {
            stageScreen.postUpdateGame(this);
        }
    }
    
    
    
    

    public Role getRole(String name) {
        Role role = roles.get(name);
        if (role == null) {
            logger.warn("Role未取到:{}", name);
        }
        return roles.get(name);
    }
    
    
    
    public int updateFrameMs = 100;
    public double updateFrameNumPerSecond = 1000.0 / updateFrameMs;
    public int renderFrameMs = 100;
    
    public void prepare(StagePanel stagePanel, List<String> docs) {
        this.roles.clear();
        this.stageScreens.clear();
        
        this.stagePanel = stagePanel;
        stagePanel.world = this;
        
        updateCounter = 0;
        renderCounter = 0;
        
        
        
        loader.reloadScriptIntoEngine(this, docs, parser);

    }
    
    public void start() {
        World world = this;
        Timer logicTimer = new Timer();
        
        logicTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateCounter++;
                world.updateGame(null);
                world.postUpdateGame(null);
            }
        }, 0, updateFrameMs);
        
        Timer renderTimer = new Timer();
        renderTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                renderCounter++;
                world.renderGame(null, null);
            }
        }, 0, renderFrameMs);
        
        MessageBus.restart();
    }

    
    public void addRole(Role role) {
        roles.put(role.name, role);
    }
    
    public void addScreen(Screen stageScreen) {
        stageScreens.put(stageScreen.name, stageScreen);
    }
    
    public Screen get(String name) {
        Screen screen = stageScreens.get(name);
        if (screen == null) {
            logger.warn("Screen未取到:{}", name);
        }
        return stageScreens.get(name);
    }
}

	
