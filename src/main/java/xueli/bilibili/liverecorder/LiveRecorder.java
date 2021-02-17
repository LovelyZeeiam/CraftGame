package xueli.bilibili.liverecorder;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import xueli.utils.Files;
import xueli.utils.Logger;

@Deprecated
public class LiveRecorder {

	private static Gson gson = new Gson();

	public static void main(String[] args) throws Exception {
		// 获取房间号
		int roomID = Integer.parseInt(Files.readAllString(new File("res/text/roomid.txt")));

		// 获取弹幕服务器和token
		String danmakuServer = null;
		String[] token = new String[1];

		{
			URL url = new URL(
					"https://api.live.bilibili.com/xlive/web-room/v1/index/getDanmuInfo?type=0&;id=" + roomID);
			InputStream in = url.openStream();
			byte[] data = new byte[in.available()];
			in.read(data);
			in.close();

			JsonObject object = gson.fromJson(new String(data), JsonObject.class);
			int code = object.get("code").getAsInt();
			String message = object.get("message").getAsString();

			if (code == 0)
				Logger.info("GetDanmakuInfo: " + code + ", " + message);
			else
				Logger.error(new Exception("GetDanmakuInfo OK: " + message));

			JsonObject dataObject = object.getAsJsonObject("data");
			token[0] = dataObject.get("token").getAsString();

			JsonArray hostList = dataObject.getAsJsonArray("host_list");
			JsonObject hostFirstList = hostList.get(1).getAsJsonObject();

			danmakuServer = "wss://" + hostFirstList.get("host").getAsString() + "/sub";

		}

		Logger.info("GetDanmakuInfo: token: " + token[0] + ", server: " + danmakuServer);

		// 连接弹幕服务器
		HashMap<String, String> danmakuServerHeaderHashMap = new HashMap<String, String>();
		danmakuServerHeaderHashMap.put("User-Agent",
				"Mozilla/5.0 (X11; Linux x86_64; rv:84.0) Gecko/20100101 Firefox/84.0");
		danmakuServerHeaderHashMap.put("Accept", "*/*");
		danmakuServerHeaderHashMap.put("Accept-Language",
				"zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
		danmakuServerHeaderHashMap.put("Origin", "https://live.bilibili.com");
		danmakuServerHeaderHashMap.put("Pragma", "no-cache");
		danmakuServerHeaderHashMap.put("CacheControl", "no-cache");

		WebSocketClient danmakuServerClient = new WebSocketClient(new URI(danmakuServer), danmakuServerHeaderHashMap) {
			@Override
			public void onOpen(ServerHandshake handshakedata) {

			}

			@Override
			public void onMessage(String message) {
				synchronized (this) {
					System.out.println(233);

				}

			}

			@Override
			public void onError(Exception ex) {
				ex.printStackTrace();
			}

			@Override
			public void onClose(int code, String reason, boolean remote) {
				Logger.info("DanmakuServer: Closed: " + code + ", " + reason);

			}
		};

		danmakuServerClient.connectBlocking();

		Logger.info("DanmakuServer: Connected~");

		danmakuServerClient.send(getDanmakuServerSend(7,
				"{\r\n" + "    \"uid\": 0,\r\n" + "    \"roomid\": " + roomID + ",\r\n" + "    \"protover\": 2,\r\n"
						+ "    \"platform\": \"web\",\r\n" + "    \"clientver\": \"2.6.25\",\r\n"
						+ "    \"type\": 2,\r\n" + "    \"key\": \"" + token[0] + "\"\r\n" + "}"));
		danmakuServerClient.send(getDanmakuServerSend(2, "[object Object]"));

		while (true) {

		}

	}

	private static byte[] getDanmakuServerSend(int type, String data) {
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			DataOutputStream oDataOutputStream = new DataOutputStream(outputStream);

			oDataOutputStream.writeInt(data.length() + 16);
			oDataOutputStream.writeShort((short) 16);
			oDataOutputStream.writeShort((short) 1);
			oDataOutputStream.writeInt(type);
			oDataOutputStream.writeInt(1);

			oDataOutputStream.write(data.getBytes());
			oDataOutputStream.flush();

			return outputStream.toByteArray();

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

}
