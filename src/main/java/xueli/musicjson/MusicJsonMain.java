package xueli.musicjson;

import java.io.File;
import java.io.IOException;

import xueli.utils.io.Files;

public class MusicJsonMain {

	public static void main(String[] args) throws IOException {
		String data = Files.readAllString(new File("res/music/beings.json"));
		MusicJson mj = new MusicJson(data);
		mj.play();
		mj.release();

	}

}
