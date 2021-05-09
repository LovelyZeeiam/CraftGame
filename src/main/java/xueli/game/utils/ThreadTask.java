package xueli.game.utils;

import java.util.concurrent.LinkedBlockingDeque;

public class ThreadTask extends Thread {

	private static final Runnable EMPTY = new Runnable() {
		@Override
		public void run() {
		}
	};

	private boolean running = false;
	private LinkedBlockingDeque<Runnable> queue = new LinkedBlockingDeque<>();

	public ThreadTask() {
		super();

	}

	public ThreadTask(String name) {
		super(name);

	}

	public void addTask(Runnable runnable) {
		if (!this.queue.contains(runnable))
			this.queue.add(runnable);
	}

	@Override
	public void run() {
		running = true;
		try {
			while (running) {
				queue.take().run();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void stopThread() {
		this.running = false;
		queue.add(EMPTY);

	}

}
