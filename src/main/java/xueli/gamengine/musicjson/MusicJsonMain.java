package xueli.gamengine.musicjson;

import xueli.gamengine.utils.IOUtils;

import java.io.IOException;

public class MusicJsonMain {

	public static void main(String[] args) throws IOException {
		String data = IOUtils.readFully("res/music/thief.json");
		MusicJson mj = new MusicJson(data);
		mj.play();
		mj.release();

	}

}
