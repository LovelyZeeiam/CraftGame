package xueLi.craftGame.world;

import xueLi.craftGame.block.BlockFace;
import xueLi.craftGame.block.Tile;
import xueLi.gamengine.resource.TextureAtlas;
import xueLi.gamengine.utils.FloatList;

public class Chunk {

	public static final int size_yiwei = 4, height_yiwei = 7;
	static final int size = 1 << size_yiwei, height = 1 << height_yiwei;

	Tile[][][] blockState = new Tile[size][height][size];
	public int[][] heightMap = new int[size][size];

	public boolean needRebuild = true;
	private FloatList buffer = new FloatList(500000);
	private int vertCount = 0;

	public int chunkX, chunkZ;

	public Chunk(int chunkX, int chunkZ) {
		this.chunkX = chunkX;
		this.chunkZ = chunkZ;
	}

	@WorldGLData
	public void update(TextureAtlas blockTextureAtlas) {
		if (needRebuild) {
			int offset_x = chunkX << size_yiwei;
			int offset_z = chunkZ << size_yiwei;

			vertCount = 0;
			buffer.clear();
			for (int x = 0; x < size; x++) {
				for (int z = 0; z < size; z++) {
					int height = heightMap[x][z];
					for (int y = 0; y <= height; y++) {
						Tile block = blockState[x][y][z];
						if (block != null) {
							if (x - 1 < 0 || blockState[x - 1][y][z] == null) {
								vertCount += block.getDrawData(x + offset_x, y, z + offset_z, BlockFace.LEFT,
										blockTextureAtlas, buffer);
							}
							if (x + 1 >= size || blockState[x + 1][y][z] == null) {
								vertCount += block.getDrawData(x + offset_x, y, z + offset_z, BlockFace.RIGHT,
										blockTextureAtlas, buffer);
							}
							if (z - 1 < 0 || blockState[x][y][z - 1] == null) {
								vertCount += block.getDrawData(x + offset_x, y, z + offset_z, BlockFace.FRONT,
										blockTextureAtlas, buffer);
							}
							if (z + 1 >= size || blockState[x][y][z + 1] == null) {
								vertCount += block.getDrawData(x + offset_x, y, z + offset_z, BlockFace.BACK,
										blockTextureAtlas, buffer);
							}
							if (y - 1 < 0 || blockState[x][y - 1][z] == null) {
								vertCount += block.getDrawData(x + offset_x, y, z + offset_z, BlockFace.BOTTOM,
										blockTextureAtlas, buffer);
							}
							if (y + 1 >= Chunk.height || blockState[x][y + 1][z] == null) {
								vertCount += block.getDrawData(x + offset_x, y, z + offset_z, BlockFace.TOP,
										blockTextureAtlas, buffer);
							}
						}
					}
				}
			}
			needRebuild = false;
		}

	}

	public int getVertCount() {
		return vertCount;
	}

	public FloatList getDrawBuffer() {
		return buffer;
	}

	public void setBlock(int x, int y, int z, Tile block) {
		if (x < 0 || x >= size || y < 0 || y >= height || z < 0 || z >= size)
			return;
		blockState[x][y][z] = block;
		needRebuild = true;

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
