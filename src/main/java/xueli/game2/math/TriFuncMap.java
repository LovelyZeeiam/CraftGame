package xueli.game2.math;

public class TriFuncMap {
	
	private static final int scale = 20;
	private static final int num = 360 * scale;
	
	private static final double[] sinMap = new double[num];
	private static final double[] cosMap = new double[num];
	private static final double[] tanMap = new double[num];
	
	static {
		for(int i = 0; i < num; i++) {
			double degree = (double)i / num * 360;
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
		degree = MathUtils.floorMod(degree, 360);
		double index = degree * num / 360;
		return (int) index;
	}

}
