package xueli.utils.download;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;

class RangeDownloadInstance extends DownloadInstance {

	protected CopyOnWriteArrayList<RangeFile> fileList = new CopyOnWriteArrayList<>();
	protected CopyOnWriteArrayList<File> toBeDeletedList = new CopyOnWriteArrayList<>();

	RangeDownloadInstance(URL url, File output, Map<String, String> headers, ExecutorService executor) {
		super(url, output, headers, executor);
	}

	@Override
	protected void init(URLConnection initialConnection) {
		this.submit(new RangeConnection(0, fileSize - 1, initialConnection));
	}

	@Override
	public void processDownloadFail(Range range, File file, long downloadedSize, Exception e) {
		long downloadEndPointer = range.getFrom() + downloadedSize - 1;
		this.submitDownloadTask(new Range(downloadEndPointer + 1, range.getTo()));
		if (downloadedSize > 0) {
			fileList.add(new RangeFile(range.getFrom(), downloadEndPointer, file));
		} else {
			toBeDeletedList.add(file);
		}
	}

	@Override
	public void processDownloadSuccess(Range range, File file) {
		fileList.add(new RangeFile(range.getFrom(), range.getTo(), file));
	}

	@Override
	protected boolean runTheWorker() {
		if (!super.runTheWorker())
			return false;

		try {
			return mergeTheFiles();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
//		return true;
	}

	private boolean mergeTheFiles() throws IOException {
		byte[] ioBuffer = new byte[4096];
		RandomAccessFile randomAccessFile = new RandomAccessFile(output, "rw");
		randomAccessFile.setLength(fileSize);
		for (RangeFile rangeFile : fileList) {
			randomAccessFile.seek(rangeFile.getFrom());
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(rangeFile.getFile()));
			while (in.available() > 0) {
				int readBytes = in.read(ioBuffer);
				randomAccessFile.write(ioBuffer, 0, readBytes);
			}
			in.close();
		}
		randomAccessFile.close();

		fileList.forEach(fragment -> fragment.getFile().delete());
		toBeDeletedList.forEach(File::delete);

		return true;
	}

}
