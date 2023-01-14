package xueli.game2.worker;

import java.util.function.Function;

/**
 * A Task Handler
 */
@Deprecated
public interface TaskHandler<T> {
	
	public T join();
	
	public void cancel();
	
	public <R> TaskHandler<R> thenRunInMainThread(Function<T, R> func);
	
	public <R> TaskHandler<R> thenRunAsync(Function<T, R> func);
	
	
	
}
