package xueli.utils.swings;

import xueli.utils.ThreadTask;

import javax.swing.*;

public class AWTSwingRefresher extends Thread {

	private ThreadTask taskManager;
	private JComponent c;
	private long refreshMills;

	private boolean running = true;

	public AWTSwingRefresher(ThreadTask taskManager, JComponent c, long refreshMills) {
		super("AWTSwingRefresher");
		this.c = c;
		this.refreshMills = refreshMills;
		this.taskManager = taskManager;

	}

	@Override
	public void run() {
		try {
			boolean nextTimeCheckShouldRun = false;
			while (running) {
				synchronized (this) {
					wait(refreshMills);
				}

				if (nextTimeCheckShouldRun && !taskManager.hasTask()) {
					c.updateUI();
					System.gc();
				}

				nextTimeCheckShouldRun = taskManager.hasTask();

			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void postExit() {
		running = false;
	}

}
