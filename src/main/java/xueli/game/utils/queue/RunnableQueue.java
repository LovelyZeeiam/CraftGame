package xueli.game.utils.queue;

import java.util.LinkedList;
import java.util.Queue;

public class RunnableQueue {

	private Queue<Runnable> runnables = new LinkedList<>();

	public RunnableQueue() {

	}

	public void addQueue(Runnable runnable) {
		this.runnables.add(runnable);

	}

	public void tick() {
		// 每次只执行一个
		if (!runnables.isEmpty()) {
			runnables.poll().run();

		}

	}

}
