package xueli.craftgame.net.message;

import xueli.craftgame.net.ByteArrayBuilder;

public class Message {

	private byte id;
	private String message;

	public Message(byte id, String message) {
		this.id = id;
		this.message = message;
	}

	public byte getId() {
		return id;
	}

	public String getMessage() {
		return message;
	}

	public static Message getMessage(byte[] message) {
		byte id = message[0];
		String m = new String(message, 1, message.length - 1);
		return new Message(id, m);
	}

	public static String generateMessage(Message message) {
		ByteArrayBuilder builder = new ByteArrayBuilder();
		builder.put(message.getId()).putString(message.getMessage());
		return new String(builder.get(), HandshakeMessages.STANDARD_CHARSET);
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", message=" + message + "]";
	}

}
