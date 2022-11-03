package xueli.utils.download;

import java.io.File;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.concurrent.ExecutorService;

class SingleThreadDownloadInstance extends DownloadInstance {

	SingleThreadDownloadInstance(URL url, File output, Map<String, String> headers, ExecutorService executor) {
		super(url, output, headers, executor);
	}

	@Override
	protected void init(URLConnection initialConnection) {
		this.submit(new RangeConnection(0, fileSize - 1, initialConnection));
	}

	@Override
	protected String genRandomString() {
		return null;
	}

	@Override
	public void processDownloadFail(Range range, File file, long downloadedSize, Exception e) {
		this.submitDownloadTask(range);
		file.delete();
	}

	@Override
	public void processDownloadSuccess(Range range, File file) {
	}

}
