package theaterengine;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import theaterengine.script.Parser;

/**
 *
 * @author hundun
 * Created on 2019/04/01
 */
public class RoleFactory {
	private static final Logger logger = LoggerFactory.getLogger(RoleFactory.class);
	
	static Map<String, Role> roles = new HashMap<>();
	
	public static Role get(String name) {
		Role role = roles.get(name);
		if (role == null) {
			logger.warn("Role未取到:{}", name);
		}
		return roles.get(name);
	}
	
	public static void registerAndAddToStage(Role role, StagePanel stagePanel) {
		roles.put(role.name, role);
		stagePanel.addRole(role);
	}
}
