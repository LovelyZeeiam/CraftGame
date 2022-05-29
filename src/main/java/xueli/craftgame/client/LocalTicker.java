package xueli.craftgame.client;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;

import xueli.craftgame.CraftGameContext;
import xueli.craftgame.block.BlockType;
import xueli.craftgame.event.EventBlockListener;
import xueli.craftgame.event.EventRequireChunk;
import xueli.craftgame.event.EventSetBlock;
import xueli.craftgame.utils.ExecutorThisThread;

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

	private ExecutorThisThread runList = new ExecutorThisThread();

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
		// Also tick for the world and tickable blocks
		while (running) {

			runList.peekAndRunAll();
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
		if (!ctx.getWorld().canOperateBlock(event.getX(), event.getY(), event.getZ()))
			return;

		runList.execute(() -> {
			if (event.getBlock() == null) {
				BlockType block = ctx.getWorld().getBlock(event.getX(), event.getY(), event.getZ());
				if (block != null) {
					block.getListener().onDestroyed(event.getX(), event.getY(), event.getZ(), this, event.getEntity());
				}
			} else {
				event.getBlock().getListener().onPlaced(event.getX(), event.getY(), event.getZ(), this,
						event.getEntity());
			}
		});

		ctx.submitEvent(event);

	}

	@Subscribe
	public void onRequireChunk(EventRequireChunk event) {
		eventBusCalls.execute(() -> worldIO.onRequireChunk(event));
	}

	@Subscribe
	public void onBlockListener(EventBlockListener event) {
		BlockType block = ctx.getWorld().getBlock(event.getX(), event.getY(), event.getZ());
		if (block == null)
			return;

		switch (event.getType()) {
		case LOOK_AT -> runList.execute(
				() -> block.getListener().onLookAt(event.getX(), event.getY(), event.getZ(), this, event.getEntity()));
		case PLACED -> runList.execute(
				() -> block.getListener().onPlaced(event.getX(), event.getY(), event.getZ(), this, event.getEntity()));
		case LEFT_CLICK -> runList.execute(() -> block.getListener().onLeftClick(event.getX(), event.getY(),
				event.getZ(), this, event.getEntity()));
		case RIGHT_CLICK -> runList.execute(() -> block.getListener().onRightClick(event.getX(), event.getY(),
				event.getZ(), this, event.getEntity()));
		}

	}

}
