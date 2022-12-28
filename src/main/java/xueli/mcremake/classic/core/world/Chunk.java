package xueli.mcremake.classic.core.world;

import com.flowpowered.nbt.CompoundMap;
import com.flowpowered.nbt.Tag;
import xueli.mcremake.classic.core.block.BlockType;

import java.util.HashMap;

public class Chunk implements WorldAccessible {

	public static final int CHUNK_SIZE = 16;
	public static final int SUB_CHUNK_HEIGHT = 16;
	public static final int SUB_CHUNK_LAYER_COUNT = 8;
	public static final int CHUNK_HEIGHT = SUB_CHUNK_HEIGHT * SUB_CHUNK_LAYER_COUNT;

	private final WorldDimension world;
	final ChunkGrid[] grids = new ChunkGrid[SUB_CHUNK_LAYER_COUNT];

	private final HashMap<String, Integer> techniqueTags = new HashMap<>();
	private final CompoundMap gamingChunkTag = new CompoundMap();

	public Chunk(WorldDimension world) {
		this.world = world;
		for (int i = 0; i < SUB_CHUNK_LAYER_COUNT; i++) {
			grids[i] = new ChunkGrid();
		}

	}

	public WorldAccessible createAccessible() {
		return this;
	}

	@Override
	public BlockType getBlock(int x, int y, int z) {
		if(y < 0 || y >= Chunk.CHUNK_HEIGHT) return null;
		int ySub = y / SUB_CHUNK_HEIGHT;
		return grids[ySub].grid[x][z][y % SUB_CHUNK_HEIGHT];
	}

	@Override
	public CompoundMap getBlockTag(int x, int y, int z) {
		if(y < 0 || y >= Chunk.CHUNK_HEIGHT) return null;
		int ySub = y / SUB_CHUNK_HEIGHT;
		return grids[ySub].tagGrid[x][z][y % SUB_CHUNK_HEIGHT];
	}

	@Override
	public void setBlock(int x, int y, int z, BlockType block) {
		if(y < 0 || y >= Chunk.CHUNK_HEIGHT) return;
		int ySub = y / SUB_CHUNK_HEIGHT;
		int yInSub = y % SUB_CHUNK_HEIGHT;
		ChunkGrid grid = grids[ySub];
		grid.grid[x][z][yInSub] = block;

		if(block == null) {
			if(y == grid.heightMap[x][z])
				for (int i = --grid.heightMap[x][z]; i >= 0 && grid.grid[x][z][i] == null; i--) {}
		} else {
			grid.heightMap[x][z] = Math.max(grid.heightMap[x][z], y);
		}

	}

	@Override
	public CompoundMap createBlockTag(int x, int y, int z) {
		if(y < 0 || y >= Chunk.CHUNK_HEIGHT) return null;
		int ySub = y / SUB_CHUNK_HEIGHT;
		return grids[ySub].tagGrid[x][z][y % SUB_CHUNK_HEIGHT] = new CompoundMap();
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

	public WorldDimension getWorld() {
		return world;
	}

}
