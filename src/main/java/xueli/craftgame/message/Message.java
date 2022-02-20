package xueli.craftgame.message;

import xueli.craftgame.client.CraftGameClient;
import xueli.craftgame.server.CraftgameServer;
import xueli.craftgame.server.ServerPlayer;

import java.io.Serializable;

public class Message implements Serializable {

	public Message() {

	}

	public void processServer(ServerPlayer from, CraftgameServer server) {

	}

	public void processClient(CraftGameClient client) {

	}

	@Override
	public String toString() {
		return super.toString();
	}

}
