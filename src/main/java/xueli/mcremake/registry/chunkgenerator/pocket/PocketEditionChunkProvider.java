package xueli.mcremake.registry.chunkgenerator.pocket;

import java.util.Random;

import xueli.mcremake.core.block.BlockType;
import xueli.mcremake.core.world.Chunk;
import xueli.mcremake.registry.GameRegistry;
import xueli.mcremake.registry.PocketNativeType;

@PocketNativeType("RandomLevelSource")
public class PocketEditionChunkProvider {

	@PocketNativeType("*((char*)this + 6644))")
	private final Random random;
	@PocketNativeType("*((char*)this + 9148))")
	/* Each size: 2520 */ private final PocketPerlinNoise[] noises = new PocketPerlinNoise[8];

	@PocketNativeType("*((_DWORD*)this + 7327))")
	private final PocketBiomeSource biomeSource;

	public PocketEditionChunkProvider() {
		this(System.currentTimeMillis());
	}

	public PocketEditionChunkProvider(long seed) {
		this.random = new Random(seed);
		this.biomeSource = new PocketBiomeSource(seed);

		/* (char*)this + 9148 */ noises[0] = new PocketPerlinNoise(this.random, 16);
		/* (char*)this + 11668 */ noises[1] = new PocketPerlinNoise(this.random, 16);
		/* (char*)this + 14188 */ noises[2] = new PocketPerlinNoise(this.random, 8);
		/* (char*)this + 16708 */ noises[3] = new PocketPerlinNoise(this.random, 4);
		/* (char*)this + 19228 */ noises[4] = new PocketPerlinNoise(this.random, 4);
		/* (char*)this + 21748 */ noises[5] = new PocketPerlinNoise(this.random, 10);
		/* (char*)this + 24268 */ noises[6] = new PocketPerlinNoise(this.random, 16);
		/* (char*)this + 26788 */ noises[7] = new PocketPerlinNoise(this.random, 8);

	}

	public void genChunk(int x, int z, Chunk chunk) {
		/* (float *)this + 1661 */ Random thisChunkRandom = new Random(132899541 * x + 341872712 * z); 
		
		// |<== Prepare Biomes ==>|
//		BiomeType[] biomes = biomeSource.getBiomeBlock(null, 16 * z, 16 * x, 16, 16);
		
		// |<== Prepare Heights ==>|
		double[] rawHeights = new double[1024];
		this.getHeights(rawHeights, 4.0 * z, 0, 4.0 * x, 5, 17, 5);
		
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				for(int k = 0; k < 16; k++) {
					double v24 = rawHeights[k + 17 * (j + 5 * i)];
					double v23 = rawHeights[k + 17 * (j + 1 + 5 * i)];
					double v22 = rawHeights[k + 17 * (j + 5 * (i + 1))];
					double v21 = rawHeights[k + 17 * (j + 1 + 5 * (i + 1))];
					double v20 = (rawHeights[k + 1 + 17 * (j + 5 * i)] - v24) * 0.125;
					double v19 = (rawHeights[k + 1 + 17 * (j + 1 + 5 * i)] - v23) * 0.125;
					double v18 = (rawHeights[k + 1 + 17 * (j + 5 * (i + 1))] - v22) * 0.125;
					double v17 = (rawHeights[k + 1 + 17 * (j + 1 + 5 * (i + 1))] - v21) * 0.125;
					
					for(int l = 0; l < 8; l++) {
						double v15 = v24;
						double v14 = v23;
						for(int m = 0; m < 4; m++) {
							double v11 = v15;
							for(int n = 0; n < 4; n++) {
								BlockType block = null;
								
								if(8 * k + l < 64) /* Maybe 64 is sea level */ {
									// We try replace the water and ice with some other thing to serve as a placeholder
									block = GameRegistry.BLOCK_DIRT;
								}
								if(v11 > 0.0) {
									block = GameRegistry.BLOCK_STONE;
								}
								
								chunk.setBlock(m + 4 * i, l + 8 * k, j * 4 + n, block);
								
								v11 += (v14 - v15) * 0.25;
							}
							
							v15 += (v22 - v24) * 0.25;
							v14 += (v21 - v23) * 0.25;
							
						}
						
						v24 += v20;
						v23 += v19;
						v22 += v18;
						v21 += v17;
						
					}
					
					
					
				}
			}
		}
		
		// |<== Build Surfaces ==>|
		/* (float *)this + 7329 */ double[] r6 = noises[3].getRegion(null, 16 * z, 16 * x, 0, 16, 16, 1, 0.03125, 0.03125, 1);
		/* (float *)this + 7585 */ double[] r7 = noises[3].getRegion(null, 16 * z, 109.01, 16 * x, 16, 1, 16, 0.03125, 1.0, 0.03125);
		/* (float *)this + 7841 */ double[] r8 = noises[4].getRegion(null, 16 * z, 16 * x, 0, 16, 16, 1, 0.0625, 0.0625, 0.0625);
		
		for(int i = 0; i < 16; i++) {
			for(int j = 0; j < 16; j++) {
				double v8 = r6[16 * j + i];
				boolean v23 = (v8 + thisChunkRandom.nextDouble() * 0.2) > 0.0;
				double v9 = r7[16 * j + i];
				boolean v22 = (v9 + thisChunkRandom.nextDouble() * 0.2) > 3.0;
				double v10 = r8[16 * j + i] / 3.0 + 3.0;
				int v16 = (int) (v10 + thisChunkRandom.nextDouble() * 0.25);
				int v15 = -1;
				
				BlockType v21 = GameRegistry.BLOCK_GRASS, v20 = GameRegistry.BLOCK_DIRT;
				
				for(int k = 127; k >= 0; k--) {
					int result = thisChunkRandom.nextInt(5);
					BlockType temp;
					if(result >= k) {
						chunk.setBlock(j, k, i, GameRegistry.BLOCK_BEDROCK);
					} else if((temp = chunk.getBlock(j, k, i)) != null) {
						if(temp == GameRegistry.BLOCK_STONE) {
							if(v15 == -1) {
								if(v16 > 0) {
									if(k >= 60 && k <= 80) { // Make 80 change to see if there remains stone
										v21 = GameRegistry.BLOCK_GRASS;
										v20 = GameRegistry.BLOCK_DIRT;
										if(v22) {
											v21 = null;
											// v20 is gravel
											v20 = GameRegistry.BLOCK_GRAVEL;
										}
										if(v23) {
											// v21 and v20 is sand
											v21 = v20 = GameRegistry.BLOCK_SAND;
										}
									} else {
										v21 = null;
										v20 = GameRegistry.BLOCK_STONE;
									}
									if(k < 64 && v21 != null) {
										// v21 is calmWater
										v21 = GameRegistry.BLOCK_DIRT;
									}
									v15 = v16;
									if(k < 63) chunk.setBlock(j, k, i, v20);
									else chunk.setBlock(j, k, i, v21);
								} else if(v15 > 0) {
									v15--;
									chunk.setBlock(j, k, i, v20);
									if(v15 == 0 && v20 == GameRegistry.BLOCK_SAND) {
										v15 = thisChunkRandom.nextInt(4);
										v20 = GameRegistry.BLOCK_DIRT;
									}
								}
							}
						}
					} else {
						v15 = -1;
					}
				}
				
			}
		}
		

	}
	
	private void getHeights(double[] dest, double a3, int a4, double a5, int a6, int a7, int a8) {
		/* this + 8100 */ double[] r1 = noises[5].getRegion(null, a3, a5, a6, a8, 1.121, 1.121, 0.5);
		/* this + 8101 */ double[] r2 = noises[6].getRegion(null, a3, a5, a6, a8, 200.0, 200.0, 0.5);
		/* this + 8097 */ double[] r3 = noises[2].getRegion(null, a3, a4, a5, a6, a7, a8, 684.41 / 80.0, 684.41 / 160.0, 684.41 / 80.0);
		/* this + 8098 */ double[] r4 = noises[0].getRegion(null, a3, a4, a5, a6, a7, a8, 684.41, 684.41, 684.41);
		/* this + 8099 */ double[] r5 = noises[1].getRegion(null, a3, a4, a5, a6, a7, a8, 684.41, 684.41, 684.41);
		
		// Not written?
		double[] temp1 = new double[1024]; // (int)(BiomeSource*)this + 8
		double[] temp2 = new double[1024]; // (int)(BiomeSource*)this + 4
		
		int v30 = 0, v29 = 0;
		for(int i = 0; i < a6; i++) {
			for(int j = 0; j < a8; j++) {
				int v10 = 16 / a6;
				double v11 = temp1[v10 / 2 + v10 * j + 16 * (v10 / 2 + v10 * i)] * temp2[v10 / 2 + v10 * j + 16 * (v10 / 2 + v10 * i)];
				double v25 = (r1[v29] + 256.0) / 512.0 * (1.0 - Math.pow(1.0 - v11, 4));
				if(v25 > 1.0) v25 = 1.0;
				
				double v21 = r2[v29] / 8000.0;
				if(v21 < 0.0) v21 *= -0.3;
				
				double v22 = v21 * 3.0 - 2.0;
				double v24;
				if(v22 >= 0.0) {
					if(v22 > 1.0) v22 = 1.0;
					v24 = v22 / 8.0;
				} else {
					double v23 = v22 / 2.0;
					if(v23 < -1.0) v23 = -1.0;
					v24 = v23 / 1.4 / 2.0;
					v25 = 0.0;
				}
				
				if(v25 < 0.0) v25 = 0.0;
				double v26 = v25 + 0.5;
				v29++;
				
				for(int k = 0; k < a7; k++) {
					double v17 = ((k - ((a7 / 2.0) + (((a7 * v24) / 16.0) * 4.0))) * 12.0) / v26;
					if(v17 < 0.0)
						v17 = v17 * 4.0;
					double v16 = (r3[v30] / 10.0 + 1.0) / 2.0;
					
					double v18;
					if(v16 >= 0.0) {
						if(v16 <= 1.0) {
							double v12 = r4[v30] / 512.0;
							v18 = (r5[v30] / 512.0 - v12) * v16 + v12;
						} else {
							v18 = r5[v30] / 512.0;
						}
					} else {
						v18 = r4[v30] / 512.0;
					}
					double v19 = v18 - v17;
					if(a7 - 4 < k) {
						v19 = ((1.0 - ((k + 4 - a7) / 3.0)) * v19) + (((k + 4 - a7) / 3.0) * -10.0);
					}
					
					dest[v30] = v19;
					v30++;
					
				}
				
			}
		}
		
	}
	
}
