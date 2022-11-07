package xueli.mcremake.classic.server;

import xueli.game2.network.ClientConnection;
import xueli.game2.network.processor.PacketProcessor;

public class ServerPlayerListener extends PacketProcessor {

	private CraftGameServer ctx;
	private ClientConnection connection;

	public ServerPlayerListener(CraftGameServer ctx, ClientConnection connection) {
		this.ctx = ctx;
		this.connection = connection;

		initProcessor();

	}

	private void initProcessor() {


	}

}
