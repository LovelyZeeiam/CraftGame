package xueli.mcremake.server;

import xueli.game2.network.ConnectionStageListener;
import xueli.game2.network.Packet;
import xueli.game2.network.ServerClientConnection;
import xueli.game2.network.processor.PacketProcessor;

public class MyServerConnection extends ServerClientConnection {
	
	private final CraftGameServer ctx;
	
	private final PacketProcessor packetProcessor = new PacketProcessor();

	private ConnectionStageListener<MyServerConnection> listener = new ServerStageHelloListener(this);
	
	public MyServerConnection(CraftGameServer ctx) {
		this.ctx = ctx;
	}

	@Override
	protected void packetRead(Packet msg) {
		if(listener != null) {
			listener.doProcess(msg);
		} else {
			System.err.println("Bugs? No Packet Listener for: " + msg);
		}

	}

	public void setListener(ConnectionStageListener<MyServerConnection> listener) {
		this.listener = listener;
	}
	
}
