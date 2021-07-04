package xueli.utils.console;

public class ConwidDownloadProgressbar {

	private String head;

	private long lastTimeCall = 0;
	private long lastDoneSize = 0;

	public ConwidDownloadProgressbar(String head) {
		this.head = head;

	}

	public void call(long doneSize, long fileSize) {
		long duration = 0;
		if ((duration = System.currentTimeMillis() - lastTimeCall) > 1000) {
			lastTimeCall = System.currentTimeMillis();

			// double progress = (double) (doneSize) / fileSize * 100;
			// int progressInt = (int) progress;

			float realDoneSize = doneSize / 1024.0f;

			long deltaSize = doneSize - lastDoneSize;
			double speed = deltaSize / (duration / 1000.0) / 1024.0; // byte per second

			System.out.println("[" + head + "] " + ConsoleFormatter.roundScale(realDoneSize, 1) + "KB < ... > "
					+ ConsoleFormatter.roundScale(speed, 2) + "Kb/s");

			this.lastDoneSize = doneSize;

		}

	}

}
