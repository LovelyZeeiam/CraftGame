package xueli.mcremake.registry.chunkgenerator;

import xueli.mcremake.core.world.Chunk;
import xueli.mcremake.core.world.biome.BiomeType;

public class PocketEditionChunkProvider {
	
	private final PocketBiomeSource biomeSource;
	
	public PocketEditionChunkProvider() {
		this(System.currentTimeMillis());
	}
	
	public PocketEditionChunkProvider(long seed) {
		this.biomeSource = new PocketBiomeSource(seed);
	}
	
	public void genChunk(int x, int z, Chunk chunk) {
		BiomeType[] buffer = new BiomeType[4096];
		this.biomeSource.getBiomeBlock(buffer, 16 * x, 16 * z, 16, 16);
		
//		System.out.println("===== " + x + ", " + z + " ====");
		for(int i = 0; i < 16; i++) {
			for(int j = 0; j < 16; j++) {
//				System.out.print(String.format("%s ", Strings.padRight(buffer[j + i * 16].name(), 8, ' ')));
			}
//			System.out.println();
		}
		
		
	}
	
}
