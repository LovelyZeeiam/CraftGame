package xueli.bilibili.liverecorder;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.Timer;
import java.util.logging.Logger;

import net.dengzixu.java.message.Message;
import net.dengzixu.java.websocket.BilibiliLiveWebSocketManager;
import net.dengzixu.java.websocket.LiveMessageCallback;
import xueli.bilibili.liverecorder.data.ResponseBilibiliAPI;
import xueli.bilibili.liverecorder.data.ResponseRoomInfo;
import xueli.utils.properties.PropertiesReflection;
import xueli.utils.properties.Property;

public class LiveRecorder implements Runnable, LiveMessageCallback {

	private static final Logger logger = Logger.getLogger(LiveRecorder.class.getName());

	@Property("liverecorder.uuid")
	long uuid = 0;
	@Property("liverecorder.workingspace")
	String workingDirectory;
	@Property("liverecorder.sessdata")
	String sessData;
	private long liveid = 0;

	boolean running = true;
	StreamingThread streamingThread;
	BilibiliLiveWebSocketManager danmakuManager;
	private BufferedOutputStream danmakuWriter;
	private Timer timer = new Timer();

	public LiveRecorder() throws Exception {
		readProperties();
		init();

		streamingThread = new StreamingThread(this);
		danmakuManager = BilibiliLiveWebSocketManager.getInstance(liveid);

		danmakuManager.init();
		danmakuManager.setCallback(this);

	}

	private void readProperties() {
		try {
			PropertiesReflection.reflect(this, new File("liverecorder/liverecorder.properties"));
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}

		this.workingDirectory += "/";

	}

	private void init() throws Exception {
		ResponseRoomInfo info = ResponseBilibiliAPI.fetchInfo(uuid, sessData);
		if (info.data.code != 0) {
			throw new Exception("Fetch room status exception: " + info.data.code + ", " + info.data.message);
		}
		if (info.data.roomStatus == 0) {
			throw new Exception("Can't find room: " + uuid);
		}

		this.liveid = info.data.roomid;
		logger.info("Fetch properties OK, start on listening at live id " + liveid);

	}

	public long getLiveid() {
		return liveid;
	}

	@Override
	public void run() {
		running = true;

		timer.schedule(new LiveRecorderController(this), 0, 5000);

		// streamingThread.start();
		danmakuManager.connect();

		try {
			danmakuWriter = new BufferedOutputStream(new FileOutputStream(
					workingDirectory + StreamingThread.getStreamSaveName(liveid) + "_danmaku.csv"));
			danmakuWriter.write(
					("Time,Header,Username,UUID,Message" + System.lineSeparator()).getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {
			e.printStackTrace();
			danmakuWriter = null;
		}

		Scanner scanner = new Scanner(System.in);
		a: while (true) {
			String c = scanner.nextLine();
			switch (c) {
			case "q":
				break a;
			default:
				break;
			}
		}

		scanner.close();

		if (danmakuWriter != null)
			try {
				danmakuWriter.flush();
				danmakuWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		timer.cancel();
		this.running = false;
		danmakuManager.close();

		System.exit(0);

	}

	@Override
	public void onMessage(Message m) throws Exception {
		String logHead = null, logBody = null;

		switch (m.bodyCommandEnum) {
		case DANMU_MSG:
			logHead = "Danmaku";
			logBody = "\"" + m.userInfo.getUsername() + "\"," + m.userInfo.getUid() + ",\"" + m.content.get("danmu")
					+ "\"";
			break;
		case SEND_GIFT:
			logHead = "Gift";
			logBody = "\"" + m.userInfo.getUsername() + "\"," + m.userInfo.getUid() + ",\"" + m.content.get("gift_name")
					+ "*" + m.content.get("num") + "\"";
			break;
		case INTERACT_WORD:
			logHead = "Interact";
			logBody = "\"" + m.userInfo.getUsername() + "\"," + m.userInfo.getUid();
			break;
		case LIVE_START:
			logHead = "LiveStart";
			logBody = "";
			break;
		default:
			break;
		}

		if (logHead != null && logBody != null) {
			System.out.println("[Danmaku] " + logHead + ": " + logBody);
			if (danmakuWriter != null)
				danmakuWriter.write(((streamingThread.getStartTime() == 0 ? 0 : (System.currentTimeMillis() - streamingThread.getStartTime())) + "," + logHead + ","
						+ logBody + System.lineSeparator()).getBytes(StandardCharsets.UTF_8));

		}

	}

}
