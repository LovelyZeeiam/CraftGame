package xueli.musicjson.nbs;

import java.io.File;
import java.util.ArrayList;

public class NBSChooser {

	public static ArrayList<NoteBlock> choose(String path) {
		File[] file = new File(path).listFiles();
		if (file.length == 0)
			return null;

		System.out.println("There are so many music to choose: ");

		for (int i = 0; i < file.length; i++) {
			File f = file[i];
			if (!f.isFile())
				continue;

			System.out.println("[" + i + "] " + f.getName());

		}

		return null;
	}

}
