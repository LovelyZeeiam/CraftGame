package xueli.craftgame.net.event;

import xueli.craftgame.net.client.Client;
import xueli.craftgame.net.player.PlayerStat;
import xueli.craftgame.net.server.Server;
import xueli.craftgame.net.server.ServerPlayer;
import xueli.gamengine.utils.vector.Vector;

public class EventPlayerTurn extends Event {

	private static final long serialVersionUID = -4381176019014887071L;

	private float rotX, rotY;

	public EventPlayerTurn(float rotX, float rotY, int clientId, PlayerStat stat) {
		super(clientId, stat);

		this.rotX = rotX;
		this.rotY = rotY;

	}

	@Override
	public void invokeServer(Server server) {
		ServerPlayer player = server.getWorld().getPlayer(playerStat.getName());
		Vector playerPos = player.getPlayerPos();
		playerPos.rotX += rotX;
		playerPos.rotY += rotY;

	}

	@Override
	public void invokeClient(Client client) {
		// 由于已经在事件响应之前将事件进行处理了 所以在这里就不用对发出事件的客户端进行处理事件辽
		if (client.getId() == this.clientId)
			return;

		ServerPlayer player = client.getWorld().getPlayer(playerStat.getName());
		Vector playerPos = player.getPlayerPos();
		playerPos.rotX += rotX;
		playerPos.rotY += rotY;

	}

}
