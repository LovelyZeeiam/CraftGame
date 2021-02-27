package xueli.game;

import xueli.game.display.Display;
import xueli.game.utils.Time;

public abstract class Game implements Runnable {

	public static Game INSTANCE_GAME;

	private Display display;
	private final String workingDirectory;

	public Game(int width, int height, String windowTitle, String workingDirectory) {
		INSTANCE_GAME = this;

		this.display = new Display(width, height, windowTitle);
		this.workingDirectory = workingDirectory;

	}

	@Override
	public void run() {
		display.create();
		Time.tick();

		oncreate();
		display.show();

		while (display.isRunning()) {
			onrender();
			display.update();
			Time.tick();
		}

		onrelease();

	}

	public abstract void oncreate();

	public abstract void onrender();

	public abstract void onrelease();

}
