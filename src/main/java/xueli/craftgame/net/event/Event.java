package xueli.craftgame.net.event;

import java.io.Serializable;

import xueli.craftgame.net.client.Client;
import xueli.craftgame.net.server.Server;

public abstract class Event implements Serializable {

	private static final long serialVersionUID = -7908454396166048767L;

	protected int clientId;
	protected String playerName;

	public Event(int clientId, String playerName) {
		this.clientId = clientId;
		this.playerName = playerName;

	}

	public abstract void invokeServer(Server server);

	public abstract void invokeClient(Client client);

	public int getClientId() {
		return clientId;
	}

	public String getPlayerName() {
		return playerName;
	}

}
