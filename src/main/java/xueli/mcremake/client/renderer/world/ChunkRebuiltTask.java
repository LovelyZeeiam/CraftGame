package xueli.mcremake.client.renderer.world;

import com.flowpowered.nbt.CompoundMap;

import xueli.mcremake.core.block.BlockType;
import xueli.mcremake.core.world.Chunk;

public class ChunkRebuiltTask {

	private final int x, z;
	private final Chunk chunk;
	private final ChunkRenderBuildManager manager;

	public ChunkRebuiltTask(int x, int z, Chunk chunk, ChunkRenderBuildManager manager) {
		this.x = x;
		this.z = z;
		this.chunk = chunk;
		this.manager = manager;

	}

	public void run() {
		for (int i = 0; i < Chunk.CHUNK_SIZE; i++) {
			for (int j = 0; j < Chunk.CHUNK_SIZE; j++) {
				for (int k = 0; k < Chunk.SUB_CHUNK_LAYER_COUNT; k++) {
					int maxHeight = chunk.getMaxHeight(i, j, k);
					for (int l = 0; l <= maxHeight; l++) {
						int realHeight = k * Chunk.SUB_CHUNK_HEIGHT + l;
						BlockType block = chunk.getBlock(i, realHeight, j);
						if(block == null) continue;
						CompoundMap tag = chunk.getBlockTag(i, realHeight, j);
						BlockVertexGatherer renderer = block.renderer();
						if(renderer == null) continue;
						renderer.render(this.x * Chunk.CHUNK_SIZE + i, realHeight, this.z * Chunk.CHUNK_SIZE + j, tag, chunk.getWorld(), manager);
					}
				}
			}
		}

	}

}
