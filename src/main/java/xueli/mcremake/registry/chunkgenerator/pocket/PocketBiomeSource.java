package xueli.mcremake.registry.chunkgenerator.pocket;

import java.util.Random;

import xueli.mcremake.core.world.biome.BiomeType;
import xueli.mcremake.registry.GameRegistry;
import xueli.mcremake.registry.PocketNativeType;

/**
 * Stored in (_DWORD)(Dimension*)this + 8)
 */
@PocketNativeType("BiomeSource")
public class PocketBiomeSource {
	
	private static final BiomeType[] BIOME_MAP = new BiomeType[4096];
	
	static {
		for(int i = 0; i <= 63; i++) {
			for(int j = 0; j <= 63; j++) {
				BIOME_MAP[(j << 6) + i] = calcBiome(i / 63.0f, j / 63.0f);
			}
		}
	}
	
	@PocketNativeType("Biome::_getBiome")
	private static BiomeType calcBiome(float i, float j) {
		float v5 = i * j;
		if(i < 0.1) return GameRegistry.BIOME_TUNDRA;
		
		BiomeType result;
		// Yes, this is how it's originally written. Or optimized by the compiler?
		// Anyway, if there is time I will optimize this by myself
		if(v5 > 0.2)
			if(v5 <= 0.5 || i >= 0.7) {
				if(i >= 0.5) {
					if(i >= 0.97)
						if(v5 >= 0.45) result = v5 >= 0.9 ? GameRegistry.BIOME_RAIN_FOREST : GameRegistry.BIOME_SEASONAL_FOREST;
						else result = GameRegistry.BIOME_PLAINS;
					else if(v5 >= 0.35) result = GameRegistry.BIOME_FOREST;
					else result = GameRegistry.BIOME_SHRUBLAND;
				} else result = GameRegistry.BIOME_TAIGA_FOREST;
			} else result = GameRegistry.BIOME_SWAMPLAND;
		else if(i >= 0.5) result = i >= 0.95 ? GameRegistry.BIOME_DESERT : GameRegistry.BIOME_SAVANNA;
		else result = GameRegistry.BIOME_TUNDRA;
		return result;
	}
	
//	private final long seed;
	
	// this offset(char)[4-12] seems like a temporary cache. Seems like used by RandomLevelSource::getHeights
	// this offset(char)[12-36] seems staying at 0
	
	@PocketNativeType("(char*)this + 48") private final Random random1;
	@PocketNativeType("(char*)this + 2552") private final Random random2;
	@PocketNativeType("(char*)this + 5056") private final Random random3;
	
	@PocketNativeType("(char*)this + 36") private final PocketPerlinNoise noise1;
	@PocketNativeType("(char*)this + 40") private final PocketPerlinNoise noise2;
	@PocketNativeType("(char*)this + 44") private final PocketPerlinNoise noise3;
	
	public PocketBiomeSource() {
		this(System.currentTimeMillis());
	}
	
	public PocketBiomeSource(long seed) {
//		this.seed = seed;
		random1 = new Random(9871 * seed);
		random2 = new Random(39811 * seed);
		random3 = new Random(543321 * seed);
		
		this.noise1 = new PocketPerlinNoise(random1, 4);
		this.noise2 = new PocketPerlinNoise(random2, 4);
		this.noise3 = new PocketPerlinNoise(random3, 2);
		
	}
	
	public BiomeType[] getBiomeBlock(BiomeType[] dest, int a3, int a4, int a5, int a6) {
		if(dest == null) dest = new BiomeType[a5 * a6];
		
		double[] of1 = new double[256];
		noise1.getRegion(of1, a3, a4, a5, a5, 0.025, 0.025, 0.25);
		
		double[] of2 = new double[256];
		noise2.getRegion(of2, a3, a4, a5, a5, 0.05, 0.05, 0.3333);
		
		double[] of3 = new double[256];
		noise3.getRegion(of3, a3, a4, a5, a5, 0.05, 0.05, 0.588);
		
		int v17 = 0;
		for(int i = 0; i < a5; i++) {
			for(int j = 0; j < a6; j++) {
//				int v10 = j;
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
				int v14_int = (int) (v14 * 63.0);
				dest[j + i * a6] = BIOME_MAP[v14_int + (v13_int << 6)];
				
				v17++;
				
			}
		}
		
		return dest; 
	}
	
	
	
}
