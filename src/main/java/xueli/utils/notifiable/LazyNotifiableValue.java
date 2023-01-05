package xueli.utils.notifiable;

public class LazyNotifiableValue<T> extends NotifiableValue<T> {

	private T lazyValue = null;

	public LazyNotifiableValue() {
	}

	public LazyNotifiableValue(T initialValue) {
		super(initialValue);
	}

	@Override
	public void set(T value) {
		this.lazyValue = value;
	}

	public void flush() {
		if(this.lazyValue != null && !this.lazyValue.equals(this.getValue())) {
			super.set(this.lazyValue);
			this.lazyValue = null;
		}
	}

}
