package xueli.craftgame.world;

import com.flowpowered.nbt.CompoundMap;
import com.flowpowered.nbt.CompoundTag;
import org.lwjgl.utils.vector.Vector3i;
import xueli.craftgame.block.BlockBase;
import xueli.craftgame.init.Blocks;
import xueli.game.utils.Light;
import xueli.game.utils.tick.Tickable;

import java.util.concurrent.ConcurrentHashMap;

public class Dimension implements Tickable {

	ConcurrentHashMap<Vector3i, Chunk> chunks = new ConcurrentHashMap<>();

	private ChunkProvider provider;
	private WorldUpdater updater;

	Blocks blocks;

	public Dimension(Blocks blocks) {
		this.blocks = blocks;

		this.provider = new ChunkProvider(this);
		this.provider.start();
		this.updater = new WorldUpdater(this);
		this.updater.start();

	}

	public CompoundMap getBlockTag(int x, int y, int z) {
		Chunk chunk = chunks.get(new Vector3i(x >> 4, y >> 4, z >> 4));
		if (chunk == null)
			return null;
		return chunk.getBlockTag(x - (chunk.getChunkX() << 4), y - (chunk.getChunkY() << 4), z - (chunk.getChunkZ() << 4));

	}

	public BlockBase getBlock(int x, int y, int z) {
		Chunk chunk = chunks.get(new Vector3i(x >> 4, y >> 4, z >> 4));
		if (chunk == null)
			return null;
		return chunk.getBlock(x - (chunk.getChunkX() << 4), y - (chunk.getChunkY() << 4), z - (chunk.getChunkZ() << 4));
	}

	public void setBlockTag(int x, int y, int z, CompoundMap tag) {
		Chunk chunk = chunks.get(new Vector3i(x >> 4, y >> 4, z >> 4));
		if (chunk == null)
			return;
		chunk.setBlockTag(x - (chunk.getChunkX() << 4), y - (chunk.getChunkY() << 4), z - (chunk.getChunkZ() << 4), tag);
	}

	public void setBlock(int x, int y, int z, BlockBase tile) {
		Chunk chunk = chunks.get(new Vector3i(x >> 4, y >> 4, z >> 4));
		if (chunk == null)
			return;
		chunk.setBlock(x - (chunk.getChunkX() << 4), y - (chunk.getChunkY() << 4), z - (chunk.getChunkZ() << 4), tile);
	}

	public void setBlock(int x, int y, int z, BlockBase tile, CompoundMap tag) {
		Chunk chunk = chunks.get(new Vector3i(x >> 4, y >> 4, z >> 4));
		if (chunk == null)
			return;
		chunk.setBlock(x - (chunk.getChunkX() << 4), y - (chunk.getChunkY() << 4), z - (chunk.getChunkZ() << 4), tile);
		chunk.setBlockTag(x - (chunk.getChunkX() << 4), y - (chunk.getChunkY() << 4), z - (chunk.getChunkZ() << 4), tag);
	}

	public Chunk getChunk(int x, int y, int z) {
		return chunks.get(new Vector3i(x, y, z));
	}

	public Light getLight(int x, int y, int z) {
		Chunk chunk = chunks.get(new Vector3i(x >> 4, y >> 4, z >> 4));
		if (chunk == null)
			return null;
		return chunk.getLight(x - (chunk.getChunkX() << 4), y - (chunk.getChunkY() << 4), z - (chunk.getChunkZ() << 4));
	}
	
	@Override
	public void tick(int deltaTime) {
		
	}
	
	public void close() {
		this.updater.stopThread();
		try {
			this.updater.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		for (Vector3i v : chunks.keySet()) {
			this.provider.save(v.getX(), v.getY(), v.getZ());
		}
		this.provider.release();

	}

	public Blocks getBlocks() {
		return blocks;
	}

	public WorldUpdater getUpdater() {
		return updater;
	}

	public ChunkProvider getProvider() {
		return provider;
	}
	
}
