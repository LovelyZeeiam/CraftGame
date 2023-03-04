package xueli.mcremake.core.world.pocket;

import xueli.mcremake.core.world.Chunk;

public class PocketEditionChunkProvider {
	
	private final PocketBiomeSource biomeSource;
	
	public PocketEditionChunkProvider() {
		this(System.currentTimeMillis());
	}
	
	public PocketEditionChunkProvider(long seed) {
		this.biomeSource = new PocketBiomeSource(seed);
	}
	
	public void genChunk(int x, int z, Chunk chunk) {
		this.biomeSource.getBiomeBlock(16 * x, 16 * z, 16, 16);
		
	}
	
}
