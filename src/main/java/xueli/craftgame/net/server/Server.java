package xueli.craftgame.net.server;

import java.net.InetSocketAddress;
import java.util.HashMap;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import xueli.craftgame.WorldLogic;
import xueli.craftgame.net.message.Message;
import xueli.craftgame.net.message.MessageDefine;
import xueli.craftgame.world.World;
import xueli.utils.Logger;

public class Server extends WebSocketServer {

	public static final int SERVER_PORT = 1145;

	private WorldLogic logic;
	private World world;

	private HashMap<Integer, String> clientIdNamesHashMap = new HashMap<Integer, String>();
	private int idCount = 0;

	public Server(WorldLogic logic) {
		super(new InetSocketAddress(SERVER_PORT));
		this.logic = logic;
		this.world = logic.getWorld();

	}

	@Override
	public void onOpen(WebSocket conn, ClientHandshake handshake) {

	}

	@Override
	public void onClose(WebSocket conn, int code, String reason, boolean remote) {

	}

	@Override
	public void onMessage(WebSocket conn, String message) {
		Message m = Message.getMessage(message.getBytes(MessageDefine.STANDARD_CHARSET));
		switch (m.getId()) {
		case MessageDefine.PLAYER_CONNECT:
			int id = idCount;
			clientIdNamesHashMap.put(id, m.getMessage());
			Logger.info("[Server] " + m.getMessage() + " join the game: " + id);

			conn.send(Message.generateMessage(new Message(MessageDefine.PLAYER_CONNECT, Integer.toString(id))));

			break;
		case MessageDefine.PREPARE_SPAWN_POINT:
			int clientID = Integer.parseInt(m.getMessage());
			// TODO: 根据玩家的位置生成地图

			// TODO: 如果是单纯的服务端，此时就需要给client发地图了

			break;

		}

	}

	@Override
	public void onError(WebSocket conn, Exception ex) {
		Logger.error("[Server] Exception from " + conn.getRemoteSocketAddress() + ": ");
		ex.printStackTrace();

	}

	@Override
	public void onStart() {

	}

	public static void main(String[] args) {

	}

}
