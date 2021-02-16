package xueli.craftgame.client;

import java.net.URI;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import xueli.craftgame.server.Server;
import xueli.utils.Logger;

public class Client extends WebSocketClient {

	public Client(URI serverUri) {
		super(serverUri);
		
	}

	public Client() throws Exception {
		super(new URI("ws://localhost:" + Server.SERVER_PORT));
		
	}
	
	@Override
	public void onOpen(ServerHandshake handshakedata) {

	}

	@Override
	public void onMessage(String message) {
		
	}

	@Override
	public void onClose(int code, String reason, boolean remote) {
		
		
	}

	@Override
	public void onError(Exception ex) {
		Logger.error("[Client] Exception: " + ex.getMessage());
		
	}

}
