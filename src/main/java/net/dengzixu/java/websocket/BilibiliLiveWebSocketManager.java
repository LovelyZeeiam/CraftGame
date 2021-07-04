package net.dengzixu.java.websocket;

import java.io.EOFException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.dengzixu.java.constant.Constant;
import net.dengzixu.java.constant.PacketOperationEnum;
import net.dengzixu.java.constant.PacketProtocolVersionEnum;
import net.dengzixu.java.message.Message;
import net.dengzixu.java.packet.Packet;
import net.dengzixu.java.packet.PacketBuilder;
import net.dengzixu.java.packet.PacketResolve;
import net.dengzixu.java.payload.AuthPayload;
import net.dengzixu.java.payload.PayloadResolver;
import net.dengzixu.java.third.api.GetAuthToken;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class BilibiliLiveWebSocketManager {

	private static final Logger logger = Logger.getLogger(BilibiliLiveWebSocketManager.class.getName());

	private static BilibiliLiveWebSocketManager webSocketManager = null;

	private OkHttpClient okHttpClient;
	private Request request;
	private WebSocket webSocket;

	private Timer heartbeatTimer;

	private boolean connect;

	private final long roomId;

	private LiveMessageCallback callback;

	private BilibiliLiveWebSocketManager(long roomId) {
		this.roomId = roomId;
	}

	/**
	 * getInstance
	 *
	 * @param roomId 房间ID
	 * @return WebSocketManager
	 */
	public static BilibiliLiveWebSocketManager getInstance(long roomId) {
		if (null == webSocketManager) {
			synchronized (BilibiliLiveWebSocketManager.class) {
				if (null == webSocketManager) {
					webSocketManager = new BilibiliLiveWebSocketManager(roomId);
				}
			}
		}
		return webSocketManager;
	}

	/**
	 * 初始化
	 */
	public void init() {
		okHttpClient = new OkHttpClient.Builder().build();

		request = new Request.Builder().url(Constant.BILIBILI_LIVE_WS_URL).build();
	}

	/**
	 * 建立链接
	 */
	public void connect() {
		webSocket = okHttpClient.newWebSocket(request, createWebSocketListener());
	}

	public boolean sendMessage(String data) {
		return webSocket.send(data);
	}

	public boolean sendMessage(ByteString data) {
		return webSocket.send(data);
	}

	public void setCallback(LiveMessageCallback callback) {
		this.callback = callback;
	}

	public void close() {
		webSocket.close(1001, "");

		synchronized (Thread.currentThread()) {
			try {
				Thread.currentThread().wait(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		webSocket.cancel();

	}

	/**
	 * 开始发送心跳
	 */
	private void startHeartbeat() {
		if (null == heartbeatTimer) {
			heartbeatTimer = new Timer();
		}

		heartbeatTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				byte[] bytes = new PacketBuilder(PacketProtocolVersionEnum.PROTOCOL_VERSION_1,
						PacketOperationEnum.OPERATION_2, "[object Object]").buildArrays();
				webSocket.send(new ByteString(bytes));
			}
		}, 0, 1000 * 30);
	}

	/**
	 * 停止发送心跳
	 */
	private void stopHeartbeat() {
		if (null != heartbeatTimer) {
			heartbeatTimer.cancel();
		}
	}

	public boolean isConnect() {
		return connect;
	}

	private WebSocketListener createWebSocketListener() {
		return new WebSocketListener() {

			@Override
			public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
				stopHeartbeat();
				logger.info("Socket Closed.");
				super.onClosed(webSocket, code, reason);
			}

			@Override
			public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
				connect = false;
				logger.info("Socket Closing.");
				super.onClosing(webSocket, code, reason);
			}

			@Override
			public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
				connect = false;

				if (!(t instanceof EOFException)) {
					logger.severe("Socket Failure: " + t.getClass() + ": " + t.getMessage());
					t.printStackTrace();
				}

				stopHeartbeat();
				close();
				super.onFailure(webSocket, t, response);
			}

			@Override
			public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
				System.out.println("onMessage Bytes:" + text);
				super.onMessage(webSocket, text);
			}

			@Override
			public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {
				List<Packet> packets = new PacketResolve(bytes.toByteArray()).getPacketList();

				if (packets.size() > 0) {
					for (Packet packet : packets) {
						Message message = new PayloadResolver(packet.getPayload(),
								PacketOperationEnum.getEnum(packet.getOperation())).resolve();

						switch (message.getBodyCommand()) {
						case DANMU_MSG:
						case INTERACT_WORD:
						case SEND_GIFT:
							if (callback != null)
								try {
									callback.onMessage(message);
								} catch (Exception e) {
									e.printStackTrace();
								}
							break;
						case LIVE_START:
							logger.info("Live Start!");
							break;
						case AUTH_SUCCESS:
							logger.info("Certificated successfully~");
							connect = true;
							startHeartbeat();
							break;
						case UNKNOWN:
							break;
						default:
						}
					}
				}

				super.onMessage(webSocket, bytes);
			}

			@Override
			public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
				logger.info("Start Connecting to danmaku server...");

				// 构建认证 payload
				AuthPayload authPayload = new AuthPayload();
				authPayload.setRoomid(roomId);
				authPayload.setKey(GetAuthToken.get(roomId));

				String payloadString = null;

				try {
					payloadString = new ObjectMapper().writeValueAsString(authPayload);
				} catch (JsonProcessingException ignored) {
				}

				if (null != payloadString) {
					byte[] packetArray = new PacketBuilder(PacketProtocolVersionEnum.PROTOCOL_VERSION_1,
							PacketOperationEnum.OPERATION_7, payloadString).buildArrays();

					webSocket.send(new ByteString(packetArray));
				}

				super.onOpen(webSocket, response);
			}
		};
	}
}
