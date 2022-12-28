package xueli.mcremake.core.world;

import com.flowpowered.nbt.CompoundMap;
import org.lwjgl.utils.vector.Vector2i;
import xueli.mcremake.registry.GameRegistry;
import xueli.mcremake.client.CraftGameClient;
import xueli.mcremake.client.WorldEvents;
import xueli.mcremake.core.block.BlockType;

import java.util.HashMap;
import java.util.function.Consumer;

public class WorldDimension implements WorldAccessible {

	private final CraftGameClient ctx;
	private final HashMap<Vector2i, Chunk> chunkMap = new HashMap<>();

	public WorldDimension(CraftGameClient ctx) {
		this.ctx = ctx;

	}

	public void init() {
		for (int i = -4; i < 4; i++) {
			for (int j = -4; j < 4; j++) {
				Chunk chunk = new Chunk(this);
				for (int k = 0; k < 8; k++) {
					for (int l = 0; l < Chunk.CHUNK_SIZE; l++) {
						for (int m = 0; m < Chunk.CHUNK_SIZE; m++) {
							chunk.setBlock(l, k, m, GameRegistry.STONE);
						}
					}
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
		Vector2i chunkPos = Chunk.toChunkPos(x, z);
		Chunk chunk = chunkMap.get(chunkPos);
		return chunk == null ? null : chunk.getBlock(x - (chunkPos.x * Chunk.CHUNK_SIZE), y, z - (chunkPos.y * Chunk.CHUNK_SIZE));
	}

	@Override
	public CompoundMap getBlockTag(int x, int y, int z) {
		Vector2i chunkPos = Chunk.toChunkPos(x, z);
		Chunk chunk = chunkMap.get(chunkPos);
		return chunk == null ? null : chunk.getBlockTag(x - (chunkPos.x * Chunk.CHUNK_SIZE), y, z - (chunkPos.y * Chunk.CHUNK_SIZE));
	}

	@Override
	public void setBlock(int x, int y, int z, BlockType block) {
		Vector2i chunkPos = Chunk.toChunkPos(x, z);
		Chunk chunk = chunkMap.get(chunkPos);
		if(chunk != null) {
			chunk.setBlock(x - (chunkPos.x * Chunk.CHUNK_SIZE), y, z - (chunkPos.y * Chunk.CHUNK_SIZE), block);
		}

	}

	@Override
	public void modifyBlockTag(int x, int y, int z, Consumer<CompoundMap> c) {
		Vector2i chunkPos = Chunk.toChunkPos(x, z);
		Chunk chunk = chunkMap.get(chunkPos);
		if(chunk == null) return;
		chunk.modifyBlockTag(x - (chunkPos.x * Chunk.CHUNK_SIZE), y, z - (chunkPos.y * Chunk.CHUNK_SIZE), c);

	}

	public Chunk getChunk(int x, int z) {
		return chunkMap.get(new Vector2i(x, z));
	}

}
