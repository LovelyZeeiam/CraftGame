package xueli.game2.network;

import xueli.game2.network.processor.PacketProcessor;

public class ConnectionStageListener<T extends ClientConnection> {

	private final T ctx;
	protected final PacketProcessor processor = new PacketProcessor();

	public ConnectionStageListener(T ctx) {
		this.ctx = ctx;

	}

	public void doProcess(Packet packet) {
		processor.doProcess(packet);
	}

	public T getConnection() {
		return ctx;
	}

}
