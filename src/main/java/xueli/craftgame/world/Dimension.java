package xueli.craftgame.world;

import java.util.concurrent.ConcurrentHashMap;

import org.lwjgl.util.vector.Vector3i;

import xueli.craftgame.init.Blocks;
import xueli.game.utils.Light;
import xueli.game.vector.Vector;

public class Dimension {

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

	public Tile getBlock(int x, int y, int z) {
		Chunk chunk = chunks.get(new Vector3i(x >> 4, y >> 4, z >> 4));
		if (chunk == null)
			return null;
		return chunk.getBlock(x - (chunk.getChunkX() << 4), y - (chunk.getChunkY() << 4), z - (chunk.getChunkZ() << 4));
	}

	public void setBlock(int x, int y, int z, Tile tile) {
		Chunk chunk = chunks.get(new Vector3i(x >> 4, y >> 4, z >> 4));
		if (chunk == null)
			return;
		chunk.setBlock(x - (chunk.getChunkX() << 4), y - (chunk.getChunkY() << 4), z - (chunk.getChunkZ() << 4), tile);
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

	public void tick(Vector playerPos) {
		this.provider.tick(playerPos);

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

}
