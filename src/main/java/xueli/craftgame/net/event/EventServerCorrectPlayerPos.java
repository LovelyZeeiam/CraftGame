package xueli.craftgame.net.event;

import java.util.HashMap;

import xueli.craftgame.net.client.Client;
import xueli.craftgame.net.server.Server;
import xueli.craftgame.net.server.ServerPlayer;

public class EventServerCorrectPlayerPos extends Event {

	private static final long serialVersionUID = -755770298124912170L;

	private HashMap<String, ServerPlayer> playerPos;

	public EventServerCorrectPlayerPos(HashMap<String, ServerPlayer> playerPos) {
		super(-1, null);
		this.playerPos = playerPos;

	}

	@Override
	public void invokeServer(Server server) {
		// 不存在的
		return;
	}

	@Override
	public void invokeClient(Client client) {
		client.getWorld().correctPlayers(playerPos);

	}

}
