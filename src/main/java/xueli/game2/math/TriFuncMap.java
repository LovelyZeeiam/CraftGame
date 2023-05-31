package xueli.game2.math;

public class TriFuncMap {

	private static final int scale = 20;

	private static final int mapLength = 360 * scale;
	private static final double[] sinMap = new double[mapLength];
	private static final double[] cosMap = new double[mapLength];
	private static final double[] tanMap = new double[mapLength];

	static {
		for (int i = 0; i < mapLength; i++) {
			double degree = (double) i / mapLength * 360;
			double radius = Math.toRadians(degree);
			sinMap[i] = Math.sin(radius);
			cosMap[i] = Math.cos(radius);
			tanMap[i] = Math.tan(radius);
		}
	}

	public static double sin(double degree) {
		int fittingIndex = getFittingIndex(degree);
		return sinMap[fittingIndex];
	}

	public static double cos(double degree) {
		int fittingIndex = getFittingIndex(degree);
		return cosMap[fittingIndex];
	}

	public static double tan(double degree) {
		int fittingIndex = getFittingIndex(degree);
		return tanMap[fittingIndex];
	}

	private static int getFittingIndex(double degree) {
		// degree = MathUtils.floorMod(degree, 360.0); // When degree is
		// -0.000000000000007077671781985373 or something, this method will output 360,
		// which leads to an ArrayOutOfBoundsException
		double index = degree * scale;
		return (int) MathUtils.floorMod(Math.floor(index), mapLength);
	}

}
