package xueli.mcremake.classic.world;

import com.flowpowered.nbt.CompoundMap;
import com.flowpowered.nbt.Tag;
import xueli.mcremake.classic.block.BlockType;

import java.util.HashMap;

public class Chunk {

	public static final int CHUNK_SIZE = 16;
	public static final int SUB_CHUNK_HEIGHT = 16;
	public static final int SUB_CHUNK_LAYER_COUNT = 8;

	private final ChunkGrid[] grids = new ChunkGrid[SUB_CHUNK_LAYER_COUNT];

	private final HashMap<String, Integer> techniqueTags = new HashMap<>();
	private final CompoundMap gamingChunkTag = new CompoundMap();

	public Chunk() {
	}

	public ChunkAccessible createStraightAccessible() {
		return new ChunkAccessible() {
			@Override
			public BlockType getBlock(int x, int y, int z) {
				int yInSub = y / SUB_CHUNK_HEIGHT;
				return grids[yInSub].grid[x][z][y % SUB_CHUNK_HEIGHT];
			}

			@Override
			public CompoundMap getBlockTag(int x, int y, int z) {
				int yInSub = y / SUB_CHUNK_HEIGHT;
				return grids[yInSub].tagGrid[x][z][y % SUB_CHUNK_HEIGHT];
			}

			@Override
			public void setBlock(int x, int y, int z, BlockType block) {
				int yInSub = y / SUB_CHUNK_HEIGHT;
				grids[yInSub].grid[x][z][y % SUB_CHUNK_HEIGHT] = block;
			}

			@Override
			public CompoundMap createBlockTag(int x, int y, int z) {
				int yInSub = y / SUB_CHUNK_HEIGHT;
				return grids[yInSub].tagGrid[x][z][y % SUB_CHUNK_HEIGHT] = new CompoundMap();
			}
		};
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

}
