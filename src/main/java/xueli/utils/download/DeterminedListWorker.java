package xueli.utils.download;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Consumer;

/**
 * <code>DeterminedListWorker</code> is a worker that keeps working until the list gets empty. When the
 * task throws an exception, it can send the exception to the <code>BiConsumer</code>, but at the same
 * time, another task can be submitted. This can be very useful when doing work filled with exceptions.
 * <br/>
 * It has two executor, one of which from the constructor is
 *
 * @param <T> The task carrier
 */
public abstract class DeterminedListWorker<T> {

	private final CopyOnWriteArrayList<T> workList = Lists.newCopyOnWriteArrayList();
	private final ExecutorService executor;

	/**
	 * @param executor The executor that all the download task apart will run at.
	 */
	public DeterminedListWorker(ExecutorService executor) {
		this.executor = Objects.requireNonNull(executor);
	}

	public void submit(T t) {
		workList.add(t);
	}

	/**
	 * The <code>runTask</code> method will be used to process the task carrier.
	 *
	 * @param t The task carrier
	 * @throws Exception used when it is thought that the exception is extremely severe that it must be
	 *                   thrown immediately, stopping all the tasks right now. When this happens, you
	 *                   can use <code>forEach</code> to take the remaining tasks back.
	 */
	protected abstract void runTask(T t) throws Exception;

	public boolean run() {
		return this.runTheWorker();
	}

	/**
	 * Submit <i>the task manager</i> to another executor service and get a <code>Future</code> representing its
	 * state. This can be working if a multi-file downloading is considered.
	 */
	public Future<Boolean> run(ExecutorService executor) {
		return executor.submit(this::runTheWorker);
	}

	/**
	 * The <code>runTheWorker</code> method actually serves as a task manager, which takes the task,
	 * submits to the executor from the constructor and tracks its progress.
	 */
	protected boolean runTheWorker() {
		while (!workList.isEmpty()) {
			this.onTasksRestart();

			ArrayList<Future<?>> futures = Lists.newArrayList();

			for (T t : workList) {
				workList.remove(t);
				Future<?> future = this.executor.submit(() -> {
					try {
						this.runTask(t);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				});
				futures.add(future);
			}

			for (Future<?> f : futures) {
				try {
					f.get();
				} catch (InterruptedException | ExecutionException e) {
					throw new RuntimeException(e);
				}
			}

		}
		return true;
	}

	/**
	 * Execute when the new run is executed
	 */
	protected void onTasksRestart() {
	}

	public synchronized void forEach(Consumer<T> action) {
		this.workList.forEach(action);
	}

}
