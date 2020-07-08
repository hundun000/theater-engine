package theaterengine.entity;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import theaterengine.gui.StagePanel;
import theaterengine.script.Parser;

/**
 *
 * @author hundun
 * Created on 2019/04/01
 */
public class ScreenFactory {
	private static final Logger logger = LoggerFactory.getLogger(ScreenFactory.class);
	
	static Map<String, Screen> sceens = new HashMap<>();
	
	public static Screen get(String name) {
		Screen screen = sceens.get(name);
		if (screen == null) {
			logger.warn("Screen未取到:{}", name);
		}
		return sceens.get(name);
	}
	
	public static void registerAndAddToStage(Screen screen, StagePanel stagePanel) {
		sceens.put(screen.name, screen);
		stagePanel.addScreen(screen);
	}
}