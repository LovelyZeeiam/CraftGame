package xueli.mcremake.registry.chunkgenerator.pocket;

import java.util.Random;

import riven.PerlinNoise;
import xueli.mcremake.registry.PocketNativeType;

@PocketNativeType(version = "0.1.3", value = "PerlinNoise")
public class PocketPerlinNoise {
	
	@SuppressWarnings("unused")
	private final Random random1;
	@PocketNativeType(version = "0.1.3", value = "*((_DWORD *)this + 629)")
	private final Random random2;
	
	@PocketNativeType(version = "0.1.3", value = "*((_DWORD *)this + 2)")
	private final int num;
	
	@PocketNativeType(version = "0.1.3", value = "*((_DWORD *)this + 1)")
	private PerlinNoise[] result;
	
	public PocketPerlinNoise(int num) {
		random2 = random1 = new Random();
		this.num = num;
		this.init(num);
	}
	
	public PocketPerlinNoise(Random random, int num) {
		random1 = new Random();
		random2 = random;
		this.num = num;
		this.init(num);
	}
	
	private void init(int num) {
		this.result = new PerlinNoise[num];
		for(int i = 0; i < num; i++) {
			this.result[i] = new PerlinNoise(random2);
		}
	}
	
	public double getValue(double v5, double v4) {
		double v8 = 1.0, v9 = 0.0;
		for(int i = 0; i < this.num; i++) {
			v9 += this.result[i].noise(v5 * v8, v4 * v8, 0);
			v8 /= 2.0;
		}
		return v9;
	}
	
	public double getValue(double v7, double v6, double v5) {
		double v10 = 1.0, v11 = 0.0;
		for(int i = 0; i < this.num; i++) {
			v11 += this.result[i].noise(v7 * v10, v6 * v10, v5 * v10);
			v10 /= 2.0;
		}
		return v11;
	}
	
	public double[] getRegion(double[] dest, double a3, double a4, int a5, int a6, double a7, double a8, double a9) {
		return this.getRegion(dest, a3, 10.0, a4, a5, 1, a6, a7, 1.0, a8);
	}
	
	public double[] getRegion(double[] v14, double v13, double v12, double a5, int a6, int a7, int a8, double a9, double a10, double a11) {
		int v19 = a8 * a7 * a6;
		if(v14 == null) v14 = new double[v19];
		// Set all the element in v14 to 0.0 here but Java did this
		if(this.num > 0) {
			double v18 = 1.0;
			for(int j = 0; j < this.num; j++) {
				result[j].add(v14, v13, v12, a5, a6, a7, a8, a9 * v18, a10 * v18, a11 * v18, v18);
				v18 /= 2.0;
			}
		}
		return v14;
	}
	
}
