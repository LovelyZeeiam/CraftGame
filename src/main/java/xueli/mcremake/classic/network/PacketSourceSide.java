package xueli.mcremake.classic.network;

import xueli.game2.network.Protocol;
import xueli.mcremake.classic.network.protocol.C00HelloPacket;

public enum PacketSourceSide {

	FROM_SERVER(new Protocol()),
	
	FROM_CLIENT(new Protocol()
			.register(0x00, C00HelloPacket.class, C00HelloPacket::new));

	private Protocol protocol;
	PacketSourceSide(Protocol p) {
		this.protocol = p;
	}

	public Protocol getProtocol() {
		return protocol;
	}

}
