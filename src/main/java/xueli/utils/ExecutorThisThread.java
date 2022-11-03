package xueli.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class ExecutorThisThread {

	private ConcurrentLinkedQueue<Runnable> runnables = new ConcurrentLinkedQueue<>();

	public ExecutorThisThread() {
	}

	public Callable<Object> execute(Runnable command) {
		// Block the thread when "get" the result from another thread
		LinkedBlockingDeque<Object> blockingDeque = new LinkedBlockingDeque<>();
		Callable<Object> callable = () -> blockingDeque.poll(Long.MAX_VALUE, TimeUnit.DAYS);
		runnables.add(() -> {
			command.run();
			blockingDeque.add(new Object());
		});
		return callable;
	}

	public <T> Callable<T> execute(Callable<T> callable) {
		LinkedBlockingDeque<T> blockingDeque = new LinkedBlockingDeque<>();
		Callable<T> newCallable = () -> blockingDeque.poll(Long.MAX_VALUE, TimeUnit.DAYS);
		runnables.add(() -> {
			try {
				T call = callable.call();
				blockingDeque.add(call);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
		return newCallable;
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
