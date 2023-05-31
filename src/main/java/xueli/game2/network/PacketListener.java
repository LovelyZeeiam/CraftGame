package xueli.game2.network;

public interface PacketListener {

	public void onPacketSentSuccessfully();

	default Packet onPacketSendFailure() {
		return null;
	}

}
