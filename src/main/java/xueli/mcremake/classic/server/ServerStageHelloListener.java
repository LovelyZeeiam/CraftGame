package xueli.mcremake.classic.server;

import xueli.game2.network.ConnectionStageListener;
import xueli.mcremake.classic.network.protocol.C00HelloPacket;

public class ServerStageHelloListener extends ConnectionStageListener<MyServerConnection> {

	public ServerStageHelloListener(MyServerConnection ctx) {
		super(ctx);
		processor.addProcessor(C00HelloPacket.class, this::handleHello);

	}

	private void handleHello(C00HelloPacket packet) {
		System.out.println(packet);

	}

}
