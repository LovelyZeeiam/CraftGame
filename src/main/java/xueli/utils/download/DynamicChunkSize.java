package xueli.utils.download;

import java.io.File;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Dynamically calculate the chunk size according to the previous downloading
 * size
 */
public class DynamicChunkSize implements DownloadStateListener {

	private final ArrayList<Long> downloadSizes = new ArrayList<>();
	private long value;

	public DynamicChunkSize(long initialValue) {
		this.value = initialValue;
	}

	public void onTasksRestart() {
		if (!downloadSizes.isEmpty()) {
			double averageValueDouble = downloadSizes.stream().collect(Collectors.averagingLong(l -> l));
			this.value = (long) averageValueDouble;
			downloadSizes.clear();
		}

	}

	@Override
	public void processDownloadSuccess(Range range, File file) {
		long size = range.getTo() - range.getFrom() + 1;
		downloadSizes.add(size);
	}

	@Override
	public void processDownloadFail(Range range, File file, long downloadedSize, Exception e) {
		if (downloadedSize <= 0)
			return;
		downloadSizes.add(downloadedSize);
	}

	public long getValue() {
		return value;
	}

}
