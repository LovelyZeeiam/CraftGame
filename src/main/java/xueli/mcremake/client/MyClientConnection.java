package xueli.mcremake.client;

import xueli.game2.network.ClientConnection;

import java.io.IOException;
import java.net.InetSocketAddress;

public class MyClientConnection {

	private InetSocketAddress addr;
	private ClientConnection conn;

	private boolean isRunning = true;

	public MyClientConnection(InetSocketAddress addr) throws IOException {
		this.addr = addr;

	}

	public void close() {
		this.isRunning = false;

	}

}
