package xueli.utils.concurrent;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.Delayed;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ControllerExecutorService extends AbstractExecutorService implements ScheduledExecutorService {
	
	private TreeMap<Long, ArrayList<Runnable>> queue = new TreeMap<>(); // 可以改成二叉树
	
	public void tick() {
		Long thisTime = System.nanoTime();
		synchronized (queue) {
			Entry<Long, ArrayList<Runnable>> entry;
			while((entry = queue.floorEntry(thisTime)) != null) {
//				System.out.println(entry);
				queue.remove(entry.getKey());
				entry.getValue().forEach(Runnable::run);
			}
		}
	
		
	}
	
	@Override
	public void shutdown() {
	}

	@Override
	public List<Runnable> shutdownNow() {
		ArrayList<Runnable> remaining = new ArrayList<>();
		queue.values().forEach(remaining::addAll);
		return remaining;
	}

	@Override
	public void execute(Runnable command) {
		synchronized (queue) {
			queue.computeIfAbsent(System.nanoTime(), t -> new ArrayList<>()).add(command);
		}
	}
	
	private final AtomicInteger sequenceCounter = new AtomicInteger();

	@Override
	public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
		long runTime = unit.toNanos(delay) + System.nanoTime();
		MyScheduledFuture<Void> future = new MyScheduledFuture<>(runTime, command, null, sequenceCounter.getAndIncrement());
		synchronized (queue) {
			queue.computeIfAbsent(runTime, t -> new ArrayList<>()).add(future);
		}
		return future;
	}

	@Override
	public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
		long runTime = unit.toNanos(delay) + System.nanoTime();
		MyScheduledFuture<V> future = new MyScheduledFuture<>(runTime, callable, sequenceCounter.getAndIncrement());
		synchronized (queue) {
			queue.computeIfAbsent(runTime, t -> new ArrayList<>()).add(future);
		}
		return future;
	}

	@Override
	public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
		long runTime = unit.toNanos(initialDelay) + System.nanoTime();
		MyScheduledFuture<Void> future = new MyScheduledFuture<>(runTime, unit.toNanos(period), command, null, sequenceCounter.getAndIncrement());
		synchronized (queue) {
			queue.computeIfAbsent(runTime, t -> new ArrayList<>()).add(future);
		}
		return future;
	}

	@Override
	public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
		throw new UnsupportedOperationException("Actually the programmer can't figure out what the difference is between this and the method above :{");
	}
	
	@Override
	public boolean isShutdown() {
		return false;
	}

	@Override
	public boolean isTerminated() {
		return false;
	}

	@Override
	public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
		return true;
	}
	
	class MyScheduledFuture<V> extends FutureTask<V> implements ScheduledFuture<V> {
		
		private long time, period = 0;
		private final int sequenceNum;
		
		public MyScheduledFuture(long time, Callable<V> callable, int sequenceNum) {
			super(callable);
			this.time = time;
			this.sequenceNum = sequenceNum;
		}

		public MyScheduledFuture(long time, Runnable runnable, V result, int sequenceNum) {
			super(runnable, result);
			this.time = time;
			this.sequenceNum = sequenceNum;
		}
		
		public MyScheduledFuture(long time, long period, Runnable runnable, V result, int sequenceNum) {
			super(runnable, result);
			this.time = time;
			this.period = period;
			this.sequenceNum = sequenceNum;
		}

		@Override
		public long getDelay(TimeUnit unit) {
			return unit.convert(time - System.nanoTime(), TimeUnit.NANOSECONDS);
		}

		@Override
		public int compareTo(Delayed other) {
			if (other == this)
                return 0;
			if (other instanceof MyScheduledFuture x) {
                long diff = time - x.time;
                if (diff < 0)
                    return -1;
                else if (diff > 0)
                    return 1;
                else if (sequenceNum < x.sequenceNum)
                    return -1;
                else
                    return 1;
            }
			long diff = getDelay(NANOSECONDS) - other.getDelay(NANOSECONDS);
            return (diff < 0) ? -1 : (diff > 0) ? 1 : 0;
		}
		
		@Override
		public void run() {
			if(period == 0) {
				super.run();
			} else if(!this.isCancelled()) {
				super.runAndReset();
				this.time += this.period;
				synchronized (queue) {
					queue.computeIfAbsent(this.time, t -> new ArrayList<>()).add(this);
				}
				
			}
		}
		
	}

}
