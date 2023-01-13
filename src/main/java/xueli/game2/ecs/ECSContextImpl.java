package xueli.game2.ecs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import xueli.utils.events.DeferredEventBus;

public class ECSContextImpl implements ECSContext {
	
	private long entityIndex = 0;
	
	private final HashMap<Class<?>, ComponentInfo> componentInstances = new HashMap<>();
	
	@Override
	public long spawn() {
		return entityIndex++;
	}
	
	@Override
	public void kill(long entityId) {
		componentInstances.forEach((c, i) -> {
			i.removeComponent(entityId);
		});
	}

	@Override
	public void addComponent(long entityId, Object component) {
		componentInstances.computeIfAbsent(component.getClass(), c -> new ComponentInfo())
			.addComponent(entityId, component);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getComponent(long entityId, Class<T> componentClazz) {
		ComponentInfo info = componentInstances.get(componentClazz);
		if(info == null) return null;
		return (T) info.getComponent(entityId);
	}

	@Override
	public <T> void removeComponent(long entityId, Class<T> componentClazz) {
		ComponentInfo info = componentInstances.get(componentClazz);
		if(info == null) return;
		info.removeComponent(entityId);
	}
	
	final ResourceList resources = new ResourceListImpl();

	@Override
	public void addResource(Object resource) {
		resources.add(resource);
	}
	
	public <R> R getResource(Class<R> resourceClazz) {
		return resources.get(resourceClazz);
	}
	
	@Override
	public <R> void removeResource(Class<R> resourceClazz) {
		resources.remove(resourceClazz);
	}
	
	@Override
	public <T> Set<Long> query(Class<T> clazz) {
		ComponentInfo info = componentInstances.get(clazz);
		if(info == null) return new HashSet<>();
		return info.query();
	}
	
	private final ArrayList<ECSSystem> systems = new ArrayList<>();
	
	@Override
	public void addSystem(ECSSystem system) {
		systems.add(system);
	}
	
	private final DeferredEventBus busTemp = new DeferredEventBus(), bus = new DeferredEventBus();
	
	@Override
	public void broadcast(Object e) {
		busTemp.post(e);
	}

	@Override
	public <E> E readEvent(Class<E> clazz) {
		return bus.read(clazz);
	}

	@Override
	public void startUp() {
		systems.forEach(s -> s.start(this));
	}

	@Override
	public void update() {
		systems.forEach(s -> s.update(this));
		
		this.bus.clear();
		this.busTemp.copyTo(bus);
		this.busTemp.clear();
		
	}

	@Override
	public void shutdown() {
		systems.forEach(s -> s.shutdown(this));
	}
	
}
