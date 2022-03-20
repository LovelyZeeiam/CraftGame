package xueli.craftgame.client;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;

import xueli.craftgame.CraftGameContext;
import xueli.craftgame.event.EventRequireChunk;
import xueli.craftgame.event.EventSetBlock;

public class LocalTicker {

	private CraftGameContext ctx;

	private Thread tickThread = new Thread(this::run);
	private volatile boolean running = false;

	// The Main EventBus Communicating with Everything
	// private ExecutorService eventBusExecutor =
	// Executors.newSingleThreadExecutor(new
	// ThreadFactoryBuilder().setNameFormat("LocalTicker-EventBus-pool-%d").build());
	private ExecutorService eventBusCalls = Executors.newSingleThreadExecutor();
	private AsyncEventBus masterEventBus = new AsyncEventBus(eventBusCalls);

	private LocalWorldIO worldIO;

	public LocalTicker(CraftGameContext ctx) {
		this.ctx = ctx;
		this.masterEventBus.register(this);

		this.worldIO = new LocalWorldIO(this, ctx.getWorld());

	}

	public void start() {
		this.running = true;
		this.tickThread.start();

	}

	private void run() {
		// Block and Run for All the Events
		while (running) {

		}

	}

	public void stop() {
		this.running = false;

		eventBusCalls.shutdownNow();

	}

	public void submitEvent(Object eventObject) {
		masterEventBus.post(eventObject);
	}

	@Subscribe
	public void onBlockChange(EventSetBlock event) {
		if(!ctx.getWorld().canOperateBlock(event.getX(), event.getY(), event.getZ()))
			return;
		
		ctx.submitEvent(event);
		
	}

	@Subscribe
	public void onRequireChunk(EventRequireChunk event) {
		eventBusCalls.execute(() -> worldIO.onRequireChunk(event));
	}

}
