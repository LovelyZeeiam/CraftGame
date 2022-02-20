package xueli.craftgame.message;

import xueli.craftgame.client.CraftGameClient;
import xueli.craftgame.server.CraftgameServer;
import xueli.craftgame.server.PlayerInfo;
import xueli.craftgame.server.ServerPlayer;

public class MessageChatMessage extends Message {

	private PlayerInfo from;
	private String message;

	public MessageChatMessage(String message) {
		this.message = message;

	}

	public MessageChatMessage(PlayerInfo from, String message) {
		this.from = from;
		this.message = message;
	}

	@Override
	public void processServer(ServerPlayer from, CraftgameServer server) {
		server.getServer().sendMessage(new MessageChatMessage(from.getInfo(), this.message), false);
	}

	@Override
	public void processClient(CraftGameClient client) {
		System.out.println("[" + from.getName() + "] " + message);
	}

	public PlayerInfo getFrom() {
		return from;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "MessageChatMessage{" +
				"message='" + message + '\'' +
				'}';
	}

}
