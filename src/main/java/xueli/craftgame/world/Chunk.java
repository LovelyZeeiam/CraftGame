package xueli.craftgame.world;

import com.flowpowered.nbt.CompoundMap;
import com.flowpowered.nbt.CompoundTag;
import com.flowpowered.nbt.StringTag;
import org.lwjgl.util.vector.Vector3i;
import xueli.craftgame.WorldLogic;
import xueli.craftgame.block.BlockParameters;
import xueli.craftgame.block.Tile;
import xueli.craftgame.block.data.BlockFace;
import xueli.craftgame.interfaces.Saveable;
import xueli.craftgame.world.biome.BiomeData;
import xueli.craftgame.world.biome.BiomeResource;
import xueli.gamengine.resource.TextureAtlas;
import xueli.gamengine.utils.Time;
import xueli.gamengine.utils.store.Color;
import xueli.gamengine.utils.store.FloatList;
import xueli.gamengine.utils.vector.Vector2i;

import java.util.HashMap;

public class Chunk implements Saveable {

	public static final int size_yiwei = 4, height_yiwei = 8;
	public static final int size = 1 << size_yiwei, height = 1 << height_yiwei;

	public int chunkX, chunkZ;

	public int[][] heightMap = new int[size][size];
	private ChunkGrid grid = new ChunkGrid();

	public boolean needRebuild = true;
	private World world;
	private FloatList buffer;
	private FloatList bufferForAlphaDraw;
	private int vertCount = 0, vertCountForAlphaDraw = 0;

	private BiomeData biome;
	private long lastUsedTime;

	private Color[][] colorMap;
	private HashMap<Vector3i, BlockParameters> blockParams = new HashMap<>();

	public Chunk(int chunkX, int chunkZ, World world, BiomeData biome) {
		this.chunkX = chunkX;
		this.chunkZ = chunkZ;
		this.world = world;
		this.biome = biome;
		this.colorMap = new Color[size][size];

	}

	public Chunk(int chunkX, int chunkZ, World world, CompoundTag chunkData) {
		this.chunkX = chunkX;
		this.chunkZ = chunkZ;
		this.world = world;
		this.colorMap = new Color[size][size];

		setSaveData(chunkData, world.getWorldLogic());

	}

	@WorldGLData
	public void update(TextureAtlas blockTextureAtlas) {
		if (needRebuild) {
			if (buffer == null)
				buffer = new FloatList(30000);
			if (bufferForAlphaDraw == null)
				bufferForAlphaDraw = new FloatList(5000);

			int offset_x = chunkX << size_yiwei;
			int offset_z = chunkZ << size_yiwei;

			vertCount = 0;
			vertCountForAlphaDraw = 0;
			buffer.clear();
			bufferForAlphaDraw.clear();

			for (int x = 0; x < size; x++) {
				for (int z = 0; z < size; z++) {
					int height = heightMap[x][z];
					for (int y = 0; y <= height; y++) {
						Tile block = grid.getBlock(x, y, z);
						if (block != null) {
							BlockParameters params = block.getParams();
							if (block.getModel().isAlpha(world)) {
								vertCountForAlphaDraw += block.getDrawData(x + offset_x, y, z + offset_z,
										BlockFace.LEFT, blockTextureAtlas, bufferForAlphaDraw, params, world, this);
								vertCountForAlphaDraw += block.getDrawData(x + offset_x, y, z + offset_z,
										BlockFace.RIGHT, blockTextureAtlas, bufferForAlphaDraw, params, world, this);
								vertCountForAlphaDraw += block.getDrawData(x + offset_x, y, z + offset_z,
										BlockFace.FRONT, blockTextureAtlas, bufferForAlphaDraw, params, world, this);
								vertCountForAlphaDraw += block.getDrawData(x + offset_x, y, z + offset_z,
										BlockFace.BACK, blockTextureAtlas, bufferForAlphaDraw, params, world, this);
								vertCountForAlphaDraw += block.getDrawData(x + offset_x, y, z + offset_z,
										BlockFace.BOTTOM, blockTextureAtlas, bufferForAlphaDraw, params, world, this);
								vertCountForAlphaDraw += block.getDrawData(x + offset_x, y, z + offset_z, BlockFace.TOP,
										blockTextureAtlas, bufferForAlphaDraw, params, world, this);

							} else if (!block.getModel().isCompleteBlock(world)) {
								vertCount += block.getDrawData(x + offset_x, y, z + offset_z, BlockFace.LEFT,
										blockTextureAtlas, buffer, params, world, this);
								vertCount += block.getDrawData(x + offset_x, y, z + offset_z, BlockFace.RIGHT,
										blockTextureAtlas, buffer, params, world, this);
								vertCount += block.getDrawData(x + offset_x, y, z + offset_z, BlockFace.FRONT,
										blockTextureAtlas, buffer, params, world, this);
								vertCount += block.getDrawData(x + offset_x, y, z + offset_z, BlockFace.BACK,
										blockTextureAtlas, buffer, params, world, this);
								vertCount += block.getDrawData(x + offset_x, y, z + offset_z, BlockFace.BOTTOM,
										blockTextureAtlas, buffer, params, world, this);
								vertCount += block.getDrawData(x + offset_x, y, z + offset_z, BlockFace.TOP,
										blockTextureAtlas, buffer, params, world, this);

							} else {
								if ((x - 1 < 0) ? (world.getBlock(x + offset_x - 1, y, z + offset_z) == null
										|| world.getBlock(x + offset_x - 1, y, z + offset_z).getModel().isAlpha(world)
										|| !world.getBlock(x + offset_x - 1, y, z + offset_z).getModel()
												.isCompleteBlock(world))
										: (grid.getBlock(x - 1, y, z) == null)
												|| (world.getBlock(x + offset_x - 1, y, z + offset_z).getModel()
														.isAlpha(world))
												|| (!world.getBlock(x + offset_x - 1, y, z + offset_z).getModel()
														.isCompleteBlock(world))) {
									vertCount += block.getDrawData(x + offset_x, y, z + offset_z, BlockFace.LEFT,
											blockTextureAtlas, buffer, params, world, this);
								}
								if ((x + 1 >= size) ? (world.getBlock(x + offset_x + 1, y, z + offset_z) == null
										|| world.getBlock(x + offset_x + 1, y, z + offset_z).getModel().isAlpha(world)
										|| !world.getBlock(x + offset_x + 1, y, z + offset_z).getModel()
												.isCompleteBlock(world))
										: (grid.getBlock(x + 1, y, z) == null)
												|| (world.getBlock(x + offset_x + 1, y, z + offset_z).getModel()
														.isAlpha(world))
												|| (!world.getBlock(x + offset_x + 1, y, z + offset_z).getModel()
														.isCompleteBlock(world))) {
									vertCount += block.getDrawData(x + offset_x, y, z + offset_z, BlockFace.RIGHT,
											blockTextureAtlas, buffer, params, world, this);
								}
								if ((z - 1 < 0) ? (world.getBlock(x + offset_x, y, z + offset_z - 1) == null
										|| world.getBlock(x + offset_x, y, z + offset_z - 1).getModel().isAlpha(world)
										|| !world.getBlock(x + offset_x, y, z + offset_z - 1).getModel()
												.isCompleteBlock(world))
										: (grid.getBlock(x, y, z - 1) == null)
												|| (world.getBlock(x + offset_x, y, z + offset_z - 1).getModel()
														.isAlpha(world))
												|| (!world.getBlock(x + offset_x, y, z + offset_z - 1).getModel()
														.isCompleteBlock(world))) {
									vertCount += block.getDrawData(x + offset_x, y, z + offset_z, BlockFace.FRONT,
											blockTextureAtlas, buffer, params, world, this);
								}
								if ((z + 1 >= size) ? (world.getBlock(x + offset_x, y, z + offset_z + 1) == null
										|| world.getBlock(x + offset_x, y, z + offset_z + 1).getModel().isAlpha(world)
										|| !world.getBlock(x + offset_x, y, z + offset_z + 1).getModel()
												.isCompleteBlock(world))
										: (grid.getBlock(x, y, z + 1) == null)
												|| (world.getBlock(x + offset_x, y, z + offset_z + 1).getModel()
														.isAlpha(world))
												|| (!world.getBlock(x + offset_x, y, z + offset_z + 1).getModel()
														.isCompleteBlock(world))) {
									vertCount += block.getDrawData(x + offset_x, y, z + offset_z, BlockFace.BACK,
											blockTextureAtlas, buffer, params, world, this);
								}
								if ((y - 1 < 0) ? (world.getBlock(x + offset_x, y - 1, z + offset_z) == null
										|| world.getBlock(x + offset_x, y - 1, z + offset_z).getModel().isAlpha(world)
										|| !world.getBlock(x + offset_x, y - 1, z + offset_z).getModel()
												.isCompleteBlock(world))
										: (grid.getBlock(x, y - 1, z) == null)
												|| (world.getBlock(x + offset_x, y - 1, z + offset_z).getModel()
														.isAlpha(world))
												|| (!world.getBlock(x + offset_x, y - 1, z + offset_z).getModel()
														.isCompleteBlock(world))) {
									vertCount += block.getDrawData(x + offset_x, y, z + offset_z, BlockFace.BOTTOM,
											blockTextureAtlas, buffer, params, world, this);
								}
								if ((y + 1 >= Chunk.height) ? (world.getBlock(x + offset_x, y + 1, z + offset_z) == null
										|| world.getBlock(x + offset_x, y + 1, z + offset_z).getModel().isAlpha(world)
										|| !world.getBlock(x + offset_x, y + 1, z + offset_z).getModel()
												.isCompleteBlock(world))
										: (grid.getBlock(x, y + 1, z) == null)
												|| (world.getBlock(x + offset_x, y + 1, z + offset_z).getModel()
														.isAlpha(world))
												|| (!world.getBlock(x + offset_x, y + 1, z + offset_z).getModel()
														.isCompleteBlock(world))) {
									vertCount += block.getDrawData(x + offset_x, y, z + offset_z, BlockFace.TOP,
											blockTextureAtlas, buffer, params, world, this);
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
		for (int x = 0; x < size; x++)
			for (int z = 0; z < size; z++) {
				int height = this.heightMap[x][z];
				Tile tile = this.getBlock(x, height, z);
				if (tile != null)
					this.colorMap[x][z] = tile.getData().getMapColor();

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

	@Override
	public CompoundTag getSaveData(WorldLogic logic) {
		CompoundTag tag = grid.getSaveData(logic);
		tag.getValue().put(new StringTag("biome", biome.getNamespace()));
		return tag;
	}

	@Override
	public void setSaveData(CompoundTag data, WorldLogic logic) {
		CompoundMap map = data.getValue();
		String biomeNamespace = ((StringTag) map.get("biome")).getValue();
		this.biome = BiomeResource.biomes.get(biomeNamespace);
		grid.setSaveData(data, logic);

		// 初始化顶点缓存
		if (buffer == null)
			buffer = new FloatList(300000);
		if (bufferForAlphaDraw == null)
			bufferForAlphaDraw = new FloatList(50000);

		// 计算heightmap
		for (int x = 0; x < Chunk.size; x++) {
			for (int z = 0; z < Chunk.size; z++) {
				for (int y = Chunk.height - 1; y >= 0; y--) {
					if (hasBlock(new Vector3i(x, y, z))) {
						heightMap[x][z] = y;
						break;
					}
				}
			}
		}

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
		grid.setBlock(x, y, z, block);
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
		return grid.getBlock(x, y, z);
	}

	public boolean hasBlock(Vector3i pos) {
		if (pos.getX() < 0 || pos.getX() >= size || pos.getY() < 0 || pos.getY() >= height || pos.getZ() < 0
				|| pos.getZ() >= size)
			return false;
		return grid.getBlock(pos.getX(), pos.getY(), pos.getZ()) != null;
	}

	public void close() {
		if (buffer != null)
			buffer.postDispose();
		if (bufferForAlphaDraw != null)
			bufferForAlphaDraw.postDispose();

	}

}
