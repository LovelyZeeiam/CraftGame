package xueli.gamengine.musicjson;

import java.io.IOException;

import xueli.gamengine.utils.IOUtils;

public class MusicJsonMain {

	public static void main(String[] args) throws IOException {
		String data = IOUtils.readFully("res/music/One Summer Day.json");
		MusicJson mj = new MusicJson(data);
		mj.play();
		mj.release();

	}

}
