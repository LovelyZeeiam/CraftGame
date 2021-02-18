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
import xueli.gamengine.utils.vector.Vector;
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
		if(conn == null) {
			Logger.info("[Server] Server closed: " + code + ", " + reason + ", isRemote: " + remote);
			
		}
		
	}

	@Override
	public void onMessage(WebSocket conn, String message) {
		Message m = Message.getMessage(message.getBytes(MessageDefine.STANDARD_CHARSET));
		switch (m.getId()) {
		case MessageDefine.PLAYER_CONNECT:
			if(!clientIdNamesHashMap.containsValue(m.getMessage())) {
				int id = idCount;
				Logger.info("[Server] " + m.getMessage() + " join the game: " + id);
				
				clientIdNamesHashMap.put(id, m.getMessage());
				conn.send(Message.generateMessage(new Message(MessageDefine.PLAYER_CONNECT, Integer.toString(id))));
				
			} else {
				Logger.warn("[Server] The client named " + m.getMessage() + " sent another PLAYER_CONNECT message! Ignore.");
				
			}
			
			break;
		case MessageDefine.PREPARE_SPAWN_POINT:
			int clientID = Integer.parseInt(m.getMessage());
			String nameString = clientIdNamesHashMap.get(clientID);
			if(nameString == null) {
				Logger.warn("[Server] A client sent an ID that doesn't exist in id_name map. Ignore.");
			}
			// TODO: 根据玩家的位置生成地图
			Vector playerPos = world.getPlayer(clientIdNamesHashMap.get(clientID));
			world.generateChunkAccordingToPlayerPos(playerPos);
			
			// TODO: 如果是单纯的服务端，此时就需要给client发地图了
			if(logic == null) {
				
				
			}
			
			conn.send(Message.generateMessage(new Message(MessageDefine.PREPARE_SPAWN_POINT_OK, null)));
			
			break;

		}

	}

	@Override
	public void onError(WebSocket conn, Exception ex) {
		if(conn != null) {
			Logger.error("[Server] Exception from " + conn.getRemoteSocketAddress() + ": ");
		} else if(isStarted) {
			Logger.error("[Server] Exception: ");
		} else {
			Logger.error("[Server] Fatal Exception: ");
		}
		
		ex.printStackTrace();
		
		exception = ex;

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
	
	public static void main(String[] args) {

	}

}
