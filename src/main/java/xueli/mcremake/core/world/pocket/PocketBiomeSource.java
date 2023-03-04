package xueli.mcremake.core.world.pocket;

import java.util.Random;

// Still working on it
@SuppressWarnings("unused")
public class PocketBiomeSource {
	
	private final long seed;
	private final Random random1, random2, random3;
	private final PocketPerlinNoise noise1, noise2, noise3;
	
	
	
	public PocketBiomeSource() {
		this(System.currentTimeMillis());
	}
	
	public PocketBiomeSource(long seed) {
		this.seed = seed;
		random1 = new Random(9871 * seed);
		random2 = new Random(39811 * seed);
		random3 = new Random(543321 * seed);
		
		this.noise1 = new PocketPerlinNoise(random1, 4);
		this.noise2 = new PocketPerlinNoise(random2, 4);
		this.noise3 = new PocketPerlinNoise(random3, 2);
		
	}
	
	public void getBiomeBlock(int a3, int a4, int a5, int a6) {
		double[] of1 = new double[256];
		noise1.getRegion(of1, a3, a4, a5, a5, 0.025, 0.025, 0.25);
		
		double[] of2 = new double[256];
		noise2.getRegion(of2, a3, a4, a5, a5, 0.05, 0.05, 0.3333);
		
		double[] of3 = new double[256];
		noise3.getRegion(of3, a3, a4, a5, a5, 0.05, 0.05, 0.588);
		
		int v17 = 0;
		for(int i = 0; i < a5; i++) {
			for(int j = 0; ; j++) {
				int v10 = j;
				if(j >= a6) break;
				double v8 = of3[v17] * 1.1 + 0.5;
				double v9 = (of1[v17] * 0.15 + 0.7) * 0.99 + v8 * 0.01;
				double v13 = (of2[v17] * 0.15 + 0.5) * 0.998 + v8 * 0.002;
				double v14 = 1.0 - (1.0 - v9) * (1.0 - v9);
				if(v14 < 0.0) v14 = 0.0;
				if(v13 < 0.0) v13 = 0.0;
				if(v14 > 1.0) v14 = 1.0;
				if(v13 > 1.0) v13 = 1.0;
				
				of1[v17] = v14;
				of2[v17] = v13;
				
				int v13_int = (int) (v13 * 63.0);
				System.out.print(v13_int + " ");
				v17++;
				
			}
			System.out.println();
			System.out.println("===== " + a3 + ", " + a4);
		}
		
	}
	
}
