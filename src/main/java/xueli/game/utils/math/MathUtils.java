package xueli.game.utils.math;

import java.math.BigDecimal;

@Deprecated
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
		return (int) Math.floor(value);
	}

	public static int floatToInt2(float value) {
		return (int) value;
	}

	public static long vert2ToLong(int x, int z) {
		return (long) x & 4294967295L | ((long) z & 4294967295L) << 32;
	}

	public static float interpolate(float a, float b, float blend) {
		double theta = blend * Math.PI;
		float f = (float) (1f - Math.cos(theta)) * 0.5f;
		return a * (1f - f) + b * f;
	}

	public static float mixLinear(float a, float b, float blend) {
		return blend * (b - a) + a;
	}

	public static double min(double... nums) {
		double min = nums[0];
		for (int i = 0; i < nums.length; i++) {
			min = Math.min(min, nums[i]);
		}
		return min;
	}

	public static int min(int... nums) {
		int min = nums[0];
		for (int i = 0; i < nums.length; i++) {
			min = Math.min(min, nums[i]);
		}
		return min;
	}

	public static double max(double... nums) {
		double max = nums[0];
		for (int i = 0; i < nums.length; i++) {
			max = Math.max(max, nums[i]);
		}
		return max;
	}

	public static int max(int... nums) {
		int max = nums[0];
		for (int i = 0; i < nums.length; i++) {
			max = Math.max(max, nums[i]);
		}
		return max;
	}

}
