package xueli.craftgame.world;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.google.common.eventbus.AsyncEventBus;

public class WorldIO {

	private World world;
	private WorldGenerator generator;

	private ExecutorService executor = Executors.newSingleThreadExecutor();
	private AsyncEventBus eventBus = new AsyncEventBus(executor);

	public WorldIO(World world) {
		this.world = world;
		this.generator = new WorldGenerator(world);

		eventBus.register(this);

	}

	public Future<SubChunk> getChunk(int x, int z) {
		return executor.submit(new Callable<SubChunk>() {
			@Override
			public SubChunk call() throws Exception {
				return generator.genChunk(x, z);
			}
		});
	}

	public void postEvent(Object eventObject) {
		eventBus.post(eventObject);
	}

	public void tick() {

	}

	public void release() {
		try {
			executor.awaitTermination(500, TimeUnit.MICROSECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
