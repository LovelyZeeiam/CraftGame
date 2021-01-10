package xueli.gamengine.musicjson;

import java.io.IOException;

import xueli.gamengine.utils.IOUtils;

public class MusicJsonMain {

	/**
	 * current in test
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		String data = IOUtils.readFully("res/music/fresh_static_snow.json");
		MusicJson mj = new MusicJson(data);
		mj.play();
		mj.release();

	}

}
