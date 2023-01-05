package xueli.utils.notifiable;

import java.util.ArrayList;
import java.util.function.Consumer;

public class NotifiableValue<T> {

	private T value = null;
	private ArrayList<Consumer<T>> listeners = new ArrayList<>();

	public NotifiableValue() {
	}

	public NotifiableValue(T initialValue) {
		this.value = initialValue;
	}

	public void addListeners(Consumer<T> listener) {
		listeners.add(listener);
	}

	public void set(T value) {
		this.value = value;
		listeners.forEach(c -> c.accept(value));
	}

	public T getValue() {
		return value;
	}

}
