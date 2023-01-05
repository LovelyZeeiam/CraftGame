package xueli.mcremake.core.world;

import com.flowpowered.nbt.CompoundMap;

import xueli.mcremake.core.block.BlockType;

class ChunkGrid {

	public final BlockType[][][] grid = new BlockType[Chunk.CHUNK_SIZE][Chunk.CHUNK_SIZE][Chunk.SUB_CHUNK_HEIGHT];
	public final int[][] heightMap = new int[Chunk.CHUNK_SIZE][Chunk.CHUNK_SIZE];
	public final CompoundMap[][][] tagGrid = new CompoundMap[Chunk.CHUNK_SIZE][Chunk.CHUNK_SIZE][Chunk.SUB_CHUNK_HEIGHT];

}
