package xueli.craftgame.world;

import com.flowpowered.nbt.CompoundMap;
import xueli.craftgame.block.BlockBase;
import xueli.game.utils.Light;

public class Chunk {

	protected int chunkX, chunkY, chunkZ;
	protected Dimension dimension;

	public BlockBase[][][] grid = new BlockBase[16][16][16];
	public CompoundMap[][][] tags = new CompoundMap[16][16][16];
	public Light[][][] light = new Light[16][16][16];
	public int[][] heightmap = new int[16][16];

	public boolean somethingHasChanged = true;

	{
		for (int x = 0; x < 16; x++) {
			for (int y = 0; y < 16; y++) {
				for (int z = 0; z < 16; z++) {
					light[x][y][z] = new Light((byte) 15, (byte) 15, (byte) 15, (byte) 15);
				}
			}
		}
	}

	public Chunk(int x, int y, int z, Dimension dimension) {
		this.chunkX = x;
		this.chunkY = y;
		this.chunkZ = z;
		this.dimension = dimension;
		
	}

	public void setBlockTag(int x, int y, int z, CompoundMap tag) {
		tags[x][y][z] = tag;
	}

	public CompoundMap getBlockTag(int x, int y, int z) {
		return tags[x][y][z];
	}

	public void setBlock(int x, int y, int z, BlockBase tile) {
		grid[x][y][z] = tile;

		new WorldUpdater.LightUpdater(this).run();

		if (y > heightmap[x][z] && tile != null) {
			heightmap[x][z] = y;
		}
		if (y == heightmap[x][z] && tile == null) {
			for (int i = heightmap[x][z]; i >= 0; i--) {
				heightmap[x][z] = i;
				if (getBlock(x, i, z) != null)
					break;
			}
		}

		somethingHasChanged = true;

	}

	public BlockBase getBlock(int x, int y, int z) {
		return grid[x][y][z];
	}

	public Light getLight(int x, int y, int z) {
		return this.light[x][y][z];
	}

	public int getChunkX() {
		return chunkX;
	}

	public int getChunkY() {
		return chunkY;
	}

	public int getChunkZ() {
		return chunkZ;
	}

	public Dimension getDimension() {
		return dimension;
	}

}
