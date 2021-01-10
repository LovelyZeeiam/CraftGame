package xueli.craftgame.world;

import java.nio.FloatBuffer;
import java.util.HashMap;

import xueli.craftgame.WorldLogic;
import xueli.craftgame.block.Tile;
import xueli.craftgame.entity.Player;
import xueli.gamengine.resource.TextureAtlas;
import xueli.gamengine.utils.MathUtils;
import xueli.gamengine.utils.MatrixHelper;

public class World {

	private static Chunk tempChunk;
	private WorldLogic worldLogic;
	volatile HashMap<Long, Chunk> chunks = new HashMap<Long, Chunk>();

	private CubeWorldCollider collider;

	public World(WorldLogic worldLogic) {
		this.worldLogic = worldLogic;

		collider = new CubeWorldCollider(this);

		ChunkGenerator generator = new ChunkGenerator(this);

		for (int i = 0; i < 4; i++)
			for (int q = 0; q < 4; q++)
				chunks.put(MathUtils.vert2ToLong(i, q), generator.normal(i, q));

	}

	public CubeWorldCollider getCollider() {
		return collider;
	}

	public Tile getBlock(int x, int y, int z) {
		ChunkPos cp = getChunkPosFromBlock(x, z);

		int xInChunk = x - (cp.getX() << Chunk.size_yiwei);
		int zInChunk = z - (cp.getZ() << Chunk.size_yiwei);

		if (tempChunk == null || cp.getX() != tempChunk.chunkX || cp.getZ() != tempChunk.chunkZ) {
			long key = MathUtils.vert2ToLong(cp.getX(), cp.getZ());
			tempChunk = chunks.get(key);

		}
		if (tempChunk == null)
			return null;
		return tempChunk.getBlock(xInChunk, y, zInChunk);
	}

	public void setBlock(int x, int y, int z, Tile block) {
		ChunkPos cp = getChunkPosFromBlock(x, z);

		long key = MathUtils.vert2ToLong(cp.getX(), cp.getZ());
		Chunk chunk = chunks.get(key);
		if (chunk == null)
			return;

		int xInChunk = x - (cp.getX() << Chunk.size_yiwei);
		int zInChunk = z - (cp.getZ() << Chunk.size_yiwei);
		if (block == null) {
			Tile tile = chunk.getBlock(xInChunk, y, zInChunk);
			if (tile != null)
				tile.getListener().onDestroy(x, y, z, this);

		} else {
			block.getListener().onCreate(x, y, z, this);

		}
		chunk.setBlock(xInChunk, y, zInChunk, block);
	}

	public void setBlock(BlockPos p, Tile b) {
		if (p == null)
			return;
		setBlock(p.getX(), p.getY(), p.getZ(), b);
	}

	public void setBlock(BlockPos p, String blockName) {
		if (p == null)
			return;
		setBlock(p.getX(), p.getY(), p.getZ(), new Tile(blockName));
	}

	public void putChunk(Chunk chunk) {
		long key = MathUtils.vert2ToLong(chunk.chunkX, chunk.chunkZ);
		chunks.put(key, chunk);

	}

	public static ChunkPos getChunkPosFromBlock(int x, int z) {
		int chunkX = x >> Chunk.size_yiwei;
		int chunkZ = z >> Chunk.size_yiwei;
		return new ChunkPos(chunkX, chunkZ);
	}

	public boolean hasBlock(BlockPos p) {
		ChunkPos cp = getChunkPosFromBlock(p.getX(), p.getZ());
		Chunk chunk = chunks.get(MathUtils.vert2ToLong(cp.getX(), cp.getZ()));
		if (chunk == null)
			return false;
		int xInChunk = p.getX() - (cp.getX() << Chunk.size_yiwei);
		int zInChunk = p.getZ() - (cp.getZ() << Chunk.size_yiwei);

		return chunk.hasBlock(new BlockPos(xInChunk, p.getY(), zInChunk));
	}

	public Chunk getChunk(int x, int z) {
		long key = MathUtils.vert2ToLong(x, z);
		Chunk chunk = chunks.get(key);
		return chunk;
	}

	public void updateVertexBuffer(TextureAtlas textureAtlas, Player player, int draw_distance) {
		ChunkPos chunkPos = getChunkPosFromBlock((int) player.pos.x, (int) player.pos.z);

		for (int x = chunkPos.getX() - draw_distance; x < chunkPos.getX() + draw_distance; x++) {
			for (int z = chunkPos.getZ() - draw_distance; z < chunkPos.getZ() + draw_distance; z++) {
				// long key = MathUtils.vert2ToLong(x, z);
				Chunk chunk = getChunk(x, z);
				if (chunk != null) {
					chunk.update(textureAtlas);
				}

			}
		}
	}

	public void tick(Player player) {
		// removalChecker.tick();

	}

	public int draw(TextureAtlas textureAtlas, Player player, FloatBuffer drawData, int draw_distance) {
		ChunkPos chunkPos = getChunkPosFromBlock((int) player.pos.x, (int) player.pos.z);

		int vertCount = 0;

		for (int x = chunkPos.getX() - draw_distance; x < chunkPos.getX() + draw_distance; x++) {
			for (int z = chunkPos.getZ() - draw_distance; z < chunkPos.getZ() + draw_distance; z++) {
				// long key = MathUtils.vert2ToLong(x, z);
				Chunk chunk = getChunk(x, z);
				if (chunk != null) {
					if (MatrixHelper.isChunkInFrustum(x, Chunk.height, z)) {
						chunk.update(textureAtlas);
						vertCount += chunk.getVertCount();
						drawData.put(chunk.getDrawBuffer().getData());

					}
				} else {
					// provider.postGenChunk(x, z);

				}

			}
		}

		// System.out.println(vertCount);

		return vertCount;
	}

	public void close() {

	}

}
