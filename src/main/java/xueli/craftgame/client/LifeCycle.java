package xueli.craftgame.client;

public interface LifeCycle extends Runnable {

	public void init();

	public boolean isRunning();

	public void tick();

	public void release();

	@Override
	default void run() {
		init();
		while (isRunning()) {
			tick();
		}
		release();
	}

}
