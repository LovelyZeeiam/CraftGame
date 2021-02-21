package xueli.craftgame.net.client;

import java.net.URI;
import java.util.Base64;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import xueli.craftgame.WorldLogic;
import xueli.craftgame.net.event.Event;
import xueli.craftgame.net.message.HandshakeMessages;
import xueli.craftgame.net.message.Message;
import xueli.craftgame.net.server.Server;
import xueli.craftgame.world.World;
import xueli.gamengine.utils.vector.Vector;
import xueli.utils.Bytes;
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
		Message m = Message.getMessage(message.getBytes(HandshakeMessages.STANDARD_CHARSET));
		switch (m.getId()) {
		case HandshakeMessages.SERVER_RESPONSE_PLAYER_CONNECT: {
			this.id = Integer.parseInt(m.getMessage());
			Logger.info("[Client] " + logic.getCg().getPlayerStat().getName() + " join the game: " + id);

			this.send(Message
					.generateMessage(new Message(HandshakeMessages.PREPARE_SPAWN_POINT, Integer.toString(this.id))));

			break;
		}
		case HandshakeMessages.PREPARE_SPAWN_POINT_OK: {
			Logger.info("[Client] Prepare spawnpoint map ok.");
			this.send(Message.generateMessage(
					new Message(HandshakeMessages.HANDSHAKE_CLIENT_REQUEST_PLAYER_POSITION, Integer.toString(id))));

			break;
		}
		case HandshakeMessages.HANDSHAKE_SERVER_ANSWER_PLAYER_POSITION_AND_NOTIFY: {
			String base64PosData = m.getMessage();
			byte[] posObjData = Base64.getDecoder().decode(base64PosData);
			Vector playerPos = (Vector) Bytes.getObject(posObjData);

			if (playerPos == null) {
				return;
			}

			logic.setPlayerPos(playerPos);

			synchronized (Waiter.waitObject) {
				Waiter.waitObject.notify();
			}

			break;
		}
		case HandshakeMessages.EVENT: {
			Event event = Message.getEvent(message.getBytes(HandshakeMessages.STANDARD_CHARSET));
			event.invokeClient(this);

			break;
		}

		}

	}

	@Override
	public void onClose(int code, String reason, boolean remote) {
		Logger.info("[Client] Client closed: " + code + ", " + reason + ", remote: " + remote);

	}

	@Override
	public void onError(Exception ex) {
		Logger.error("[Client] Exception!");
		ex.printStackTrace();

	}

	public int getId() {
		return id;
	}

	public WorldLogic getLogic() {
		return logic;
	}

	public World getWorld() {
		return logic.getWorld();
	}

}
