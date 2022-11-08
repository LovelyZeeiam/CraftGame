package xueli.mcremake.classic.server;

import xueli.game2.network.ServerClientConnection;
import xueli.game2.network.processor.PacketProcessor;
import xueli.mcremake.classic.network.ServerPlayerInfo;
import xueli.mcremake.classic.network.protocol.C00HelloPacket;

public class MyServerConnection extends ServerClientConnection {
	
	private final CraftGameServer ctx;
	
	private final PacketProcessor packetProcessor = new PacketProcessor();
	
	{
		packetProcessor.addProcessor(C00HelloPacket.PROCESS_NAME, this::handleHello);
		
	}
	
	private ServerPlayerInfo playerInfo;
	
	public MyServerConnection(CraftGameServer ctx) {
		this.ctx = ctx;
		
		setPacketProcessor(packetProcessor);
		
	}
	
	public ServerPlayerInfo getPlayerInfo() {
		return playerInfo;
	}
	
	private void handleHello(C00HelloPacket packet) {
		System.out.println(packet);
		
	}
	
}
