package xueli.craftgame.consoletest;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.server.WebSocketServer;

import xueli.utils.Logger;

/**
 * This is a simple chat room
 */
public class ServerTest {
	
	public static class Server extends WebSocketServer {
		
		private HashMap<InetSocketAddress, String> names = new HashMap<InetSocketAddress, String>();
 		
		public Server(InetSocketAddress address) {
			super(address);
			
		}

		@Override
		public void onOpen(WebSocket conn, ClientHandshake handshake) {
			
		}

		@Override
		public void onClose(WebSocket conn, int code, String reason, boolean remote) {
			Logger.info("Server closed: " + code + ", " + reason + ", remote: " + remote);
			
		}

		@Override
		public void onMessage(WebSocket conn, String message) {
			if(!names.containsKey(conn.getRemoteSocketAddress())) {
				names.put(conn.getRemoteSocketAddress(), message);
				Logger.info("\'" + message + "\" from" + conn.getRemoteSocketAddress() + " join the room.");
				
			} else {
				String nameString = names.get(conn.getRemoteSocketAddress());
				this.broadcast(nameString + ": " + message);
				
				Logger.info("Client \"" + nameString + "\" message: " + message);
				
			}
			
		}

		@Override
		public void onError(WebSocket conn, Exception ex) {
			Logger.error("Server error: " + ex.getMessage());
			
		}

		@Override
		public void onStart() {
			
			
		}
		
		public static void main(String[] args) throws IOException, InterruptedException {
			Server server = new Server(new InetSocketAddress(1145));
			server.start();
			
			Scanner scanner = new Scanner(System.in);
			a: while (true) {
				String command = scanner.nextLine();
				if(command.equals("q")) {
					break a;
				}
			}
			
			scanner.close();
			server.stop();
			
		}
		
	}
	
	public static class Client extends WebSocketClient {

		public Client(URI serverUri) {
			super(serverUri);
			
		}

		@Override
		public void onOpen(ServerHandshake handshakedata) {
			Logger.info("Client Started.");
						
		}

		@Override
		public void onMessage(String message) {
			Logger.info(message);
			
		}

		@Override
		public void onClose(int code, String reason, boolean remote) {
			Logger.info("Client Closed: " + code + ", " + reason);
			System.exit(0);
			
		}

		@Override
		public void onError(Exception ex) {
			Logger.error("Client Error: " + ex.getMessage());
			
		}
		
		public static void main(String[] args) throws Exception {
			Client client = new Client(new URI("ws://localhost:1145"));
			client.connectBlocking(5000, TimeUnit.MILLISECONDS);
			
			Scanner scanner = new Scanner(System.in);
			
			System.out.print("Input your name: ");
			String nameString = scanner.nextLine();
			client.send(nameString);
			
			a: while (true) {
				String command = scanner.nextLine();
				if(command.equals("quit")) {
					break a;
				} else {
					client.send(command);
				}
				
			}
			
			scanner.close();
			client.closeBlocking();
			
		}
		
	}

}
