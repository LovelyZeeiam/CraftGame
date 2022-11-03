package xueli.utils.download;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

class RangeConnection extends Range {

	private final URLConnection connection;

	public RangeConnection(long start, long to, URLConnection connection) {
		super(start, to);
		this.connection = connection;
	}

	public static RangeConnection makeConnection(long start, long to, URL url, Map<String, String> headers) throws IOException {
		URLConnection con = DownloadInstance.createConnection(url, headers);
		con.setRequestProperty("Range", String.format("bytes=%d-%d", start, to));
		return new RangeConnection(start, to, con);
	}

	public URLConnection getConnection() {
		return connection;
	}

	@Override
	public String toString() {
		return "RangeConnection{" +
				"connection=" + connection +
				", start=" + getFrom() +
				", to=" + getTo() +
				'}';
	}

}
