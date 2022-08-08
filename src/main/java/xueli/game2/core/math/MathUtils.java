package xueli.game2.core.math;

public class MathUtils {

	private MathUtils() {
	}
	
	public static float floorMod(float x, float y) {
		float multiple = x / y;
		return x - (int) multiple * y;
	}

}
