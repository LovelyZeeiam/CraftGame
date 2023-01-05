package xueli.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingDeque;

@Deprecated
public class ThreadTask extends Thread {

	private static final Task EMPTY = new Task(() -> {
	});

	protected boolean running = false;

	protected LinkedBlockingDeque<Task> queue = new LinkedBlockingDeque<>();
	protected HashMap<String, ArrayList<Task>> runnables = new HashMap<>();

	public ThreadTask() {
		super();

	}

	public ThreadTask(String name) {
		super(name);

	}

	public void addTask(Runnable runnable, String tag) {
		if (!this.queue.contains(new Task(runnable))) {
			this.queue.add(new Task(runnable));

			if (!runnables.containsKey(tag))
				runnables.put(tag, new ArrayList<>());
			runnables.get(tag).add(new Task(runnable));

		}

	}

	public void addTask(Runnable runnable) {
		addTask(runnable, "bus");
	}

	public void removeTasks(String tag) {
		if (!runnables.containsKey(tag))
			return;

		runnables.get(tag).forEach(t -> {
			if (t.state == TaskState.WAIT_IN_LIST) {
				queue.remove(t);
			}
		});

	}

	public boolean hasTask() {
		return !queue.isEmpty();
	}

	@Override
	public void run() {
		running = true;
		try {
			while (running) {
				Task task = queue.take();
				task.state = TaskState.RUNNING;
				task.runnable.run();
				task.state = TaskState.DONE;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void stopThread() {
		this.running = false;
		queue.add(EMPTY);

	}

	private static class Task {

		public Runnable runnable;
		public TaskState state = TaskState.WAIT_IN_LIST;

		public Task(Runnable runnable) {
			this.runnable = runnable;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((runnable == null) ? 0 : runnable.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Task other = (Task) obj;
			if (runnable == null) {
				if (other.runnable != null)
					return false;
			} else if (!runnable.equals(other.runnable))
				return false;
			return true;
		}

	}

	private static enum TaskState {
		WAIT_IN_LIST, RUNNING, DONE
	}

}
