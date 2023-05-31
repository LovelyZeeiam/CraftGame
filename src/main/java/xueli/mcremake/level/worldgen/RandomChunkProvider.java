package xueli.mcremake.level.worldgen;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

import xueli.mcremake.core.world.Chunk;
import xueli.mcremake.core.world.ChunkProvider;

public class RandomChunkProvider implements ChunkProvider {

	public RandomChunkProvider(long seed) {
		this.init(seed);

	}

	private void init(long seed) {

	}

	@Override
	public CompletableFuture<Chunk> getChunk(int x, int z, ExecutorService executor) {
		return CompletableFuture.supplyAsync(() -> {
			Chunk chunk = new Chunk();

			// TODO

			chunk.recalcHeightMap();
			return chunk;
		}, executor);
	}

}
