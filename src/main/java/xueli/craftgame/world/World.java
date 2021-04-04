package xueli.craftgame.world;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector3i;

import com.flowpowered.nbt.CompoundTag;

import xueli.craftgame.block.Block;
import xueli.game.player.PlayerStat;
import xueli.game.utils.math.MathUtils;
import xueli.game.vector.Vector;
import xueli.game.vector.Vector2i;

public class World {

	public static final int RENDER_DISTANCE = 5;

	private ConcurrentHashMap<Long, Chunk> chunksHashMap = new ConcurrentHashMap<Long, Chunk>();
	private Vector3f defaultSpawnpoint = new Vector3f(20, 12, 20);

	private HashMap<Vector3i, CompoundTag> blockData = new HashMap<>();

	public World() {

	}

	public Chunk requestGenChunk(int x, int z) {
		Chunk chunk = new Chunk(x, z, this);

		for (int x_chunk = 0; x_chunk < 16; x_chunk++) {
			for (int z_chunk = 0; z_chunk < 16; z_chunk++) {
				chunk.setBlock(x_chunk, 10, z_chunk, "craftgame:grass_block", null);
			}
		}

		chunksHashMap.put(MathUtils.vert2ToLong(x, z), chunk);

		return chunk;
	}

	public Block getBlock(int x, int y, int z) {
		int chunkX = x >> 4;
		int chunkZ = z >> 4;
		Chunk chunk = chunksHashMap.get(MathUtils.vert2ToLong(chunkX, chunkZ));
		if (chunk == null)
			return null;
		return chunk.getBlock(x - chunkX * 16, y, z - chunkZ * 16);
	}

	public boolean hasBlock(int x, int y, int z) {
		return getBlock(x, y, z) != null;
	}

	public Vector3f getDefaultSpawnpoint() {
		return defaultSpawnpoint;
	}

	public void setDefaultSpawnpoint(Vector3f defaultSpawnpoint) {
		this.defaultSpawnpoint = defaultSpawnpoint;
	}

	public void generateChunkAccordingToPlayerPos(Vector playerPos) {
		Vector2i playerInChunkPos = World.getChunkPosFromBlock((int) playerPos.x, (int) playerPos.y);
		for (int chunkX = playerInChunkPos.x - RENDER_DISTANCE; chunkX <= playerInChunkPos.x
				+ RENDER_DISTANCE; chunkX++) {
			for (int chunkZ = playerInChunkPos.y - RENDER_DISTANCE; chunkZ <= playerInChunkPos.y
					+ RENDER_DISTANCE; chunkZ++) {
				if (!this.hasChunk(chunkX, chunkZ)) {
					this.requestGenChunk(chunkX, chunkZ);
				}
			}
		}

	}

	public boolean hasChunk(int chunkX, int chunkZ) {
		return chunksHashMap.containsKey(MathUtils.vert2ToLong(chunkX, chunkZ));
	}

	public Chunk getChunk(int chunkX, int chunkZ) {
		return chunksHashMap.get(MathUtils.vert2ToLong(chunkX, chunkZ));
	}

	public static Vector2i getChunkPosFromBlock(int x, int z) {
		int chunkX = x >> 4;
		int chunkZ = z >> 4;
		return new Vector2i(chunkX, chunkZ);
	}

	public void setBlock(int x, int y, int z, Block block, PlayerStat stat) {
		Vector2i cp = getChunkPosFromBlock(x, z);

		long key = MathUtils.vert2ToLong(cp.x, cp.y);
		Chunk chunk = chunksHashMap.get(key);
		if (chunk == null)
			return;

		int xInChunk = x - (cp.x << 4);
		int zInChunk = z - (cp.y << 4);
		chunk.setBlock(xInChunk, y, zInChunk, block, stat);
	}

	public void readyDrawcall(Vector playerPos) {
		Vector2i playerInChunkPos = World.getChunkPosFromBlock((int) playerPos.x, (int) playerPos.y);
		for (int chunkX = playerInChunkPos.x - RENDER_DISTANCE; chunkX <= playerInChunkPos.x
				+ RENDER_DISTANCE; chunkX++) {
			for (int chunkZ = playerInChunkPos.y - RENDER_DISTANCE; chunkZ <= playerInChunkPos.y
					+ RENDER_DISTANCE; chunkZ++) {
				if (this.hasChunk(chunkX, chunkZ)) {
					Chunk chunk = this.getChunk(chunkX, chunkZ);
					chunk.getMeshBuilder().drawUpdate();

				}
			}
		}

	}

	public CompoundTag getBlockData(int x, int y, int z) {
		return blockData.get(new Vector3i(x, y, z));
	}

}
