package xueli.game2.network;

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
