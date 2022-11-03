package xueli.utils.download;

import java.io.File;

public interface DownloadStateListener {

	void processDownloadSuccess(Range range, File file);

	void processDownloadFail(Range range, File file, long downloadedSize, Exception e);

}
