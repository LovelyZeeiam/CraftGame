package xueli.craftgame.net.event;

import xueli.craftgame.net.client.Client;
import xueli.craftgame.net.server.Server;
import xueli.craftgame.net.server.ServerPlayer;
import xueli.gamengine.utils.vector.Vector;

public class EventPlayerMove extends Event {

	private static final long serialVersionUID = 759328432123163763L;

	private float mx, my, mz;

	public EventPlayerMove(float mx, float my, float mz, int clientId, String playerName) {
		super(clientId, playerName);
		this.mx = mx;
		this.my = my;
		this.mz = mz;

	}

	@Override
	public void invokeServer(Server server) {
		ServerPlayer player = server.getWorld().getPlayer(playerName);
		Vector playerPos = player.getPlayerPos();
		playerPos.x += mx;
		playerPos.y += my;
		playerPos.z += mz;

	}

	@Override
	public void invokeClient(Client client) {
		// 由于已经在事件响应之前将事件进行处理了 所以在这里就不用对发出事件的客户端进行处理事件辽
		if (client.getId() == this.clientId)
			return;

		ServerPlayer player = client.getWorld().getPlayer(playerName);
		Vector playerPos = player.getPlayerPos();
		playerPos.x += mx;
		playerPos.y += my;
		playerPos.z += mz;

	}

}
