package xueli.utils.download;

import java.io.File;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DownloadMain {

	public static void main(String[] args) throws Exception {
		String url, filePath;
		int chunkSize;
		try {
			url = args[0];
			filePath = args[1];
			chunkSize = args.length > 2 ? Integer.parseInt(args[2]) : Integer.MAX_VALUE;
		} catch (Exception e) {
			System.out.println("Usage: java -jar (JarPath) (URL) (FilePath) [ChunkSize]");
			return;
		}

		ExecutorService executor = Executors.newWorkStealingPool();
		DownloadInstance downloadInstance = DownloadInstance.create(new URL(url), new File(filePath), null, chunkSize, executor);
		downloadInstance.run();
		executor.shutdownNow();

	}

}
