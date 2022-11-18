package xueli.mcremake.classic.world;

import com.flowpowered.nbt.CompoundMap;
import xueli.mcremake.classic.block.BlockType;

class ChunkGrid {

	public final BlockType[][][] grid = new BlockType[Chunk.CHUNK_SIZE][Chunk.CHUNK_SIZE][Chunk.SUB_CHUNK_HEIGHT];
	public final CompoundMap[][][] tagGrid = new CompoundMap[Chunk.CHUNK_SIZE][Chunk.CHUNK_SIZE][Chunk.SUB_CHUNK_HEIGHT];

}
