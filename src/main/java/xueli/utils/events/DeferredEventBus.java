package xueli.utils.events;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class DeferredEventBus {

	private HashMap<Class<?>, Queue<Object>> eventStorage = new HashMap<>();

	public DeferredEventBus() {
	}

	public void post(Object obj) {
		Class<?> clazz = obj.getClass();
		Queue<Object> queue = eventStorage.computeIfAbsent(clazz, c -> new LinkedList<>());
		queue.add(obj);
	}

	@SuppressWarnings("unchecked")
	public <T> T read(Class<T> clazz) {
		Queue<Object> queue = eventStorage.get(clazz);
		if (queue == null)
			return null;
		return (T) queue.poll();
	}

	public void clear() {
		eventStorage.values().forEach(Queue::clear);
	}

	public void copyTo(DeferredEventBus e) {
		this.eventStorage.forEach((c, q) -> e.eventStorage.computeIfAbsent(c, c1 -> new LinkedList<>()).addAll(q));
	}

}
