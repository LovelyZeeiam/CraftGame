package xueli.craftgame.world;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import org.lwjgl.util.vector.Vector3f;

import xueli.craftgame.block.BlockData;
import xueli.craftgame.chunk.Chunk;
import xueli.gamengine.utils.math.MathUtils;
import xueli.gamengine.utils.vector.Vector;

public class World {

	private ConcurrentHashMap<Long, Chunk> chunksHashMap = new ConcurrentHashMap<Long, Chunk>();
	private Vector3f defaultSpawnpoint = new Vector3f(0, 0, 0);

	private HashMap<String, Vector> playerPos = new HashMap<String, Vector>();

	public World() {

	}

	public Chunk requestGenChunk(int x, int z) {
		Chunk chunk = new Chunk(x, z, this);

		for (int x_chunk = 0; x_chunk < 16; x_chunk++) {
			for (int z_chunk = 0; z_chunk < 16; z_chunk++) {
				chunk.setBlock(x_chunk, 10, z_chunk, "craftgame:grass_block", 0, null);
			}
		}

		chunksHashMap.put(MathUtils.vert2ToLong(x, z), chunk);
		return chunk;
	}

	public BlockData getBlock(int x, int y, int z) {
		int chunkX = x >> 4;
		int chunkZ = z >> 4;
		Chunk chunk = chunksHashMap.get(MathUtils.vert2ToLong(chunkX, chunkZ));
		return chunk.getBlock(x % 16, y, z % 16);
	}

	public long getDetail(int x, int y, int z) {
		int chunkX = x >> 4;
		int chunkZ = z >> 4;
		Chunk chunk = chunksHashMap.get(MathUtils.vert2ToLong(chunkX, chunkZ));
		return chunk.getDetail(x % 16, y, z % 16);
	}

	public Vector3f getDefaultSpawnpoint() {
		return defaultSpawnpoint;
	}

	public void setDefaultSpawnpoint(Vector3f defaultSpawnpoint) {
		this.defaultSpawnpoint = defaultSpawnpoint;
	}

	public Vector getPlayer(String name) {
		return playerPos.get(name);
	}

}
