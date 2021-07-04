package xueli.bilibili.liverecorder;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.sql.Time;
import java.util.logging.Logger;

import org.bytedeco.ffmpeg.avcodec.AVPacket;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;

import com.google.gson.Gson;

import xueli.bilibili.liverecorder.data.ResponseStreamingRealURL;
import xueli.utils.io.Webs;

public class StreamingThread extends Thread {

	private static final Gson gson = new Gson();
	private static final Logger logger = Logger.getLogger(StreamingThread.class.getName());

	private LiveRecorder recorder;
	private long liveid;
	private long startTime = 0;

	public StreamingThread(LiveRecorder recorder) {
		super("LiveStreamThread");
		this.recorder = recorder;

		this.liveid = this.recorder.getLiveid();

	}

	private String getStreamURL() throws IOException {
		String getterURL = "https://youknow.top/zhibo/get_real_url?type=bilibili&url=" + liveid;
		String readAll = null;
		try {
			readAll = Webs.readAllFromWebString(getterURL);
		} catch (SocketTimeoutException e) {
			throw new SocketTimeoutException("read timed out at liveid " + liveid + "! Has the live stream ended? :(");
		}
		ResponseStreamingRealURL.Response r = gson.fromJson(readAll, ResponseStreamingRealURL.Response.class);
		return r.url.FlvUrl;
	}

	public static String getStreamSaveName(long liveid) {
		return "Stream_" + liveid + "_" + System.currentTimeMillis();
	}

	@Override
	public void run() {
		try {
			String streamURL = getStreamURL();
			String streamSaveName = this.recorder.workingDirectory + getStreamSaveName(liveid) + ".flv";

			logger.info("Get stream url: " + streamURL);

			FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(streamURL);
			grabber.setOption("stimeout", String.valueOf(10 * 1000000));
			grabber.start();

			System.out.println("[Stream] Video: " + grabber.getImageWidth() + "x" + grabber.getImageHeight()
					+ " Audio: " + (grabber.getAudioBitrate() / 1024) + "kB/s x" + grabber.getAudioChannels());

			FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(streamSaveName, grabber.getImageWidth(),
					grabber.getImageHeight(), grabber.getAudioChannels());
			recorder.setVideoCodec(grabber.getVideoCodec());
			recorder.setAudioCodec(grabber.getAudioCodec());
			recorder.start(grabber.getFormatContext());

			AVPacket packet = null;

			long frameCount = 0;
			long lastTimeDebugOut = 0;
			this.startTime = System.currentTimeMillis();

			while (this.recorder.running && (packet = grabber.grabPacket()) != null) {
				frameCount++;

				recorder.recordPacket(packet);

				if (System.currentTimeMillis() - lastTimeDebugOut >= 1000) {
					Time time = new Time(System.currentTimeMillis() - startTime + 16 * 60 * 60 * 1000);
					System.out.println("[Grabber] \t" + frameCount + (frameCount > 1 ? " packets" : " packet") + "\t\t"
							+ grabber.getVideoFrameRate() + " FPS\t\t" + time);

					lastTimeDebugOut = System.currentTimeMillis();

				}

			}

			grabber.close();
			recorder.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public long getStartTime() {
		return startTime;
	}

}
