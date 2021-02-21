package xueli.craftgame.net.server;

import java.io.Serializable;

import xueli.gamengine.utils.vector.Vector;

public class ServerPlayer implements Serializable {

	private static final long serialVersionUID = -6934059673282759689L;

	private String clientName;

	private Vector playerPos;
	private PlayerState state;

	public ServerPlayer(String clientName, Vector playerPos) {
		this.clientName = clientName;
		this.playerPos = playerPos;
		this.state = PlayerState.GAMEPREPARE;

	}

	public PlayerState getState() {
		return state;
	}

	public void setState(PlayerState state) {
		this.state = state;
	}

	public String getClientName() {
		return clientName;
	}

	public Vector getPlayerPos() {
		return playerPos;
	}

	public void setPlayerPos(Vector playerPos) {
		this.playerPos = playerPos;
	}

}
