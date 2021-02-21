package xueli.craftgame.net.server;

import java.net.InetSocketAddress;
import java.util.Base64;
import java.util.HashMap;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import xueli.craftgame.WorldLogic;
import xueli.craftgame.net.event.Event;
import xueli.craftgame.net.event.EventServerCorrectPlayerPos;
import xueli.craftgame.net.message.HandshakeMessages;
import xueli.craftgame.net.message.Message;
import xueli.craftgame.world.World;
import xueli.gamengine.utils.vector.Vector;
import xueli.utils.Bytes;
import xueli.utils.Logger;

public class Server extends WebSocketServer {

	public static final int SERVER_PORT = 8888;

	private boolean isStarted = false;
	private Exception exception;

	private WorldLogic logic;
	private World world;

	private HashMap<Integer, String> clientIdNamesHashMap = new HashMap<Integer, String>();
	private int idCount = 0;

	public Server(WorldLogic logic) {
		super(new InetSocketAddress(SERVER_PORT));
		// 设置能被服用，防止在linux系统下套接字状态TIME_WAIT在套接字关闭后保留2-4分钟，使程序不能再次使用这个端口
		setReuseAddr(true);

		this.logic = logic;
		this.world = logic.getWorld();

	}

	@Override
	public void onOpen(WebSocket conn, ClientHandshake handshake) {

	}

	@Override
	public void onClose(WebSocket conn, int code, String reason, boolean remote) {
		if (conn == null) {
			Logger.info("[Server] Server closed: " + code + ", " + reason + ", isRemote: " + remote);

		}

	}

	@Override
	public void onMessage(WebSocket conn, String message) {
		Message m = Message.getMessage(message.getBytes(HandshakeMessages.STANDARD_CHARSET));
		switch (m.getId()) {
		case HandshakeMessages.PLAYER_CONNECT: {
			if (!clientIdNamesHashMap.containsValue(m.getMessage())) {
				int id = idCount;
				Logger.info("[Server] " + m.getMessage() + " join the game: " + id);

				clientIdNamesHashMap.put(id, m.getMessage());
				conn.send(Message.generateMessage(new Message(HandshakeMessages.PLAYER_CONNECT, Integer.toString(id))));

			} else {
				Logger.warn("[Server] The client named " + m.getMessage()
						+ " sent another PLAYER_CONNECT message! Ignore.");

			}

			break;
		}
		case HandshakeMessages.PREPARE_SPAWN_POINT: {
			int clientID = Integer.parseInt(m.getMessage());
			String nameString = clientIdNamesHashMap.get(clientID);
			if (nameString == null) {
				Logger.warn("[Server] A client sent an ID that doesn't exist in id_name map. Ignore: "
						+ conn.getRemoteSocketAddress());
				break;
			}
			// TODO: 根据玩家的位置生成地图
			Vector playerPos = world.getPlayer(clientIdNamesHashMap.get(clientID)).getPlayerPos();
			world.generateChunkAccordingToPlayerPos(playerPos);

			// TODO: 如果是单纯的服务端，此时就需要给client发地图了
			if (logic == null) {

			}

			conn.send(Message.generateMessage(new Message(HandshakeMessages.PREPARE_SPAWN_POINT_OK, null)));
			break;
		}
		case HandshakeMessages.HANDSHAKE_CLIENT_REQUEST_PLAYER_POSITION: {
			int clientId = Integer.parseInt(m.getMessage());
			String name = clientIdNamesHashMap.get(clientId);
			if (name == null) {
				Logger.warn("[Server] A client sent an ID that doesn't exist in id_name map. Ignore: "
						+ conn.getRemoteSocketAddress());
				break;
			}

			Vector playerPos = world.getPlayer(name).getPlayerPos();
			byte[] playerPosData = Bytes.getBytes(playerPos);
			// 如果直接以字符串的形式发送就会出现invalid stream header: EFBFBDEF 所以转换为base64先
			String dataBase64 = Base64.getEncoder().encodeToString(playerPosData);
			conn.send(Message.generateMessage(
					new Message(HandshakeMessages.HANDSHAKE_SERVER_ANSWER_PLAYER_POSITION, dataBase64)));

			break;
		}
		case HandshakeMessages.CLIENT_ENTER_GAMEPLAY: {
			int clientId = Integer.parseInt(m.getMessage());
			String name = clientIdNamesHashMap.get(clientId);
			if (name == null) {
				Logger.warn("[Server] A client sent an ID that doesn't exist in id_name map. Ignore: "
						+ conn.getRemoteSocketAddress());
				break;
			}

			world.getPlayer(name).setState(PlayerState.ALIVE);

			break;
		}
		case HandshakeMessages.EVENT: {
			Event event = Message.getEvent(message.getBytes(HandshakeMessages.STANDARD_CHARSET));
			event.invokeServer(this);

			this.broadcast(message);

			break;

		}

		}

	}

	@Override
	public void onError(WebSocket conn, Exception ex) {
		if (conn != null) {
			Logger.error("[Server] Exception from " + conn.getRemoteSocketAddress() + ": ");
		} else if (isStarted) {
			Logger.error("[Server] Exception: ");
		} else {
			Logger.error("[Server] Fatal Exception: ");
		}

		ex.printStackTrace();

		exception = ex;

	}

	private long lastTimePosCorrect = System.currentTimeMillis();

	public void onTick() {
		if (System.currentTimeMillis() - lastTimePosCorrect > 5000) {
			EventServerCorrectPlayerPos event = new EventServerCorrectPlayerPos(world.getPlayers());
			this.broadcast(Message.generateEventMessage(event));

			lastTimePosCorrect = System.currentTimeMillis();
		}

	}

	@Override
	public void onStart() {
		Logger.info("[Server] Server started~");
		isStarted = true;

	}

	public boolean isStarted() {
		return isStarted;
	}

	/**
	 * 在单人游戏，服务器启动时，如果抛出异常会直接在onError方法里面传出 所以在这里获取这个异常
	 */
	public Exception getException() {
		return exception;
	}

	public HashMap<Integer, String> getClientIdNamesHashMap() {
		return clientIdNamesHashMap;
	}

	public World getWorld() {
		return world;
	}

	public static void main(String[] args) {

	}

}
