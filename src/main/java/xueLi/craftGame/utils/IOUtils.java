package xueLi.craftGame.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class IOUtils {

	public static byte[] readAllFromFile(String path) throws IOException {
		FileInputStream s = new FileInputStream(path);
		byte[] b = new byte[s.available()];
		s.read(b);
		s.close();
		return b;
	}

	public static String readAllToString(String path) throws IOException {
		byte[] b = readAllFromFile(path);
		return new String(b);
	}

	public static ByteBuffer getByteBufferFromFile(String path) throws IOException {
		byte[] b = readAllFromFile(path);
		ByteBuffer buffer = ByteBuffer.wrap(b);
		buffer.flip();
		return buffer;
	}

}
