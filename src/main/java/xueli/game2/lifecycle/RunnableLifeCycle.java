package xueli.game2.lifecycle;

public interface RunnableLifeCycle extends LifeCycle, Runnable {
	
	public boolean isRunning();

	@Override
	default void run() {
		init();
		while (isRunning()) {
			gameLoop();
		}
		release();
	}

}
