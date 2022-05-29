package xueli.utils.concurrent;

import java.util.concurrent.CopyOnWriteArrayList;

import xueli.utils.ThreadTask;

public class Concurrent extends ThreadTask {

	private CopyOnWriteArrayList<Thread> summited = new CopyOnWriteArrayList<>();
	private Runnable callbackWhenTaskEnds;

	private Thread demonstrator = new Thread() {
		public void run() {
			while (true) {
				synchronized (this) {
					try {
						wait(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				if (!summited.isEmpty()) {
					while (true) {
						synchronized (this) {
							try {
								wait(500);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}

						boolean threadEnded = true;
						for (Thread thread : summited) {
							if (thread.getState() != State.TERMINATED) {
								threadEnded = false;
								break;
							}
						}

						if (threadEnded) {
							break;
						}
					}

					if (callbackWhenTaskEnds != null)
						callbackWhenTaskEnds.run();

					summited.clear();

				}

			}
		}

		;
	};

	public Concurrent() {
		demonstrator.start();

	}

	public void summit(Runnable r) {
		Thread thread = new Thread(r);
		thread.start();
		summited.add(thread);

	}

	public void setWhenTaskEndsCallback(Runnable callback) {
		this.callbackWhenTaskEnds = callback;
	}

}
