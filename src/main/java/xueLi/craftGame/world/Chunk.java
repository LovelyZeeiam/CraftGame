package xueLi.craftGame.world;

import java.nio.ByteBuffer;

import xueLi.craftGame.block.Tile;

public class Chunk {

	public static final int size_yiwei = 4, height_yiwei = 7;
	static final int size = 1 << size_yiwei, height = 1 << height_yiwei;

	Tile[][][] blockState = new Tile[size][height][size];
	public int[][] heightMap = new int[size][size];

	public boolean needRebuild = false;
	public ByteBuffer blocksVertexBuffer = ByteBuffer.allocate(475136);

	public int chunkX, chunkZ;

	public Chunk(int chunkX, int chunkZ) {
		this.chunkX = chunkX;
		this.chunkZ = chunkZ;
	}

	@WorldGLData
	public void update() {
		if (needRebuild) {
			for (int x = 0; x < size; x++) {
				for (int z = 0; z < size; z++) {
					int height = heightMap[x][z];
					for (int y = 0; y < height; y++) {

					}
				}
			}
		}

	}

	public void setBlock(int x, int y, int z, Tile block) {
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

	public Tile getBlock(int x, int y, int z) {
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
