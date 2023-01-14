package xueli.game2.worker;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameWorker {
	
	private final Thread mainThread;
	
	private final MainThreadWorker mainThreadWorker = new MainThreadWorker();
	private final ExecutorService concurrentWorker = Executors.newWorkStealingPool();
	
	public GameWorker() {
		this.mainThread = Thread.currentThread();
		
	}
	
	public Executor mainThreadWorker() {
		return mainThreadWorker;
	}
	
	public Executor concurrentWorker() {
		return concurrentWorker;
	}
	
	public void tickMainThread() {
		mainThreadWorker.tickMainThread();
	}
	
	public void shutdown() {
		concurrentWorker.shutdownNow();
	}
	
	private class MainThreadWorker implements Executor {
		
		private ConcurrentLinkedQueue<Runnable> queue = new ConcurrentLinkedQueue<>();
		
		@Override
		public void execute(Runnable command) {
			if(Thread.currentThread().equals(GameWorker.this.mainThread)) {
				command.run();
				return;
			}
			queue.add(command);
		}
		
		public void tickMainThread() {
			Runnable r;
			if((r = queue.poll()) != null) {
				r.run();
			}
			
		}
		
	}
	
}
