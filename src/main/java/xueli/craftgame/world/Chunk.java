package xueli.craftgame.world;

import java.util.HashMap;

import xueli.craftgame.block.BlockFace;
import xueli.craftgame.block.BlockParameters;
import xueli.craftgame.block.Tile;
import xueli.craftgame.world.biome.BiomeData;
import xueli.gamengine.resource.TextureAtlas;
import xueli.gamengine.utils.Color;
import xueli.gamengine.utils.FloatList;
import xueli.gamengine.utils.Time;
import xueli.gamengine.utils.vector.Vector2i;

public class Chunk {

	public static final int size_yiwei = 4, height_yiwei = 8;
	public static final int size = 1 << size_yiwei, height = 1 << height_yiwei;
	
	public int chunkX, chunkZ;
	
	public int[][] heightMap = new int[size][size];
	Tile[][][] blockState = new Tile[size][height][size];
	
	public boolean needRebuild = true;
	private World world;
	private FloatList buffer = new FloatList(500000);
	private FloatList bufferForAlphaDraw = new FloatList(500000);
	private int vertCount = 0, vertCountForAlphaDraw = 0;

	private BiomeData biome;
	private long lastUsedTime;
	
	private Color[][] colorMap;
	private HashMap<BlockPos, BlockParameters> blockParams = new HashMap<>();

	public Chunk(int chunkX, int chunkZ, World world, BiomeData biome) {
		this.chunkX = chunkX;
		this.chunkZ = chunkZ;
		this.world = world;
		this.biome = biome;
		this.colorMap = new Color[size][size];

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
					
					// TODO: generate map
					
					for (int y = 0; y <= height; y++) {
						Tile block = blockState[x][y][z];
						if (block != null) {
							FloatList targetBuffer = buffer;

							if (block.data.isAlpha()) {
								vertCount += block.getDrawData(x + offset_x, y, z + offset_z, BlockFace.LEFT,
										blockTextureAtlas, targetBuffer, world, this);
								vertCount += block.getDrawData(x + offset_x, y, z + offset_z, BlockFace.RIGHT,
										blockTextureAtlas, targetBuffer, world, this);
								vertCount += block.getDrawData(x + offset_x, y, z + offset_z, BlockFace.FRONT,
										blockTextureAtlas, targetBuffer, world, this);
								vertCount += block.getDrawData(x + offset_x, y, z + offset_z, BlockFace.BACK,
										blockTextureAtlas, targetBuffer, world, this);
								vertCount += block.getDrawData(x + offset_x, y, z + offset_z, BlockFace.BOTTOM,
										blockTextureAtlas, targetBuffer, world, this);
								vertCount += block.getDrawData(x + offset_x, y, z + offset_z, BlockFace.TOP,
										blockTextureAtlas, targetBuffer, world, this);
							} else {
								if ((x - 1 < 0)
										? (world.getBlock(x + offset_x - 1, y, z + offset_z) == null
												|| world.getBlock(x + offset_x - 1, y, z + offset_z).data.isAlpha())
										: (blockState[x - 1][y][z] == null)
												|| (world.getBlock(x + offset_x - 1, y, z + offset_z).data.isAlpha())) {
									vertCount += block.getDrawData(x + offset_x, y, z + offset_z, BlockFace.LEFT,
											blockTextureAtlas, targetBuffer, world, this);
								}
								if ((x + 1 >= size)
										? (world.getBlock(x + offset_x + 1, y, z + offset_z) == null
												|| world.getBlock(x + offset_x + 1, y, z + offset_z).data.isAlpha())
										: (blockState[x + 1][y][z] == null)
												|| (world.getBlock(x + offset_x + 1, y, z + offset_z).data.isAlpha())) {
									vertCount += block.getDrawData(x + offset_x, y, z + offset_z, BlockFace.RIGHT,
											blockTextureAtlas, targetBuffer, world, this);
								}
								if ((z - 1 < 0)
										? (world.getBlock(x + offset_x, y, z + offset_z - 1) == null
												|| world.getBlock(x + offset_x, y, z + offset_z - 1).data.isAlpha())
										: (blockState[x][y][z - 1] == null)
												|| (world.getBlock(x + offset_x, y, z + offset_z - 1).data.isAlpha())) {
									vertCount += block.getDrawData(x + offset_x, y, z + offset_z, BlockFace.FRONT,
											blockTextureAtlas, targetBuffer, world, this);
								}
								if ((z + 1 >= size)
										? (world.getBlock(x + offset_x, y, z + offset_z + 1) == null
												|| world.getBlock(x + offset_x, y, z + offset_z + 1).data.isAlpha())
										: (blockState[x][y][z + 1] == null)
												|| (world.getBlock(x + offset_x, y, z + offset_z + 1).data.isAlpha())) {
									vertCount += block.getDrawData(x + offset_x, y, z + offset_z, BlockFace.BACK,
											blockTextureAtlas, targetBuffer, world, this);
								}
								if ((y - 1 < 0)
										? (world.getBlock(x + offset_x, y - 1, z + offset_z) == null
												|| world.getBlock(x + offset_x, y - 1, z + offset_z).data.isAlpha())
										: (blockState[x][y - 1][z] == null)
												|| (world.getBlock(x + offset_x, y - 1, z + offset_z).data.isAlpha())) {
									vertCount += block.getDrawData(x + offset_x, y, z + offset_z, BlockFace.BOTTOM,
											blockTextureAtlas, targetBuffer, world, this);
								}
								if ((y + 1 >= Chunk.height)
										? (world.getBlock(x + offset_x, y + 1, z + offset_z) == null
												|| world.getBlock(x + offset_x, y + 1, z + offset_z).data.isAlpha())
										: (blockState[x][y + 1][z] == null)
												|| (world.getBlock(x + offset_x, y + 1, z + offset_z).data.isAlpha())) {
									vertCount += block.getDrawData(x + offset_x, y, z + offset_z, BlockFace.TOP,
											blockTextureAtlas, targetBuffer, world, this);
								}
							}

						}

					}
				}
			}
		}
		needRebuild = false;
	}
	
	public void generateMap() {
		for(int x = 0; x < size;x ++)
			for(int z = 0;z < size; z++) {
				int height = this.heightMap[x][z];
				Tile tile = this.getBlock(x, height, z);
				if(tile != null)
					this.colorMap[x][z] = tile.data.getMapColor();
				
			}
		
	}

	public Color[][] getColorMap() {
		return colorMap;
	}

	public int getVertCount() {
		return vertCount;
	}

	public int getVertCountForAlphaDraw() {
		return vertCountForAlphaDraw;
	}

	public FloatList getDrawBuffer() {
		lastUsedTime = Time.thisTime;
		return buffer;
	}

	public FloatList getBufferForAlphaDraw() {
		lastUsedTime = Time.thisTime;
		return bufferForAlphaDraw;
	}

	public BiomeData getBiome() {
		return biome;
	}

	public long getLastUsedTime() {
		return lastUsedTime;
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

		// 通知周围的区块也更新
		{
			Chunk chunk = world.getChunk(chunkX + 1, chunkZ);
			if (chunk != null)
				chunk.needRebuild = true;
		}
		{
			Chunk chunk = world.getChunk(chunkX - 1, chunkZ);
			if (chunk != null)
				chunk.needRebuild = true;
		}
		{
			Chunk chunk = world.getChunk(chunkX, chunkZ - 1);
			if (chunk != null)
				chunk.needRebuild = true;
		}
		{
			Chunk chunk = world.getChunk(chunkX, chunkZ + 1);
			if (chunk != null)
				chunk.needRebuild = true;
		}

	}

	public Vector2i getChunkCenter2DPosition() {
		return new Vector2i(chunkX * Chunk.size + Chunk.size / 2, chunkZ * Chunk.size + Chunk.size / 2);
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
