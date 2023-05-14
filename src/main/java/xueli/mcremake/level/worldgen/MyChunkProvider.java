package xueli.mcremake.level.worldgen;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

import xueli.mcremake.core.world.Chunk;
import xueli.mcremake.core.world.ChunkProvider;
import xueli.mcremake.registry.GameRegistry;

public class MyChunkProvider implements ChunkProvider {
	
	public MyChunkProvider(long seed) {
		
	}
	
	@Override
	public CompletableFuture<Chunk> getChunk(int x, int z, ExecutorService executor) {
		return CompletableFuture.supplyAsync(() -> {
			Chunk chunk = new Chunk();
			for (int l = 0; l < Chunk.CHUNK_SIZE; l++) {
				for (int m = 0; m < Chunk.CHUNK_SIZE; m++) {
					chunk.setBlock(l, 7, m, GameRegistry.BLOCK_GRASS);
					for (int k = 6; k > 3; k--) {
						chunk.setBlock(l, k, m, GameRegistry.BLOCK_DIRT);
					}
					for (int k = 3; k >= 0; k--) {
						chunk.setBlock(l, k, m, GameRegistry.BLOCK_STONE);
					}
				}
			}
			return chunk;
		}, executor);
	}
	
}
