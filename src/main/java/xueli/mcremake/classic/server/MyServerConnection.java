package xueli.mcremake.classic.server;

import xueli.game2.network.ConnectionStageListener;
import xueli.game2.network.Packet;
import xueli.game2.network.ServerClientConnection;
import xueli.game2.network.processor.PacketProcessor;
import xueli.mcremake.classic.network.ServerPlayerInfo;
import xueli.mcremake.classic.network.protocol.C00HelloPacket;

public class MyServerConnection extends ServerClientConnection {
	
	private final CraftGameServer ctx;
	
	private final PacketProcessor packetProcessor = new PacketProcessor();

	private ConnectionStageListener<MyServerConnection> listener = new ServerStageHelloListener(this);

	ServerPlayerInfo playerInfo;
	
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

	public ServerPlayerInfo getPlayerInfo() {
		return playerInfo;
	}
	
}
