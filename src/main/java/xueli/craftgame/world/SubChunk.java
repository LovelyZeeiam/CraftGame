package xueli.craftgame.world;

import com.flowpowered.nbt.CompoundMap;

import xueli.craftgame.block.BlockType;

public class SubChunk {

	public static final int MAX_HEIGHT = 256;

	private World world;
	private int chunkX, chunkZ;

	public BlockType[][][] grid = new BlockType[16][MAX_HEIGHT][16];
	public CompoundMap[][][] tags = new CompoundMap[16][16][16];
	public int[][] heightmap = new int[16][16];

	// TODO: LIST CONTAINING ALL TICKABLE BLOCKS

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

	public World getWorld() {
		return world;
	}

}
