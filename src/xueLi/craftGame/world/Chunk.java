package xueLi.craftGame.world;

import java.nio.FloatBuffer;

import xueLi.craftGame.block.Block;
import xueLi.craftGame.utils.BlockPos;
import xueLi.craftGame.utils.Vector;

public class Chunk {

	public static final int size = 16, height = 128;
	private Block[][][] blockState = new Block[size][height][size];
	public int[][] heightMap = new int[size][size];

	private int chunkX, chunkZ;

	public Chunk(int chunkX, int chunkZ) {
		this.chunkX = chunkX;
		this.chunkZ = chunkZ;
		for (int x = 0; x < size; x++) {
			for (int z = 0; z < size; z++) {
				for (int y = 0; y < 4; y++) {
					blockState[x][y][z] = Block.blockDefault.get(1);
				}
				blockState[x][4][z] = Block.blockDefault.get(2);
				heightMap[x][z] = 4;
			}
		}

	}

	public void update() {

	}

	public void setBlock(int x, int y, int z, int id) {
		if (x < 0 || x >= size || y < 0 || y >= height || z < 0 || z >= size)
			return;
		if (id == 0) {
			blockState[x][y][z] = null;
			if (y > heightMap[x][z]) {
				for (int yy = y;; y--) {
					if (this.getBlock(x, yy, z) != null) {
						heightMap[x][z] = yy;
						break;
					}
				}
			}
			return;
		}
		blockState[x][y][z] = Block.blockDefault.get(id);

		if (y > heightMap[x][z])
			heightMap[x][z] = y;

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

	public void setBlock(BlockPos pos, int id) {
		setBlock(pos.getX(), pos.getY(), pos.getZ(), id);
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

	public int draw(FloatBuffer buffer, Vector player_pos) {
		int vertCount = 0;
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < height; y++) {
				for (int z = 0; z < size; z++) {
					Block block = blockState[x][y][z];
					if (block == null)
						continue;
					if (x - 1 > 0 && blockState[x - 1][y][z] == null) {
						block.method.getDrawData(buffer, x + chunkX * size, y, z + chunkZ * size, 3);
						vertCount += 6;
					}
					if (x + 1 >= size || blockState[x + 1][y][z] == null) {
						block.method.getDrawData(buffer, x + chunkX * size, y, z + chunkZ * size, 1);
						vertCount += 6;
					}
					if (z - 1 > 0 && blockState[x][y][z - 1] == null) {
						block.method.getDrawData(buffer, x + chunkX * size, y, z + chunkZ * size, 0);
						vertCount += 6;
					}
					if (z + 1 >= size || blockState[x][y][z + 1] == null) {
						block.method.getDrawData(buffer, x + chunkX * size, y, z + chunkZ * size, 2);
						vertCount += 6;
					}
					if (y - 1 < 0 || blockState[x][y - 1][z] == null) {
						block.method.getDrawData(buffer, x + chunkX * size, y, z + chunkZ * size, 5);
						vertCount += 6;
					}
					if (y + 1 >= height || blockState[x][y + 1][z] == null) {
						block.method.getDrawData(buffer, x + chunkX * size, y, z + chunkZ * size, 4);
						vertCount += 6;
					}

				}
			}
		}
		return vertCount;
	}

}
