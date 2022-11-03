package xueli.utils.download;

import java.io.File;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.concurrent.ExecutorService;

class MultiThreadedDownloadInstance extends RangeDownloadInstance {

	private final DynamicChunkSize chunkSize;

	MultiThreadedDownloadInstance(URL url, File output, Map<String, String> headers, long initialChunkSize, ExecutorService executor) {
		super(url, output, headers, executor);
		this.chunkSize = new DynamicChunkSize(initialChunkSize);
	}

	@Override
	protected void init(URLConnection initialConnection) {
		long chunkSize = this.chunkSize.getValue();
		this.submit(new RangeConnection(0, Math.min(chunkSize - 1, fileSize), initialConnection));
		if (chunkSize <= fileSize) {
			this.submitDownloadTask(new Range(chunkSize, fileSize));
		}

	}

	@Override
	protected void onTasksRestart() {
		this.chunkSize.onTasksRestart();

	}

	@Override
	public void processDownloadSuccess(Range range, File file) {
		this.chunkSize.processDownloadSuccess(range, file);
		super.processDownloadSuccess(range, file);
	}

	@Override
	public void processDownloadFail(Range range, File file, long downloadedSize, Exception e) {
		this.chunkSize.processDownloadFail(range, file, downloadedSize, e);
		super.processDownloadFail(range, file, downloadedSize, e);
	}

	@Override
	protected void submitDownloadTask(Range range) {
		long chunkSize = this.chunkSize.getValue();
		long start = range.getFrom();
		long end = range.getTo();
		while (start < end) {
			long rangeEnd = start + chunkSize;
			Range cutRange = new Range(start, Math.min(rangeEnd - 1, end));
			super.submitDownloadTask(cutRange);
			start = rangeEnd;
		}

	}

}
