package xueli.gamengine.musicjson;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import xueli.gamengine.utils.SoundManager;

public class NoteType {

	static {
		File file = new File("res/sounds/note/");
		Arrays.stream(file.listFiles()).forEach((f) -> {
			String filename = f.getName();
			String name = filename.substring(0, filename.lastIndexOf('.'));
			try {
				SoundManager.loadWav(name, f.getCanonicalPath());

			} catch (IOException e) {
				e.printStackTrace();
			}

		});

	}

	public static void playSound(String name, float rate) {
		SoundManager.play(name, 1.0f, rate);

	}

	public static void release() {
		SoundManager.release();

	}

}
