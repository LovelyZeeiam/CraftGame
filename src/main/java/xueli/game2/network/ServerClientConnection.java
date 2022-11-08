package xueli.game2.network;

import xueli.game2.network.processor.PacketProcessor;

public class ServerClientConnection extends ClientConnection {

	public ServerClientConnection() {
	}
	
	@Override
	public void setPacketProcessor(PacketProcessor processor) {
		super.setPacketProcessor(processor);
	}
	
}
