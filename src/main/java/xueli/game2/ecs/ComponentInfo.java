package xueli.game2.ecs;

import java.util.HashMap;
import java.util.Set;

class ComponentInfo {

	private final HashMap<Long, Object> components = new HashMap<>();

	public void addComponent(long entityId, Object component) {
		components.put(entityId, component);
	}

	public Object getComponent(long entityId) {
		return components.get(entityId);
	}

	public void removeComponent(long entityId) {
		components.remove(entityId);
	}

	public Set<Long> query() {
		return components.keySet();
	}

}
