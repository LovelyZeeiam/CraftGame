package xueli.game2.resource;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ResourceURL implements Resource {

	private URL url;

	public ResourceURL(URL url) {
		this.url = url;
	}

	@Override
	public InputStream openInputStream() throws IOException {
		InputStream in = url.openStream();
		return new BufferedInputStream(in);
	}

	@Override
	public String toString() {
		return "ResourceURL{" + url + '}';
	}

}
