package xueli.utils.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Files {

	public static void fileOutput(String name, String content) throws IOException {
		fileOutput(name, content.getBytes(StandardCharsets.UTF_8));

	}

	public static void fileOutput(String name, byte[] bytes) throws IOException {
		File file = new File(name);
		if (!file.exists())
			file.createNewFile();

		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
		out.write(bytes);
		out.flush();
		out.close();
	}

	public static void mkDir(String path) {
		new File(path).mkdirs();

	}

	public static String readAllString(File file) throws IOException {
		BufferedInputStream reader = new BufferedInputStream(new FileInputStream(file));
		byte[] all = new byte[reader.available()];
		reader.read(all);
		reader.close();
		return new String(all);
	}

}
