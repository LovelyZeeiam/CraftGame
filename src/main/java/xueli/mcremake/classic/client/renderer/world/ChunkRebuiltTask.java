package xueli.mcremake.classic.client.renderer.world;

import xueli.mcremake.classic.core.world.Chunk;

public class ChunkRebuiltTask {

	private final Chunk chunk;
	private final ChunkRebuiltManager manager;

	public ChunkRebuiltTask(Chunk chunk, ChunkRebuiltManager manager) {
		this.chunk = chunk;
		this.manager = manager;

	}

	public void run() {


	}

}
