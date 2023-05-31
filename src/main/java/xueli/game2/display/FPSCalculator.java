package xueli.game2.display;

public class FPSCalculator {

	private long thisTime;
	private long lastTimeCountFPS = 0;

	private int count = 0;

	private int fps = 0;

	public void tick() {
		thisTime = System.currentTimeMillis();
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
