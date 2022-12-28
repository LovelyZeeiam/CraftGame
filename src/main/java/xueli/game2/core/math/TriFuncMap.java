package xueli.game2.core.math;

public class TriFuncMap {
	
	private static final int scale = 20;
	private static final int num = 360 * scale;
	
	private static final float[] sinMap = new float[num];
	private static final float[] cosMap = new float[num];
	
	static {
		for(int i = 0; i < num; i++) {
			double degree = (double)i / num * 360;
			double radius = Math.toRadians(degree);
			sinMap[i] = (float) Math.sin(radius);
			cosMap[i] = (float) Math.cos(radius);
		}
	}
	
	public static float sin(double degree) {
		int fittingIndex = getFittingIndex(degree);
		return sinMap[fittingIndex];
	}
	
	public static float cos(double degree) {
		int fittingIndex = getFittingIndex(degree);
		return cosMap[fittingIndex];
	}
	
	private static int getFittingIndex(double degree) {
		degree = MathUtils.floorMod(degree, 360);
		double index = degree * num / 360;
		return (int) index;
	}

}
