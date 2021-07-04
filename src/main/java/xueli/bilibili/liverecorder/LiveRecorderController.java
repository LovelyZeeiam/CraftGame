package xueli.bilibili.liverecorder;

import java.io.IOException;
import java.util.TimerTask;
import java.util.logging.Logger;

import com.google.gson.JsonSyntaxException;

import xueli.bilibili.liverecorder.data.ResponseBilibiliAPI;
import xueli.bilibili.liverecorder.data.ResponseRoomInfo;

public class LiveRecorderController extends TimerTask {

	private static final Logger logger = Logger.getLogger(LiveRecorderController.class.getName());

	private LiveRecorder r;

	public LiveRecorderController(LiveRecorder r) {
		this.r = r;

	}

	@Override
	public void run() {
		try {
			logger.fine("Scheduled controller start~");
			ResponseRoomInfo info = ResponseBilibiliAPI.fetchInfo(r.uuid, r.sessData);

			if (info.data.code != 0) {
				logger.severe(
						"Fetch room status exception, try again later: " + info.data.code + ", " + info.data.message);
				return;
			}
			if (info.data.roomStatus == 0) {
				logger.severe("Can't find room, try again later: " + r.uuid);
				return;
			}

			if ((info.data.roundStatus == 0 && info.data.liveStatus == 1) && !r.streamingThread.isAlive()) {
				r.streamingThread.start();
				logger.info("Start streaming thread~");
			}

		} catch (JsonSyntaxException | IOException e) {
			e.printStackTrace();
		}
	}

}
