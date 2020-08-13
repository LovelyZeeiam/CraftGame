package xueLi.gamengine.utils;

public class Time {

	private static long last_time = System.currentTimeMillis();

	public static long deltaTime = 0;
	public static long thisTime = 0;

	public static void tick() {
		thisTime = System.currentTimeMillis();
		deltaTime = thisTime - last_time;
		last_time = thisTime;

	}

}
