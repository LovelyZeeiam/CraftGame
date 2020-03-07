package xueLi.craftGame.utils;

public class FPSTimer {

	private static int counter = 0;
	private static int fps = 0;

	private static long time1 = 0;

	public static int getFPS() {
		counter++;
		if (DisplayManager.currentTime - time1 >= 1000) {
			fps = counter;
			counter = 0;
			time1 = DisplayManager.currentTime;
			if (DisplayManager.isDebug)
				System.out.println("FPS : " + fps);
		}
		return fps;
	}

}
