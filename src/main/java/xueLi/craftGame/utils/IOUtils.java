package xueLi.craftGame.utils;

import java.io.FileInputStream;
import java.io.IOException;

public class IOUtils {

	public static String readAllToString(String path) throws IOException {
		FileInputStream s = new FileInputStream(path);
		byte[] b = new byte[s.available()];
		s.read(b);
		s.close();
		return new String(b);
	}

}
