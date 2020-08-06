package xueLi.gamengine.utils;

public class Time {

	private static long last_time = System.currentTimeMillis();

	public static long deltaTime = 0;

	public static void tick() {
		long this_time = System.currentTimeMillis();
		deltaTime = this_time - last_time;
		last_time = this_time;

	}

}
