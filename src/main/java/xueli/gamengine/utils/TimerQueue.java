package xueli.gamengine.utils;

import java.util.Comparator;
import java.util.TreeSet;

public class TimerQueue {
	
	public static class Task {
		
		private long executeTime;
		private Runnable runnable;
		
		public Task(long executeTime, Runnable runnable) {
			this.executeTime = executeTime;
			this.runnable = runnable;

		}
		
		public long getExecuteTime() {
			return executeTime;
		}
		
		public Runnable getRunnable() {
			return runnable;
		}
		
	}
	
	private TreeSet<Task> runnables = new TreeSet<Task>(Comparator.comparingLong(Task::getExecuteTime));

	public TimerQueue() {
		
	}
	
	public void addQueue(int time, Runnable runnable) {
		Task task = new Task(Time.thisTime + time, runnable);
		runnables.add(task);
		
	}
	
	public void addAll(TreeSet<Task> tasks) {
		for (Task task : tasks) {
			runnables.add(new Task(task.executeTime + Time.thisTime, task.runnable));
		}
		
	}

	public void tick() {
		if(!runnables.isEmpty() && Time.thisTime > runnables.first().executeTime) {
			runnables.pollFirst().runnable.run();
			return;
		}
		
	}
	
}
