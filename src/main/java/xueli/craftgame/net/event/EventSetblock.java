package xueli.craftgame.net.event;

import xueli.craftgame.block.Block;
import xueli.craftgame.net.client.Client;
import xueli.craftgame.net.player.PlayerStat;
import xueli.craftgame.net.server.Server;
import xueli.utils.Logger;

public class EventSetblock extends Event {

	private Block block;
	private int x, y, z;

	public EventSetblock(int x, int y, int z, Block block, int clientId, PlayerStat playerStat) {
		super(clientId, playerStat);

		this.block = block;
		this.x = x;
		this.y = y;
		this.z = z;

	}

	private static final long serialVersionUID = 4049127242314470257L;

	@Override
	public void invokeServer(Server server) {
		server.getWorld().setBlock(x, y, z, block, playerStat);
		Logger.info("[Server] Player set block in " + x + ", " + y + ", " + z + ", "
				+ (block == null ? null : block.getData().getNamespace()) + ": " + playerStat.getName());

	}

	@Override
	public void invokeClient(Client client) {
		client.getWorld().setBlock(x, y, z, block, playerStat);

	}

}
