package xueli.mcremake.core.world;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

public interface ChunkProvider {

	public CompletableFuture<Chunk> getChunk(int x, int z, ExecutorService executor);

}
