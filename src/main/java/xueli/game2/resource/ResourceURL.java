package xueli.game2.resource;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ResourceURL implements Resource {

	private URL url;
	private String name;

	public ResourceURL(URL url) {
		this.url = url;
		compileName();
	}

	private void compileName() {
		String path = url.getPath();
		if (path.endsWith("/")) {
			path = path.substring(0, path.length() - 1);
		}
		int index = path.lastIndexOf('/');
		this.name = path.substring(Math.max(0, index + 1));

	}

	@Override
	public InputStream openInputStream() throws IOException {
		InputStream in = url.openStream();
		return new BufferedInputStream(in);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "ResourceURL{" + url + '}';
	}

}
