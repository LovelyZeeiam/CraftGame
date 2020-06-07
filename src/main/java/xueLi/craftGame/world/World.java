package xueLi.craftGame.world;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import xueLi.craftGame.Constants;
import xueLi.craftGame.block.Block;
import xueLi.craftGame.entity.Entity;
import xueLi.craftGame.entity.HitBox;
import xueLi.craftGame.entity.Player;
import xueLi.craftGame.utils.BlockPos;
import xueLi.craftGame.utils.ChunkPos;
import xueLi.craftGame.utils.GLHelper;
import xueLi.craftGame.utils.MathUtils;

public class World {

	volatile Map<Long, Chunk> chunks = new HashMap<Long, Chunk>();
	
	private WorldIO saver;

	// 世界大小 按区块来算
	public World() {
		saver = new WorldIO(Constants.savePath);
		
	}

	private static Chunk tempChunk;

	public Block getBlock(int x, int y, int z) {
		ChunkPos cp = getChunkPosFromBlock(x, z);

		int xInChunk = x - cp.getX() * Chunk.size;
		int zInChunk = z - cp.getZ() * Chunk.size;

		if (tempChunk == null || cp.getX() != tempChunk.chunkX || cp.getZ() != tempChunk.chunkZ)
			tempChunk = chunks.get(MathUtils.vert2ToLong(cp.getX(), cp.getZ()));
		if (tempChunk == null)
			return null;
		return tempChunk.getBlock(xInChunk, y, zInChunk);
	}

	public void setBlock(int x, int y, int z, Block block) {
		ChunkPos cp = getChunkPosFromBlock(x, z);
		Chunk chunk = chunks.get(MathUtils.vert2ToLong(cp.getX(), cp.getZ()));
		if(chunk == null)
			return;
		
		int xInChunk = x - cp.getX() * Chunk.size;
		int zInChunk = z - cp.getZ() * Chunk.size;
		if (block == null) {
			chunk.getBlock(xInChunk, y, zInChunk).onDestroy(this);

		} else {
			block.onCreate(this);

		}
		chunk.setBlock(xInChunk, y, zInChunk, block);
	}

	public void setBlock(BlockPos p, Block b) {
		if (p == null)
			return;
		setBlock(p.getX(), p.getY(), p.getZ(), b);
	}

	public void setBlock(BlockPos p, int blockID) {
		if (p == null)
			return;
		setBlock(p.getX(), p.getY(), p.getZ(), new Block(blockID));
	}

	public void onRightClickOnBlock(BlockPos pos) {
		Block block = getBlock(pos.getX(), pos.getY(), pos.getZ());
		block.onRightClick(this);

	}

	public boolean hasBlock(BlockPos p) {
		ChunkPos cp = getChunkPosFromBlock(p.getX(), p.getZ());
		Chunk chunk = chunks.get(MathUtils.vert2ToLong(cp.getX(), cp.getZ()));
		if (chunk == null)
			return false;
		int xInChunk = p.getX() - cp.getX() * Chunk.size;
		int zInChunk = p.getZ() - cp.getZ() * Chunk.size;

		return chunk.hasBlock(new BlockPos(xInChunk, p.getY(), zInChunk));
	}

	public static int chunkRenderDistance = 8;

	public int draw(Player cam, FloatBuffer buffer) {
		int vertCount = 0;
		int camX = (int) cam.pos.x;
		int camZ = (int) cam.pos.z;
		ChunkPos chunkPos = this.getChunkPosFromBlock(camX, camZ);
		for (int chunkX = chunkPos.getX() - chunkRenderDistance; chunkX < chunkPos.getX()
				+ chunkRenderDistance; chunkX++) {
			for (int chunkZ = chunkPos.getZ() - chunkRenderDistance; chunkZ < chunkPos.getZ()
					+ chunkRenderDistance; chunkZ++) {

				long chunkXZ = MathUtils.vert2ToLong(chunkX, chunkZ);
				Chunk c = this.chunks.get(chunkXZ);
				if (c == null) {
					chunks.put(MathUtils.vert2ToLong(chunkX, chunkZ), saver.load(chunkX, chunkZ,this));
					
					continue;
				}
				c.update();
				if (!GLHelper.isChunkInFrustum(chunkX, Chunk.height, chunkZ))
					continue;
				for (int xInChunk = 0; xInChunk < Chunk.size; xInChunk++) {
					for (int zInChunk = 0; zInChunk < Chunk.size; zInChunk++) {
						int yMax = c.heightMap[xInChunk][zInChunk];
						for (int y = yMax; y >= 0; y--) {
							int x = chunkX * Chunk.size + xInChunk;
							int z = chunkZ * Chunk.size + zInChunk;
							Block block = c.getBlock(xInChunk, y, zInChunk);
							if (block == null)
								continue;
							if (c.getBlock(xInChunk, y - 1, zInChunk) == null) {
								vertCount += block.getDrawData(buffer, x, y, z, 5);
							}
							if (c.getBlock(xInChunk, y + 1, zInChunk) == null) {
								vertCount += block.getDrawData(buffer, x, y, z, 4);
							}
							if (xInChunk - 1 < 0 ? this.getBlock(x - 1, y, z) == null
									: c.getBlock(xInChunk - 1, y, zInChunk) == null) {
								vertCount += block.getDrawData(buffer, x, y, z, 3);
							}
							if (xInChunk + 1 >= Chunk.size ? this.getBlock(x + 1, y, z) == null
									: c.getBlock(xInChunk + 1, y, zInChunk) == null) {
								vertCount += block.getDrawData(buffer, x, y, z, 1);
							}
							if (zInChunk - 1 < 0 ? this.getBlock(x, y, z - 1) == null
									: c.getBlock(xInChunk, y, zInChunk - 1) == null) {
								vertCount += block.getDrawData(buffer, x, y, z, 0);
							}
							if (zInChunk + 1 >= Chunk.size ? this.getBlock(x, y, z + 1) == null
									: c.getBlock(xInChunk, y, zInChunk + 1) == null) {
								vertCount += block.getDrawData(buffer, x, y, z, 2);
							}

						}
					}
				}
			}
		}

		return vertCount;
	}

	public void addEntity(Entity entity) {
		ChunkPos chunkPos = getChunkPosFromBlock(entity.pos.x, entity.pos.z);
		chunks.get(MathUtils.vert2ToLong(chunkPos.getX(), chunkPos.getZ())).entities.add(entity);
	}

	public ArrayList<HitBox> getHitBoxes(HitBox box, int worldMaxSize) {
		int x1 = new BigDecimal(String.valueOf(box.x1)).setScale(0, BigDecimal.ROUND_DOWN).intValue();
		int x2 = new BigDecimal(String.valueOf(box.x2 + 1.0f)).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
		int y1 = new BigDecimal(String.valueOf(box.y1)).setScale(0, BigDecimal.ROUND_DOWN).intValue();
		int y2 = new BigDecimal(String.valueOf(box.y2 + 1.0f)).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
		int z1 = new BigDecimal(String.valueOf(box.z1)).setScale(0, BigDecimal.ROUND_DOWN).intValue();
		int z2 = new BigDecimal(String.valueOf(box.z2 + 1.0f)).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();

		if (x1 < 0)
			x1 = 0;
		if (y1 < 0)
			y1 = 0;
		if (z1 < 0)
			z1 = 0;
		if (x2 > worldMaxSize)
			x2 = worldMaxSize - 1;
		if (y2 > Chunk.height)
			y2 = Chunk.height - 1;
		if (z2 > worldMaxSize)
			z1 = worldMaxSize - 1;

		ArrayList<HitBox> boxes = new ArrayList<HitBox>();
		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y2; y++) {
				for (int z = z1; z < z2; z++) {
					Block block = this.getBlock(x, y, z);
					if (block == null)
						continue;
					boxes.add(block.getHitbox(x, y, z));
				}
			}
		}
		return boxes;
	}

	private ChunkPos getChunkPosFromBlock(int x, int z) {
		int chunkX = x / 16;
		int chunkZ = z / 16;
		return new ChunkPos(chunkX - (x < 0 ? 1 : 0), chunkZ - (z < 0 ? 1 : 0));
	}

	private ChunkPos getChunkPosFromBlock(float x, float z) {
		int chunkX = (int) (x / 16);
		int chunkZ = (int) (z / 16);
		return new ChunkPos(chunkX - (x < 0 ? 1 : 0), chunkZ - (z < 0 ? 1 : 0));
	}

	public static void processPlayer(Player player) {
		// TODO: 在写了GUI之后会把setProjMatrix这个方法放在GUI的callback嗲
		WorldVertexBinder.shader.setViewMatrix(player);
		GLHelper.calculateFrustumPlane();
	}

	public void saveAll() {
		for (Map.Entry<Long, Chunk> chunkEntry : chunks.entrySet()) {
			Chunk chunk = chunkEntry.getValue();
			try {
				saver.save(chunk);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		saver.close();
	}

}
