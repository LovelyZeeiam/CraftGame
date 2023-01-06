package xueli.mcremake.client;

import java.io.IOException;
import java.net.InetSocketAddress;

import xueli.game2.network.ClientConnection;

/**
 * Mark it deprecated because now we don't use this
 */
@SuppressWarnings("unused")
@Deprecated
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
