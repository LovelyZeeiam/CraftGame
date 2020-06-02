package xueLi.craftGame.utils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingDeque;

public class TaskManager {

	private static HashMap<Integer, Runnable> tasks = new HashMap<Integer, Runnable>();

	private volatile static LinkedBlockingDeque<Runnable> taskQueue = new LinkedBlockingDeque<Runnable>();

	private static boolean taskListenerStarted = false;
	private static Thread taskListenerThread = new Thread(() -> {
		while (taskListenerStarted) {
			Runnable runnable;
			try {
				runnable = taskQueue.take();
				runnable.run();
			} catch (InterruptedException e) {
				e.printStackTrace();
				Display.postDestroyMessage();
			}
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
		taskListenerStarted = false;
	}

	private volatile static LinkedList<Runnable> mainThreadQueue = new LinkedList<Runnable>();

	public static void addTaskToMainThread(Runnable runnable) {
		mainThreadQueue.add(runnable);
	}

	public static void addTaskToMainThread(int id) {
		mainThreadQueue.add(tasks.get(id));
	}

	public static void processQueueOfMainThread(int count) {
		for (int i = 0; i < count; i++) {
			Runnable runnable = mainThreadQueue.poll();
			if (runnable != null)
				runnable.run();
			else
				break;
		}
	}

}
