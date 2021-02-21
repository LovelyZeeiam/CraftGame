package xueli.craftgame.world;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import org.lwjgl.util.vector.Vector3f;

import xueli.craftgame.CraftGame;
import xueli.craftgame.WorldLogic;
import xueli.craftgame.block.Block;
import xueli.craftgame.net.player.PlayerStat;
import xueli.craftgame.net.server.ServerPlayer;
import xueli.gamengine.utils.math.MathUtils;
import xueli.gamengine.utils.vector.Vector;
import xueli.gamengine.utils.vector.Vector2i;
import xueli.utils.Logger;

public class World {

	public static final int RENDER_DISTANCE = 5;

	private final WorldLogic logic;

	private ConcurrentHashMap<Long, Chunk> chunksHashMap = new ConcurrentHashMap<Long, Chunk>();
	private Vector3f defaultSpawnpoint = new Vector3f(20, 12, 20);

	private HashMap<String, ServerPlayer> players = new HashMap<String, ServerPlayer>();

	public World(WorldLogic logic) {
		this.logic = logic;

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

	public ServerPlayer getPlayer(String name) {
		if (!players.containsKey(name)) {
			players.put(name,
					new ServerPlayer(name, new Vector(defaultSpawnpoint.x, defaultSpawnpoint.y, defaultSpawnpoint.z)));
		}
		return players.get(name);
	}

	public HashMap<String, ServerPlayer> getPlayers() {
		return players;
	}

	public void correctPlayers(HashMap<String, ServerPlayer> players) {
		players.forEach((name, player) -> {
			if (!this.players.containsKey(name)) {
				Logger.warn("[Client] Server has sent a unknown player message: " + name + ". ignore.");
				return;
			}

			ServerPlayer thisPlayer = this.players.get(name);
			thisPlayer.setState(player.getState());

			Vector thisPlayerPos = thisPlayer.getPlayerPos();
			Vector remotePlayerPos = player.getPlayerPos();

			thisPlayerPos.x = remotePlayerPos.x;
			thisPlayerPos.y = remotePlayerPos.y;
			thisPlayerPos.z = remotePlayerPos.z;

			if (name.equals(CraftGame.INSTANCE_CRAFT_GAME.getPlayerStat().getName())) {
				// 本地玩家
				logic.setPlayerPos(player.getPlayerPos());

			}

		});
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
