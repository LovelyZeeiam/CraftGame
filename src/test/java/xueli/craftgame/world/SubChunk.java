package xueli.craftgame.world;

import com.flowpowered.nbt.CompoundMap;

import xueli.craftgame.block.BlockType;

public class SubChunk {

	private World world;
	private int chunkX, chunkZ;

	public BlockType[][][] grid = new BlockType[16][256][16];
	public CompoundMap[][][] tags = new CompoundMap[16][16][16];
	public int[][] heightmap = new int[16][16];

	public SubChunk(int x, int z, World world) {
		this.world = world;
		this.chunkX = x;
		this.chunkZ = z;

	}

	public int getChunkX() {
		return chunkX;
	}

	public int getChunkZ() {
		return chunkZ;
	}

}
