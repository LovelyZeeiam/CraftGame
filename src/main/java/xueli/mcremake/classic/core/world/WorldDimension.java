package xueli.mcremake.classic.core.world;

import com.flowpowered.nbt.CompoundMap;
import xueli.game.vector.Vector2i;
import xueli.mcremake.classic.client.CraftGameClient;
import xueli.mcremake.classic.client.WorldEvents;
import xueli.mcremake.classic.core.block.BlockType;

import java.util.HashMap;

public class WorldDimension implements WorldAccessible {

	private final CraftGameClient ctx;
	private final HashMap<Vector2i, Chunk> chunkMap = new HashMap<>();

	public WorldDimension(CraftGameClient ctx) {
		this.ctx = ctx;

	}

	public void init() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Chunk chunk = new Chunk();
				for (int k = 0; k < 64; k++) {

				}
				chunkMap.put(new Vector2i(i, j), chunk);
				ctx.WorldEventBus.post(new WorldEvents.NewChunkEvent(i, j));
			}
		}

	}

	public WorldAccessible createAccessible() {
		return this;
	}

	@Override
	public BlockType getBlock(int x, int y, int z) {
		int chunkX = x >> 4;
		int chunkZ = z >> 4;
		Chunk chunk = chunkMap.get(new Vector2i(chunkX, chunkZ));
		return chunk == null ? null : chunk.getBlock(x - (chunkX << 4), y, z - (chunkZ << 4));
	}

	@Override
	public CompoundMap getBlockTag(int x, int y, int z) {
		int chunkX = x >> 4;
		int chunkZ = z >> 4;
		Chunk chunk = chunkMap.get(new Vector2i(chunkX, chunkZ));
		return chunk == null ? null : chunk.getBlockTag(x - (chunkX << 4), y, z - (chunkZ << 4));
	}

	// TODO: Later, Add it to a preserved chunk command list
	@Override
	public void setBlock(int x, int y, int z, BlockType block) {
		int chunkX = x >> 4;
		int chunkZ = z >> 4;
		Chunk chunk = chunkMap.get(new Vector2i(chunkX, chunkZ));
		if(chunk != null) {
			chunk.setBlock(x - (chunkX << 4), y, z - (chunkZ << 4), block);
		}

	}

	@Override
	public CompoundMap createBlockTag(int x, int y, int z) {
		int chunkX = x >> 4;
		int chunkZ = z >> 4;
		Chunk chunk = chunkMap.get(new Vector2i(chunkX, chunkZ));
		return chunk == null ? null : chunk.createBlockTag(x - (chunkX << 4), y, z - (chunkZ << 4));
	}

	public Chunk getChunk(int x, int z) {
		return chunkMap.get(new Vector2i(x, z));
	}

}
