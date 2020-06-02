package xueLi.craftGame.utils;

import java.math.BigDecimal;

public class MathUtils {

	public static float doubleToFloat(double value) {
		if (Double.isNaN(value))
			return -Float.MAX_VALUE;
		return new BigDecimal(String.valueOf(value)).floatValue();
	}

	public static double floatToDouble(float value) {
		if (Float.isNaN(value))
			return -Double.MAX_VALUE;
		return new BigDecimal(String.valueOf(value)).doubleValue();
	}

	public static int floatToInt(float value) {
		if (Double.isNaN(value))
			return -Integer.MAX_VALUE;
		return new BigDecimal(String.valueOf(value)).setScale(2, BigDecimal.ROUND_DOWN).intValue();
	}

	public static int floatToInt2(float value) {
		return (int) value;
	}

	public static long vert2ToLong(int x, int z) {
		return (long) x & 4294967295L | ((long) z & 4294967295L) << 32;
	}

}
