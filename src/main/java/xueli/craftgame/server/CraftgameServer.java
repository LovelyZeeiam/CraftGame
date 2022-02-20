package xueli.craftgame.server;

import xueli.craftgame.message.Message;

import java.io.IOException;

public class CraftgameServer {

	private static final int PORT = 8848;

	private boolean running = false;
	private ServerWrapper server;

	public CraftgameServer() {
		this.server = new ServerWrapper(PORT);

	}

	public void run() throws IOException {
		this.server.start();
		this.running = true;

		while (running) {
			WrappedMessage message = server.popMessage();
			if(message == null) continue;

			Message msg = message.getMessage();
			msg.processServer(message.getFrom(), this);

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

		try {
			this.server.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public ServerWrapper getServer() {
		return server;
	}

	public static void main(String[] args) throws IOException {
		new CraftgameServer().run();
	}

}
