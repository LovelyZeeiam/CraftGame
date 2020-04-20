package xueLi.craftGame.world;

import java.util.HashSet;
import java.util.Set;

import xueLi.craftGame.block.Block;
import xueLi.craftGame.entity.Entity;
import xueLi.craftGame.utils.BlockPos;

public class Chunk {

	public static final int size = 16, height = 128;
	Block[][][] blockState = new Block[size][height][size];
	public int[][] heightMap = new int[size][size];

	public int chunkX, chunkZ;

	public Set<Entity> entities = new HashSet<Entity>();

	public Chunk(int chunkX, int chunkZ) {
		this.chunkX = chunkX;
		this.chunkZ = chunkZ;
	}

	public void update() {

	}

	public void setBlock(int x, int y, int z, Block block) {
		if (x < 0 || x >= size || y < 0 || y >= height || z < 0 || z >= size)
			return;
		blockState[x][y][z] = block;

		if (y > heightMap[x][z]) {
			if (block == null) {
				for (int yy = y;; y--) {
					if (this.getBlock(x, yy, z) != null) {
						heightMap[x][z] = yy;
						break;
					}
				}
			} else
				heightMap[x][z] = y;
		}
	}

	public Block getBlock(BlockPos pos) {
		if (pos.getX() < 0 || pos.getX() >= size || pos.getY() < 0 || pos.getY() >= height || pos.getZ() < 0
				|| pos.getZ() >= size)
			return null;
		return blockState[pos.getX()][pos.getY()][pos.getZ()];
	}

	public Block getBlock(int x, int y, int z) {
		if (x < 0 || x >= size || y < 0 || y >= height || z < 0 || z >= size)
			return null;
		return blockState[x][y][z];
	}

	public boolean hasBlock(BlockPos pos) {
		if (pos.getX() < 0 || pos.getX() >= size || pos.getY() < 0 || pos.getY() >= height || pos.getZ() < 0
				|| pos.getZ() >= size)
			return false;
		return blockState[pos.getX()][pos.getY()][pos.getZ()] != null;
	}

}
