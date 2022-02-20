package xueli.craftgame.message;

public class MessageObject<T> extends Message {

	private T t;

	public MessageObject(T t) {
		this.t = t;
	}

	public T getValue() {
		return t;
	}

	@Override
	public String toString() {
		return "MessageObject{" +
				"t=" + t +
				'}';
	}

}
