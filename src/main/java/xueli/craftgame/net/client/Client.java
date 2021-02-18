package xueli.craftgame.net.client;

import java.net.URI;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import xueli.craftgame.WorldLogic;
import xueli.craftgame.net.message.Message;
import xueli.craftgame.net.message.MessageDefine;
import xueli.craftgame.net.server.Server;
import xueli.utils.Logger;
import xueli.utils.Waiter;

public class Client extends WebSocketClient {

	private WorldLogic logic;
	private int id;

	public Client(URI serverUri, WorldLogic logic) {
		super(serverUri);
		this.logic = logic;

	}

	public Client(WorldLogic logic) throws Exception {
		super(new URI("ws://localhost:" + Server.SERVER_PORT));
		this.logic = logic;

	}

	@Override
	public void onOpen(ServerHandshake handshakedata) {

	}

	@Override
	public void onMessage(String message) {
		Message m = Message.getMessage(message.getBytes(MessageDefine.STANDARD_CHARSET));
		switch (m.getId()) {
		case MessageDefine.PLAYER_CONNECT:
			this.id = Integer.parseInt(m.getMessage());
			Logger.info("[Client] " + logic.getCg().getPlayerStat().getName() + " join the game: " + id);

			this.send(
					Message.generateMessage(new Message(MessageDefine.PREPARE_SPAWN_POINT, Integer.toString(this.id))));
			
			break;
		case MessageDefine.PREPARE_SPAWN_POINT_OK:
			Logger.info("[Client] Prepare spawnpoint map ok.");
			synchronized (Waiter.waitObject) {
				Waiter.waitObject.notify();
				
			}
			
			break;

		}

	}

	@Override
	public void onClose(int code, String reason, boolean remote) {

	}

	@Override
	public void onError(Exception ex) {
		Logger.error("[Client] Exception!");
		ex.printStackTrace();

	}

}
