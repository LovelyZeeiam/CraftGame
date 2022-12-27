package xueli.mcremake.classic.server;

import xueli.game2.lifecycle.RunnableLifeCycle;
import xueli.game2.network.Server;
import xueli.mcremake.classic.network.PacketSourceSide;

public class CraftGameServer implements RunnableLifeCycle {

	private volatile boolean isRunning = false;

	private final Server<MyServerConnection> server;

	public CraftGameServer(int port) {
		this.server = new Server<>(port, () -> new MyServerConnection(this), PacketSourceSide.FROM_SERVER.getProtocol(), PacketSourceSide.FROM_CLIENT.getProtocol());
		
	}

	@Override
	public void init() {
		server.init();
		
		this.isRunning = true;
		
	}

	@Override
	public void tick() {
		server.tick();
		
	}

	@Override
	public void release() {
		server.release();
		
	}
	
	public void stop() {
		this.isRunning = false;
		
	}

	@Override
	public boolean isRunning() {
		return isRunning;
	}
	
	public Server<MyServerConnection> getServer() {
		return server;
	}

}
