package xueli.craftgame.net.interchange;

public class MessageChat extends Message {

	private static final long serialVersionUID = 4306807199748570564L;

	public MessageChat(String chat) {
		super("CHAT", chat);
	}

}
