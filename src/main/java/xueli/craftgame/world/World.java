package xueli.craftgame.world;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import xueli.craftgame.block.Tile;
import xueli.craftgame.entity.Player;
import xueli.gamengine.resource.TextureAtlas;
import xueli.gamengine.utils.MathUtils;
import xueli.gamengine.utils.MatrixHelper;
import xueli.gamengine.utils.vector.Vector2i;

public class World {

	private static Chunk tempChunk;
	private WorldLogic worldLogic;
	volatile HashMap<Long, Chunk> chunks = new HashMap<Long, Chunk>();

	private CubeWorldCollider collider;

	public World(WorldLogic worldLogic) {
		this.worldLogic = worldLogic;

		collider = new CubeWorldCollider(this);

		ChunkGeneratorMaster generator = new ChunkGeneratorMaster(this);

		for (int i = 0; i < 12; i++)
			for (int q = 0; q < 12; q++)
				chunks.put(MathUtils.vert2ToLong(i, q), generator.normal(i, q));

	}

	public CubeWorldCollider getCollider() {
		return collider;
	}

	public int getHeight(int x, int z) {
		ChunkPos cp = getChunkPosFromBlock(x, z);

		int xInChunk = x - (cp.getX() << Chunk.size_yiwei);
		int zInChunk = z - (cp.getZ() << Chunk.size_yiwei);

		if (tempChunk == null || cp.getX() != tempChunk.chunkX || cp.getZ() != tempChunk.chunkZ) {
			long key = MathUtils.vert2ToLong(cp.getX(), cp.getZ());
			tempChunk = chunks.get(key);

		}
		if (tempChunk == null)
			return -1;
		return tempChunk.heightMap[xInChunk][zInChunk];
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

	private ArrayList<Chunk> getDrawChunks(Player player, int draw_distance) {
		ChunkPos chunkPos = getChunkPosFromBlock((int) player.pos.x, (int) player.pos.z);
		// 将要绘制的区块成列表
		ArrayList<Chunk> drawChunk = new ArrayList<>();

		for (int x = chunkPos.getX() - draw_distance; x < chunkPos.getX() + draw_distance; x++) {
			for (int z = chunkPos.getZ() - draw_distance; z < chunkPos.getZ() + draw_distance; z++) {
				// long key = MathUtils.vert2ToLong(x, z);
				if (MatrixHelper.isChunkInFrustum(x, Chunk.height, z)) {
					Chunk chunk = getChunk(x, z);
					if (chunk != null) {
						drawChunk.add(chunk);
					} else {
						// provider.postGenChunk(x, z);

					}
				}
			}
		}

		// 玩家坐标的整数值
		int playerX = (int) player.pos.x;
		int playerZ = (int) player.pos.z;

		// 根据离玩家的远近排序
		Collections.sort(drawChunk, (c1, c2) -> {
			// 区块中心二维坐标
			Vector2i c1vec = c1.getChunkCenter2DPosition();
			Vector2i c2vec = c2.getChunkCenter2DPosition();

			// 区块中心到玩家的二维距离
			double c1dis = Math.pow(c1vec.x - playerX, 2) + Math.pow(c1vec.y - playerZ, 2);
			double c2dis = Math.pow(c2vec.x - playerX, 2) + Math.pow(c2vec.y - playerZ, 2);
			return (int) (c2dis - c1dis);
		});

		return drawChunk;
	}

	public int draw(TextureAtlas textureAtlas, Player player, FloatBuffer drawData, int draw_distance) {
		ArrayList<Chunk> drawChunk = getDrawChunks(player, draw_distance);
		int vertCount = 0;

		for (Chunk chunk : drawChunk) {
			chunk.update(textureAtlas);
			vertCount += chunk.getVertCount();
			drawData.put(chunk.getDrawBuffer().getData());
		}

		return vertCount;
	}

	public void close() {

	}

}
