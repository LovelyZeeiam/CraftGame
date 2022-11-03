package xueli.game.utils;

public class FPSCalculator {

	public static long deltaTime = 0;
	public static long thisTime = System.currentTimeMillis();
	public static int fps = 0;
	private static long last_time = System.currentTimeMillis();
	private static long lastTimeCountFPS = 0;
	private static int count = 0;

	public static void tick() {
		thisTime = System.currentTimeMillis();
		deltaTime = thisTime - last_time;
		last_time = thisTime;

		if (thisTime - lastTimeCountFPS < 1000) {
			count++;
		} else {
			fps = count;
			count = 0;

			lastTimeCountFPS = thisTime;
			// System.out.println("FPS: " + fps);

		}

	}

}
