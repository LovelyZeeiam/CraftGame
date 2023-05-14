package xueli.mcremake.core.world;

import java.util.HashMap;
import java.util.function.Consumer;

import org.lwjgl.utils.vector.Vector2i;

import com.flowpowered.nbt.CompoundMap;

import xueli.mcremake.client.CraftGameClient;
import xueli.mcremake.client.events.NewChunkEvent;
import xueli.mcremake.core.block.BlockType;
import xueli.mcremake.level.worldgen.MyChunkProvider;

public class WorldDimension implements WorldAccessible {

	private final CraftGameClient ctx;
	private final HashMap<Vector2i, Chunk> chunkMap = new HashMap<>();

	public WorldDimension(CraftGameClient ctx) {
		this.ctx = ctx;

	}

	public void init() { // Maybe it should be a startup component
		MyChunkProvider chunkGenerator = new MyChunkProvider(666);
		for (int i = -8; i < 8; i++) {
			for (int j = -8; j < 8; j++) {
				final Vector2i chunkPos = new Vector2i(i, j);
				chunkGenerator.getChunk(j, i, ctx.getAsyncExecutor())
					.thenAcceptAsync(chunk -> {
						chunkMap.put(chunkPos, chunk);
						ctx.eventbus.post(new NewChunkEvent(chunkPos.x, chunkPos.y));
					}, ctx.getMainThreadExecutor());
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
