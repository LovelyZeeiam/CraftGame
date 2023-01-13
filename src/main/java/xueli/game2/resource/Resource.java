package xueli.game2.resource;

import java.io.IOException;
import java.io.InputStream;

public interface Resource {

	public String getName();
	public InputStream openInputStream() throws IOException;
	
	default byte[] readAll() throws IOException {
		InputStream in = openInputStream();
		byte[] all = in.readAllBytes();
		in.close();
		return all;
	}
	
	default String readAllString() throws IOException {
		return new String(readAll());
	}
	
}
