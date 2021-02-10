package xueli.gamengine.utils.resource;

import java.util.HashMap;
import java.util.concurrent.LinkedBlockingDeque;

public class TaskManager {

	private static HashMap<Integer, Runnable> tasks = new HashMap<Integer, Runnable>();

	private volatile static LinkedBlockingDeque<Runnable> taskQueue = new LinkedBlockingDeque<Runnable>();

	private static boolean taskListenerStarted = false;
	private static boolean lastTimeWaitingTheQueue = false;
	private static Thread taskListenerThread = new Thread(() -> {
		while (taskListenerStarted) {
			Runnable runnable = null;
			try {
				runnable = taskQueue.take();
			} catch (InterruptedException e) {
			}
			if (runnable != null)
				runnable.run();
			if (lastTimeWaitingTheQueue & taskQueue.isEmpty())
				taskListenerStarted = false;
		}
	}, "Task Listener");

	public static void startListener() {
		taskListenerStarted = true;
		taskListenerThread.start();
	}

	public static void addTask(int id, Runnable runnable) {
		tasks.put(id, runnable);
	}

	// 从task的map里面选择任务创建
	public static void createTask(int id) {
		taskQueue.add(tasks.get(id));
	}

	// 手动创建task
	public static void createTask(Runnable runnable) {
		taskQueue.add(runnable);
	}

	public static void stopListener() {
		lastTimeWaitingTheQueue = true;
		taskListenerThread.interrupt();

	}

}
