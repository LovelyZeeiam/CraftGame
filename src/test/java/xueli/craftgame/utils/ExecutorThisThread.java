package xueli.craftgame.utils;

import java.util.LinkedList;
import java.util.concurrent.Executor;

public class ExecutorThisThread implements Executor {

	private LinkedList<Runnable> runnables = new LinkedList<>();

	public ExecutorThisThread() {
	}

	@Override
	public void execute(Runnable command) {
		runnables.add(command);
	}

	public void peekAndRun() {
		Runnable run = runnables.poll();
		if (run == null)
			return;
		run.run();
	}

	public void peekAndRunAll() {
		while (!runnables.isEmpty()) {
			Runnable run = runnables.poll();
			if (run != null)
				run.run();
		}
	}

}
