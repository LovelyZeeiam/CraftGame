package xueli.game.utils;

public class FPSCalculator {

	private long deltaTime = 0;
	private long thisTime = System.currentTimeMillis();

	private long last_time = System.currentTimeMillis();
	private long lastTimeCountFPS = 0;

	private int count = 0;

	private int fps = 0;

	public void tick() {
		thisTime = System.currentTimeMillis();
		deltaTime = thisTime - last_time;
		last_time = thisTime;

		if (thisTime - lastTimeCountFPS < 1000) {
			count++;
		} else {
			fps = count;
			count = 0;

			lastTimeCountFPS = thisTime;
			 System.out.println("FPS: " + fps);

		}

	}

	public int getFps() {
		return fps;
	}

}
