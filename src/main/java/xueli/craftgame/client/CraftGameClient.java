package xueli.craftgame.client;

import xueli.craftgame.server.PlayerInfo;

import java.io.File;

public class CraftGameClient {

	private PlayerInfo info = new PlayerInfo("LoveliZeeiam");

	private final IOAdapter client;
	private boolean running = false;

	public CraftGameClient(String hostname, int port) {
		this.client = new ClientWrapper(this, hostname, port);
		
	}
	
	// public CraftGameClient(File localLevel) {
		
	// }
	
	public void run() throws Exception {
		this.client.start();
		this.running = true;

		

		this.client.close();

	}
	
	public PlayerInfo getInfo() {
		return info;
	}
	
	public static void main(String[] args) throws Exception {
		new CraftGameClient("127.0.0.1", 8848).run();

	}

}
