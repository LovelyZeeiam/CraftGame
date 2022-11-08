package xueli.utils.download;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.concurrent.ExecutorService;

public abstract class DownloadInstance extends DeterminedListWorker<RangeConnection> implements DownloadStateListener {

	protected final URL url;
	protected final File output;
	protected final Map<String, String> headers;

	protected long fileSize = 0;

	DownloadInstance(URL url, File output, Map<String, String> headers, ExecutorService executor) {
		super(executor);
		this.url = url;
		this.output = output;
		this.headers = headers;

	}

	public static DownloadInstance create(URL url, File output, Map<String, String> headers, ExecutorService executor) throws IOException {
		return create(url, output, headers, Integer.MAX_VALUE, executor);
	}

	/**
	 * Create a <code>DownloadInstance</code> instance that is ready to run. It will check whether the
	 * link support <code>Range</code> parameter, get the length of content and make relative decisions,
	 * which is called initializing.
	 *
	 * @param url       The downloading link
	 * @param output    The output file
	 * @param headers   The headers that is necessary to make the connection
	 * @param chunkSize The chunk size of multithreaded downloading. When the value is not bigger than
	 *                  zero, the method won't consider the multithreaded downloading but returns to
	 *                  the RangeDownloadInstance.
	 * @param executor  The executor that runs multithreaded downloading task
	 * @throws IOException throw when an exception on initializing is thrown
	 */
	public static DownloadInstance create(URL url, File output, Map<String, String> headers, long chunkSize, ExecutorService executor) throws IOException {
		output.createNewFile();

		String proto = url.getProtocol().toLowerCase();
		URLConnection con = createConnection(url, headers);

		boolean supportRange = false;
		if ("http".equals(proto) || "https".equals(proto)) {
			// Open the connection to check whether Range is supported
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setRequestProperty("Range", "bytes=0-");
			int responseCode = httpCon.getResponseCode();

			switch (responseCode) {
				case HttpURLConnection.HTTP_OK:
					break;
				case HttpURLConnection.HTTP_PARTIAL:
					supportRange = true;
					break;
				case HttpURLConnection.HTTP_MOVED_TEMP:
				case HttpURLConnection.HTTP_MOVED_PERM:
				case HttpURLConnection.HTTP_SEE_OTHER:
					String newLocation = httpCon.getHeaderField("Location");
					URL newUrl = new URL(newLocation);
					return create(newUrl, output, headers, chunkSize, executor);
				default:
					throw new IOException(String.format("Get %d when fetch url: %s%n", responseCode, url));
			}
		}
		long fileSize = con.getContentLengthLong();

		DownloadInstance instance;
		// To support some occasions when a negative file size is sent back. Often this will happen when trying to access a remote website.
		if (fileSize > 0) {
			instance = supportRange ?
					(chunkSize > 0 ? new MultiThreadedDownloadInstance(url, output, headers, chunkSize, executor) : new RangeDownloadInstance(url, output, headers, executor))
					: new SingleThreadDownloadInstance(url, output, headers, executor);
		} else {
			instance = new SingleThreadDownloadInstance(url, output, headers, executor);
		}

		instance.fileSize = fileSize;
		instance.init(con);

		return instance;
	}

	public static URLConnection createConnection(URL url, Map<String, String> headers) throws IOException {
		URLConnection con = url.openConnection();
		con.setConnectTimeout(5000);
		con.setReadTimeout(3000);

		if (con instanceof HttpURLConnection && headers != null) {
			headers.forEach(con::setRequestProperty);
		}

		return con;
	}

	/**
	 * submit the initial connection to avoid spending time on connection
	 */
	protected void init(URLConnection initialConnection) {
	}

	protected void submitDownloadTask(Range range) {
		try {
			this.submit(RangeConnection.makeConnection(range.getFrom(), range.getTo(), url, headers));
		} catch (IOException e) {
			this.processDownloadFail(range, null, 0, e);
		}
	}

	protected String genRandomString() {
		return "." + RandomUtils.genRandomStr(20);
	}

	@Override
	protected void runTask(RangeConnection rangeCon) throws Exception {
		long downloadSize = rangeCon.getTo() - rangeCon.getFrom() + 1;
		long byteCount = 0;

		InputStream in = null;
		OutputStream out = null;

		String randomStr = genRandomString();
		File file = new File(output.getAbsolutePath() + (randomStr == null ? "" : randomStr));

		byte[] ioBuffer = new byte[4096];

		try {
			in = rangeCon.getConnection().getInputStream();
			out = new BufferedOutputStream(new FileOutputStream(file));

			int read = 0;
			while (read != -1) {
				long remaining = downloadSize - byteCount;

				// To ensure read count is an "integer"
				// And also make sure that the initial connection won't read more
				int thisTimeReadCount = (int) Math.min(ioBuffer.length, Math.min(remaining, Integer.MAX_VALUE));
				if (thisTimeReadCount <= 0)
					break;

				read = in.read(ioBuffer, 0, thisTimeReadCount);

				if (read > 0) {
					out.write(ioBuffer, 0, read);
					byteCount += read;
				}

			}

			this.processDownloadSuccess(rangeCon, file);

		} catch (IOException e) {
			this.processDownloadFail(rangeCon, file, byteCount, e);
		} finally {
			if (in != null)
				in.close();
			if (out != null) {
				out.flush();
				out.close();
			}
		}

	}

}
