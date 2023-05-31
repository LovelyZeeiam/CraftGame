package xueli.utils.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

public class EventBus {

	private final HashMap<Class<?>, ArrayList<Consumer<?>>> registers = new HashMap<>();

	public EventBus() {
	}

	public <T> void register(Class<T> clazz, Consumer<T> listener) {
		registers.computeIfAbsent(clazz, c -> new ArrayList<>()).add(listener);
	}

	public <T> void unregister(Class<T> clazz, Consumer<T> listener) {
		ArrayList<Consumer<?>> consumers = registers.get(clazz);
		if (consumers == null || consumers.isEmpty())
			return;
		consumers.remove(listener);
	}

	public void post(Object t) {
		Class<?> clazz = t.getClass();
		ArrayList<Consumer<?>> consumers = registers.get(clazz);
		if (consumers == null || consumers.isEmpty())
			return;
		consumers.forEach(c -> genericPost(t, c));
	}

	@SuppressWarnings("unchecked")
	private <T> void genericPost(Object t, Consumer<T> c) {
		c.accept((T) t);
	}

}
