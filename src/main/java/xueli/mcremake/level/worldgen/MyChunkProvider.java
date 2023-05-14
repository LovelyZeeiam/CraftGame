package xueli.mcremake.level.worldgen;

import java.util.concurrent.CompletableFuture;

import xueli.mcremake.client.CraftGameClient;
import xueli.mcremake.core.world.Chunk;
import xueli.mcremake.core.world.ChunkProvider;

public class MyChunkProvider implements ChunkProvider {
	
	private final CraftGameClient ctx;
	
	public MyChunkProvider(long seed, CraftGameClient ctx) {
		this.ctx = ctx;
		
	}
	
	@Override
	public CompletableFuture<Chunk> getChunk(int x, int z) {
		return null;
	}
	
}
