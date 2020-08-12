package xueLi.craftGame.world;

import xueLi.craftGame.block.Tile;

public class Chunk {

	public static final int size = 16, height = 128;
	Tile[][][] blockState = new Tile[size][height][size];
	public int[][] heightMap = new int[size][size];

	public int chunkX, chunkZ;

	public Chunk(int chunkX, int chunkZ) {
		this.chunkX = chunkX;
		this.chunkZ = chunkZ;
	}

	public void update() {

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

}
