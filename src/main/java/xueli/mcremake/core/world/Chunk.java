package xueli.mcremake.core.world;

import java.util.HashMap;
import java.util.function.Consumer;

import org.lwjgl.utils.vector.Vector2i;

import com.flowpowered.nbt.CompoundMap;
import com.flowpowered.nbt.Tag;

import xueli.mcremake.core.block.BlockType;

public class Chunk implements WorldAccessible {

	public static final int CHUNK_SIZE = 16;
	public static final int SUB_CHUNK_HEIGHT = 16;
	public static final int SUB_CHUNK_LAYER_COUNT = 8;
	public static final int CHUNK_HEIGHT = SUB_CHUNK_HEIGHT * SUB_CHUNK_LAYER_COUNT;

	final ChunkGrid[] grids = new ChunkGrid[SUB_CHUNK_LAYER_COUNT];

	private final HashMap<String, Integer> techniqueTags = new HashMap<>();
	private final CompoundMap gamingChunkTag = new CompoundMap();

	public Chunk() {
		for (int i = 0; i < SUB_CHUNK_LAYER_COUNT; i++) {
			grids[i] = new ChunkGrid();
		}

	}

	public WorldAccessible createAccessible() {
		return this;
	}

	@Override
	public BlockType getBlock(int x, int y, int z) {
		if (y < 0 || y >= Chunk.CHUNK_HEIGHT)
			return null;
		int ySub = y / SUB_CHUNK_HEIGHT;
//		System.out.println(ySub + ", " + y + ", " + x + ", " + z);
		return grids[ySub].grid[x][z][y % SUB_CHUNK_HEIGHT];
	}

	@Override
	public CompoundMap getBlockTag(int x, int y, int z) {
		if (y < 0 || y >= Chunk.CHUNK_HEIGHT)
			return null;
		int ySub = y / SUB_CHUNK_HEIGHT;
		return grids[ySub].tagGrid[x][z][y % SUB_CHUNK_HEIGHT];
	}

	@Override
	public void setBlock(int x, int y, int z, BlockType block) {
		if (y < 0 || y >= Chunk.CHUNK_HEIGHT)
			return;
		int ySub = y / SUB_CHUNK_HEIGHT;
		int yInSub = y % SUB_CHUNK_HEIGHT;
		ChunkGrid grid = grids[ySub];
		grid.grid[x][z][yInSub] = block;

		if (block == null) {
			grid.tagGrid[x][z][yInSub] = null;
			if (y == grid.heightMap[x][z]) {
				int i = --grid.heightMap[x][z];
				for (; i >= 0 && grid.grid[x][z][i] == null; i = --grid.heightMap[x][z])
					;
			}
		} else {
			grid.heightMap[x][z] = Math.max(grid.heightMap[x][z], yInSub);
		}

	}

	public void setBlockImmediate(int x, int y, int z, BlockType block) {
		int ySub = y / SUB_CHUNK_HEIGHT;
		int yInSub = y % SUB_CHUNK_HEIGHT;
		ChunkGrid grid = grids[ySub];
		grid.grid[x][z][yInSub] = block;
	}

	public void recalcHeightMap() {
		for (int i = 0; i < SUB_CHUNK_LAYER_COUNT; i++) {
			ChunkGrid grid = grids[i];
			for (int x = 0; x < CHUNK_SIZE; x++) {
				for (int z = 0; z < CHUNK_SIZE; z++) {
					int h = grid.heightMap[x][z] = SUB_CHUNK_HEIGHT - 1;
					for (; h >= 0 && grid.grid[x][z][h] == null; h = --grid.heightMap[x][z])
						;
				}
			}
		}
	}

	@Override
	public void modifyBlockTag(int x, int y, int z, Consumer<CompoundMap> c) {
		if (y < 0 || y >= Chunk.CHUNK_HEIGHT)
			return;
		int ySub = y / SUB_CHUNK_HEIGHT;
		CompoundMap map = grids[ySub].tagGrid[x][z][y % SUB_CHUNK_HEIGHT];
		if (map == null) {
			map = grids[ySub].tagGrid[x][z][y % SUB_CHUNK_HEIGHT] = new CompoundMap();
		}
		c.accept(map);
	}

	public int getMaxHeight(int x, int z, int ySub) {
		return grids[ySub].heightMap[x][z];
	}

	public void addTag(Tag<?> tag) {
		this.gamingChunkTag.put(tag);
	}

	public Tag<?> getTag(String key) {
		return this.gamingChunkTag.get(key);
	}

	void setTechniqueTags(String key, int val) {
		this.techniqueTags.put(key, val);
	}

	int getTechniqueTags(String key) {
		return this.techniqueTags.get(key);
	}

	public static Vector2i toChunkPos(int blockX, int blockZ) {
		return new Vector2i(blockX >> 4, blockZ >> 4);
	}

	public static Vector2i toChunkPos(int blockX, int blockZ, Vector2i inChunkPosDist) {
		Vector2i chunkPos = toChunkPos(blockX, blockZ);
		inChunkPosDist.x = blockX - (chunkPos.x << 4);
		inChunkPosDist.y = blockZ - (chunkPos.y << 4);
		return chunkPos;
	}

}
