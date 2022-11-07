package xueli.mcremake.classic.server;

import xueli.game2.lifecycle.RunnableLifeCycle;
import xueli.game2.network.ServerConnection;
import xueli.mcremake.classic.network.PacketSourceSide;

public class CraftGameServer implements RunnableLifeCycle {

	private boolean isRunning = false;

	private final ServerConnection connection;

	public CraftGameServer(int port) {
		this.connection = new ServerConnection(port, conn -> new ServerPlayerListener(this, conn), PacketSourceSide.FROM_SERVER.getProtocol(), PacketSourceSide.FROM_CLIENT.getProtocol());

	}

	@Override
	public void init() {


		this.isRunning = true;

	}

	@Override
	public void tick() {

	}

	@Override
	public void release() {

	}

	@Override
	public boolean isRunning() {
		return isRunning;
	}

}
