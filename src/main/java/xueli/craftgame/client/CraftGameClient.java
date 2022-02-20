package xueli.craftgame.client;

import xueli.craftgame.server.MessagePlayerInfo;
import xueli.craftgame.server.PlayerInfo;

import java.io.IOException;

public class CraftGameClient {

	private PlayerInfo info = new PlayerInfo("LoveliZeeiam");

	private final ClientWrapper client;
	private boolean running = false;

	public CraftGameClient(String hostname, int port) {
		this.client = new ClientWrapper(hostname, port);

	}

	public void run() throws IOException {
		this.client.start();
		this.running = true;

		this.client.sendMessage(new MessagePlayerInfo(info)).awaitUninterruptibly();

		while(!this.client.isAuthentiated()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		this.client.close();

	}

	public static void main(String[] args) throws IOException {
		new CraftGameClient("127.0.0.1", 8848).run();

	}

}
