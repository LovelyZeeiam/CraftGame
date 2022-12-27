package xueli.mcremake.classic.core.world;

import java.util.concurrent.CompletableFuture;

public interface ChunkProvider {

	public CompletableFuture<Chunk> getChunk(int x, int z);

}
