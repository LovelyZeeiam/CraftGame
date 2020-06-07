package xueLi.craftGame.utils;

import java.util.Random;

/**
 * This class is in beta and hasn't been started.
 *
 */
public class NoiseGenerator {

	public static Random random = new Random();

	// 中文中的起伏
	private static final int QIFU = 10;

	public long seed;

	public NoiseGenerator() {
		this.seed = random.nextLong();

	}

	public NoiseGenerator(long seed) {
		this.seed = seed;

	}

	private float getNoise(int x, int z) {
		random.setSeed(x * 114514 + z * 1919180 + seed);
		return random.nextFloat();
	}

	// Smooth the noise
	private float interpolate(float a, float b, float blend) {
		double theta = blend * Math.PI;
		float f = (float) (1f - Math.cos(theta)) * 0.5f;
		return a * (1f - f) + b * f;
	}

	public int[][] generate(int startX, int startZ, int width, int height) {
		int[][] noise = new int[width][height];

		float y1 = getNoise(startX, startZ) * QIFU;
		float y2 = getNoise(startX + width, startZ) * QIFU;
		float y3 = getNoise(startX, startZ + height) * QIFU;
		float y4 = getNoise(startX + width, startZ + height) * QIFU;

		for (int x = 0; x < width; x++) {
			for (int z = 0; z < height; z++) {
				float v1 = interpolate(y1, y2, (float) x / width);
				float v2 = interpolate(y3, y4, (float) x / width);
				noise[x][z] = (int) interpolate(v1, v2, (float) z / height);
			}
		}

		return noise;
	}

}
