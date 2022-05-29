package xueli.craftgame.event;

import java.util.concurrent.atomic.AtomicBoolean;

public class FutureEvent<T> {

	private AtomicBoolean done = new AtomicBoolean(false);
	private T value;

	public void setValue(T value) {
		this.value = value;
		this.done.set(true);
	}

	public T getValue() {
		return value;
	}

	public boolean isDone() {
		return done.get();
	}

}
