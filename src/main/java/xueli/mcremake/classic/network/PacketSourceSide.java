package xueli.mcremake.classic.network;

import xueli.game2.network.Protocol;

public enum PacketSourceSide {

	FROM_SERVER(new Protocol()),
	FROM_CLIENT(new Protocol());

	private Protocol protocol;
	PacketSourceSide(Protocol p) {
		this.protocol = p;
	}

	public Protocol getProtocol() {
		return protocol;
	}

}
