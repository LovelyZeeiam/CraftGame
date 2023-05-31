package xueli.game2.math;

public class MathUtils {

	private MathUtils() {
	}

	public static float floorMod(float x, float y) {
		float multiple = x / y;
		return (float) (x - Math.floor(multiple) * y);
	}

	public static double floorMod(double x, double y) {
		double multiple = x / y;
		return x - Math.floor(multiple) * y;
	}

}
