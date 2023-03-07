package xueli.mcremake.registry.chunkgenerator.pocket;

import java.util.Random;

import xueli.mcremake.core.world.Chunk;
import xueli.mcremake.registry.PocketNativeType;

@PocketNativeType("RandomLevelSource")
public class PocketEditionChunkProvider {
	
	@PocketNativeType("*((char*)this + 6644))") private final Random random;
	@PocketNativeType("*((char*)this + 9148))") /*Each size: 2520*/ private final PocketPerlinNoise[] noises = new PocketPerlinNoise[8];
	
	@PocketNativeType("*((_DWORD*)this + 7327))") private final PocketBiomeSource biomeSource;
	
	public PocketEditionChunkProvider() {
		this(System.currentTimeMillis());
	}
	
	public PocketEditionChunkProvider(long seed) {
		this.random = new Random(seed);
		this.biomeSource = new PocketBiomeSource(seed);
		
		/* (char*)this + 9148 */	noises[0] = new PocketPerlinNoise(this.random, 16);
		/* (char*)this + 11668 */	noises[1] = new PocketPerlinNoise(this.random, 16);
		/* (char*)this + 14188 */	noises[2] = new PocketPerlinNoise(this.random, 8);
		/* (char*)this + 16708 */	noises[3] = new PocketPerlinNoise(this.random, 4);
		/* (char*)this + 19228 */	noises[4] = new PocketPerlinNoise(this.random, 4);
		/* (char*)this + 21748 */	noises[5] = new PocketPerlinNoise(this.random, 10);
		/* (char*)this + 24268 */	noises[6] = new PocketPerlinNoise(this.random, 16);
		/* (char*)this + 26788 */	noises[7] = new PocketPerlinNoise(this.random, 8);
		
	}
	
	public void genChunk(int x, int z, Chunk chunk) {
		Random thisChunkRandom = new Random(132899541 * x + 341872712 * z);
		
		// |<== Prepare Heights ==>|
		double[] heights1 = new double[1024];
		this.getHeights(heights1, 4.0 * z, 0, 4 * x, 5, 17, 5);
		
		
	}
	
	// Seems like if you take in a negative value it will take a negative value as an index 
	private void getHeights(double[] dest, double a3, int a4, double a5, int a6, int a7, int a8) {
		double[] r1 = noises[5].getRegion(null, a3, a5, a6, a8, 1.121, 1.121, 0.5);
		double[] r2 = noises[5].getRegion(null, a3, a5, a6, a8, 200.0, 200.0, 0.5);
		double[] r3 = noises[5].getRegion(null, a3, a4, a5, a6, a7, a8, 684.41 / 80.0, 684.41 / 160.0, 684.41 / 80.0);
		double[] r4 = noises[5].getRegion(null, a3, a4, a5, a6, a7, a8, 684.41, 684.41, 684.41);
		double[] r5 = noises[5].getRegion(null, a3, a4, a5, a6, a7, a8, 684.41, 684.41, 684.41);
		
		for(int i = 0; i < a6; i++) {
			for(int j = 0; j < a8; j++) {
				int v10 = 16 / a6;
//				double v11 = 
				
			}
		}
		
	}
	
}
