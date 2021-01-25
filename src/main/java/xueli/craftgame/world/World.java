package xueli.craftgame.world;

import org.lwjgl.util.vector.Vector3i;
import xueli.craftgame.block.Tile;
import xueli.craftgame.entity.Player;
import xueli.gamengine.resource.TextureAtlas;
import xueli.gamengine.utils.MathUtils;
import xueli.gamengine.utils.MatrixHelper;
import xueli.gamengine.utils.Time;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;

public class World {

	private static Chunk tempChunk;
	private WorldLogic worldLogic;
	private ChunkGeneratorMaster gen;
	volatile HashMap<Long, Chunk> chunks = new HashMap<Long, Chunk>();

	private CubeWorldCollider collider;
	private WorldIO io;

	public World(WorldLogic worldLogic) {
		this.worldLogic = worldLogic;

		collider = new CubeWorldCollider(this);
		io = new WorldIO(this);
		gen = new ChunkGeneratorMaster(this);

		for(int i = 0;i < 8;i++)
		for(int m = 0;m < 8;m++)
			requireGenChunk(i,m);

	}

	public World() {
		gen = new ChunkGeneratorMaster(this);
		
	}

	public void requireGenChunk(int x, int z) {
		if(chunks.containsKey(MathUtils.vert2ToLong(x, z)))
			return;
		io.readChunk(x,z);

	}

	public void requireGenChunkSync(int x, int z) {
		io.readChunkSync(x,z);
	}
	
	public CubeWorldCollider getCollider() {
		return collider;
	}

	public ChunkGeneratorMaster getGen() {
		return gen;
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

	public void setBlock(Vector3i p, Tile b) {
		if (p == null)
		return;
		setBlock(p.getX(), p.getY(), p.getZ(), b);
	}

	public void setBlock(Vector3i p, String blockName) {
		if (p == null)
			return;
		setBlock(p.getX(), p.getY(), p.getZ(), new Tile(blockName, worldLogic));
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

	public boolean hasBlock(Vector3i p) {
		ChunkPos cp = getChunkPosFromBlock(p.getX(), p.getZ());
		Chunk chunk = chunks.get(MathUtils.vert2ToLong(cp.getX(), cp.getZ()));
		if (chunk == null)
			return false;
		int xInChunk = p.getX() - (cp.getX() << Chunk.size_yiwei);
		int zInChunk = p.getZ() - (cp.getZ() << Chunk.size_yiwei);

		return chunk.hasBlock(new Vector3i(xInChunk, p.getY(), zInChunk));
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

	private long lastSaveTime = Time.thisTime;

	public void tick(Player player) {
		synchronized (chunkThatNeedAdd) {
			for(Chunk c : chunkThatNeedAdd) {
				this.chunks.put(MathUtils.vert2ToLong(c.chunkX, c.chunkZ), c);
				if(chunkThatHasRequired.contains(MathUtils.vert2ToLong(c.chunkX, c.chunkZ))) {
					chunkThatHasRequired.remove(MathUtils.vert2ToLong(c.chunkX, c.chunkZ));
				}
			}
			chunkThatNeedAdd.clear();
		}
		/*synchronized (chunkThatNeedRemove) {
			for(Long c : chunkThatNeedRemove) {
				if(this.chunks.containsKey(c)) {
					this.chunks.get(c).close();
					this.chunks.remove(c);
				}
			}
			chunkThatNeedRemove.clear();
		}
		if(Time.thisTime - lastSaveTime > 10000) {
			lastSaveTime = Time.thisTime;
			io.checkSave();

		}*/

	}

	private ArrayList<Long> chunkThatHasRequired = new ArrayList<>();
	ArrayList<Chunk> chunkThatNeedAdd = new ArrayList<>();
	ArrayList<Long> chunkThatNeedRemove = new ArrayList<>();

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
						} else if (!chunkThatHasRequired.contains(MathUtils.vert2ToLong(x, z))) {
							/*int finalX = x;
							int finalZ = z;
							worldLogic.getCg().queueRunningInMainThread.add(() -> chunkThatNeedAdd.add(requireGenChunk(finalX, finalZ)));
							chunkThatHasRequired.add(MathUtils.vert2ToLong(x, z));*/

						}
					}
				}
			}

		return drawChunk;
	}

	public int draw(TextureAtlas textureAtlas, Player player, FloatBuffer drawData, int draw_distance) {
		int vertCount = 0;
			ArrayList<Chunk> drawChunk = getDrawChunks(player, draw_distance);

			for (Chunk chunk : drawChunk) {
				chunk.update(textureAtlas);
				vertCount += chunk.getVertCount();
				drawData.put(chunk.getDrawBuffer().getData());
			}

		return vertCount;
	}

	public int drawAlpha(TextureAtlas textureAtlas, Player player, FloatBuffer drawData, int draw_distance) {
		int vertCount = 0;
			ArrayList<Chunk> drawChunk = getDrawChunks(player, draw_distance);

			for (Chunk chunk : drawChunk) {
				chunk.update(textureAtlas);
				vertCount += chunk.getVertCountForAlphaDraw();
				drawData.put(chunk.getBufferForAlphaDraw().getData());
			}

		return vertCount;
	}

	public void saveAndLoad() {
		io.checkSave();

	}

	public WorldLogic getWorldLogic() {
		return worldLogic;
	}

	public void close() {
		chunks.forEach((n, c) -> {
			c.close();
			io.saveChunk(c);
		});

		chunks.clear();
		io.close();
		io = null;

	}

}
