package xueli.mcremake.server;

import xueli.game2.network.ConnectionStageListener;
import xueli.mcremake.network.protocol.C00HelloPacket;
import xueli.mcremake.network.protocol.S00PlayPacket;

public class ServerStageHelloListener extends ConnectionStageListener<MyServerConnection> {

	public ServerStageHelloListener(MyServerConnection ctx) {
		super(ctx);
		processor.addProcessor(C00HelloPacket.class, this::handleHello);

	}

	private void handleHello(C00HelloPacket packet) {
		try {
			Thread.currentThread().wait(1000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		getConnection().writeAndFlush(new S00PlayPacket(0, 0, 0));

	}

}
