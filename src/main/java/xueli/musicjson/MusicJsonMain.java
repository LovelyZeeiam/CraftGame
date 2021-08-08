package xueli.musicjson;

import xueli.utils.io.Files;

import java.io.File;
import java.io.IOException;

public class MusicJsonMain {

	public static void main(String[] args) throws IOException {
		String data = Files.readAllString(new File("res/music/aurora.json"));
		MusicJson mj = new MusicJson(data);
		mj.play();
		mj.release();

	}

}
