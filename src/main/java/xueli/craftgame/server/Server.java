package xueli.craftgame.server;

import java.net.InetSocketAddress;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import xueli.utils.Logger;

public class Server extends WebSocketServer {
	
	public static final int SERVER_PORT = 1145;

	public Server() {
		super(new InetSocketAddress(SERVER_PORT));
		
	}
	
	@Override
	public void onOpen(WebSocket conn, ClientHandshake handshake) {
		
		
	}

	@Override
	public void onClose(WebSocket conn, int code, String reason, boolean remote) {
		
		
	}

	@Override
	public void onMessage(WebSocket conn, String message) {
		
	}

	@Override
	public void onError(WebSocket conn, Exception ex) {
		Logger.error("[Server] Exception from " + conn.getRemoteSocketAddress() + ": " + ex.getMessage());
		
	}

	@Override
	public void onStart() {
		
	}
	
	public static void main(String[] args) {
		
		
	}
	
}
