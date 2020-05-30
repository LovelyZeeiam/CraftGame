package xueLi.craftGame.utils;

import xueLi.craftGame.Constants;

public class FPSTimer {

	private static int counter = 0;
	private static int fps = 0;

	private static long time1 = 0;

	public static int getFPS() {
		counter++;
		if (Display.currentTime - time1 >= 1000) {
			fps = counter;
			counter = 0;
			time1 = Display.currentTime;
			if (Constants.IS_DEBUG)
				System.out.println("FPS : " + fps);
		}
		return fps;
	}

}
