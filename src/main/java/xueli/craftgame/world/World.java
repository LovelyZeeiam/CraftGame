package xueli.craftgame.world;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import org.lwjgl.util.vector.Vector3f;

import xueli.craftgame.block.BlockData;
import xueli.gamengine.utils.math.MathUtils;
import xueli.gamengine.utils.vector.Vector;
import xueli.gamengine.utils.vector.Vector2i;

public class World {

	public static final int RENDER_DISTANCE = 5;
	
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
		if(!playerPos.containsKey(name)) {
			playerPos.put(name, new Vector(defaultSpawnpoint.x, defaultSpawnpoint.y, defaultSpawnpoint.z));
		}
		return playerPos.get(name);
	}
	
	public void generateChunkAccordingToPlayerPos(Vector playerPos) {
		Vector2i playerInChunkPos = World.getChunkPosFromBlock((int) playerPos.x, (int) playerPos.y);
		for(int chunkX = playerInChunkPos.x - RENDER_DISTANCE; chunkX < playerInChunkPos.x + RENDER_DISTANCE; chunkX++) {
			for(int chunkZ = playerInChunkPos.y - RENDER_DISTANCE; chunkZ < playerInChunkPos.y + RENDER_DISTANCE; chunkZ++) {
				if(!this.hasChunk(chunkX, chunkZ)) {
					this.requestGenChunk(chunkX, chunkZ);
				}
			}
		}
		
	}
	
	public boolean hasChunk(int chunkX, int chunkZ) {
		return chunksHashMap.contains(MathUtils.vert2ToLong(chunkX, chunkZ));
	}
	
	public Chunk getChunk(int chunkX, int chunkZ) {
		return chunksHashMap.get(MathUtils.vert2ToLong(chunkX, chunkZ));
	}
	
	public static Vector2i getChunkPosFromBlock(int x, int z) {
		int chunkX = x >> 4;
		int chunkZ = z >> 4;
		return new Vector2i(chunkX, chunkZ);
	}
	
	public void readyDrawcall(Vector playerPos) {
		Vector2i playerInChunkPos = World.getChunkPosFromBlock((int) playerPos.x, (int) playerPos.y);
		for(int chunkX = playerInChunkPos.x - RENDER_DISTANCE; chunkX < playerInChunkPos.x + RENDER_DISTANCE; chunkX++) {
			for(int chunkZ = playerInChunkPos.y - RENDER_DISTANCE; chunkZ < playerInChunkPos.y + RENDER_DISTANCE; chunkZ++) {
				if(this.hasChunk(chunkX, chunkZ)) {
					Chunk chunk = this.getChunk(chunkX, chunkZ);
					chunk.getMeshBuilder().postReadyDrawcall();
					
				}
			}
		}
		
	}
	
	/*
	 * public byte[] packChunk(int chunkX, int chunkZ) {
	 * 
	 * 
	 * }
	 * 
	 * public void recievePackedChunk(byte[] data) {
	 * 
	 * 
	 * }
	 * 
	 * public byte[] saveChunk(int chunkX, int ChunkZ) {
	 * 
	 * 
	 * }
	 * 
	 * public void readChunk(byte[] data) {
	 * 
	 * 
	 * }
	 */
}
