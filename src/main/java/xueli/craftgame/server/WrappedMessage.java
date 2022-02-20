package xueli.craftgame.server;

import xueli.craftgame.message.Message;

public class WrappedMessage {

	private Message message;
	private ServerPlayer from;

	public WrappedMessage(Message message, ServerPlayer from) {
		this.message = message;
		this.from = from;
	}

	public Message getMessage() {
		return message;
	}

	public ServerPlayer getFrom() {
		return from;
	}

}
