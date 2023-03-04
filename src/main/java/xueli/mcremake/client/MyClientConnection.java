package xueli.mcremake.client;

import java.io.IOException;
import java.net.InetSocketAddress;

import xueli.game2.network.ClientConnection;

/**
 * Mark it deprecated because now we don't use this
 * If I can't make it Promise-like just as Javascript so it is maybe easy to use
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
