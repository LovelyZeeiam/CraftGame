package xueli.game2.resource;

import java.io.IOException;
import java.io.InputStream;

public class ResourceHelper {

	public static byte[] readAll(Resource resource) throws IOException {
		InputStream in = resource.openInputStream();
		byte[] bytes = in.readAllBytes();
		in.close();
		return bytes;
	}
	
	public static String readAllToString(Resource resource) throws IOException {
		byte[] bytes = readAll(resource);
		return new String(bytes);
	}

}
