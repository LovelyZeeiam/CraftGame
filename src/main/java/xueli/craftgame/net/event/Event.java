package xueli.craftgame.net.event;

import java.io.Serializable;

import xueli.craftgame.net.client.Client;
import xueli.craftgame.net.player.PlayerStat;
import xueli.craftgame.net.server.Server;

public abstract class Event implements Serializable {

	private static final long serialVersionUID = -7908454396166048767L;

	protected int clientId;
	protected PlayerStat playerStat;

	public Event(int clientId, PlayerStat playerStat) {
		this.clientId = clientId;
		this.playerStat = playerStat;

	}

	public abstract void invokeServer(Server server);

	public abstract void invokeClient(Client client);

	public int getClientId() {
		return clientId;
	}

	public PlayerStat getPlayerStat() {
		return playerStat;
	}

}
