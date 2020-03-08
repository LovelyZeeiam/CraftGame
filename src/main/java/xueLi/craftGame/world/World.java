package xueLi.craftGame.world;

import java.math.BigDecimal;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import xueLi.craftGame.block.Block;
import xueLi.craftGame.entity.Entity;
import xueLi.craftGame.entity.Player;
import xueLi.craftGame.entity.renderer.EntityRenderer;
import xueLi.craftGame.utils.BlockPos;
import xueLi.craftGame.utils.ChunkPos;
import xueLi.craftGame.utils.GLHelper;
import xueLi.craftGame.utils.HitBox;
import xueLi.craftGame.utils.Vector;

public class World {

	private Map<Long, Chunk> chunks = new HashMap<Long, Chunk>();

	private boolean isWorldLimited = false;
	public int wlimit_long, wlimit_width;
	private int limit_long, limit_width;

	public World(int limit_long, int limit_width) {
		for (int x = 0; x < limit_long; x++) {
			for (int z = 0; z < limit_width; z++) {
				chunks.put(GLHelper.vert2ToLong(x, z), ChunkGenerator.superflat(x, z));
			}
		}
		isWorldLimited = true;
		this.wlimit_long = limit_long * Chunk.size;
		this.wlimit_width = limit_width * Chunk.size;
		this.limit_long = limit_long;
		this.limit_width = limit_width;
	}

	private static Chunk tempChunk;
	public Block getBlock(int x, int y, int z) {
		ChunkPos cp = getChunkPosFromBlock(x, z);
		if (isWorldLimited) {
			if (cp.getX() > this.limit_long - 1 || cp.getZ() > this.limit_width - 1 || cp.getX() < 0 || cp.getZ() < 0)
				return null;

			int xInChunk = x - cp.getX() * Chunk.size;
			int zInChunk = z - cp.getZ() * Chunk.size;

			if (tempChunk == null || cp.getX() != tempChunk.chunkX || cp.getZ() != tempChunk.chunkZ)
				tempChunk = chunks.get(GLHelper.vert2ToLong(cp.getX(), cp.getZ()));
			if (tempChunk == null)
				return null;
			return tempChunk.getBlock(xInChunk, y, zInChunk);
		}
		return null;
	}

	public void setBlock(int x, int y, int z, Block block) {
		ChunkPos cp = getChunkPosFromBlock(x, z);
		if (isWorldLimited) {
			if (cp.getX() > this.limit_long - 1 || cp.getZ() > this.limit_width - 1 || cp.getX() < 0 || cp.getZ() < 0
					|| y > Chunk.height - 1 || y < 0)
				return;

			int xInChunk = x - cp.getX() * Chunk.size;
			int zInChunk = z - cp.getZ() * Chunk.size;
			chunks.get(GLHelper.vert2ToLong(cp.getX(), cp.getZ())).setBlock(xInChunk, y, zInChunk, block);
		}
	}

	public void setBlock(BlockPos p, Block b) {
		setBlock(p.getX(), p.getY(), p.getZ(), b);
	}

	public boolean hasBlock(BlockPos p) {
		ChunkPos cp = getChunkPosFromBlock(p.getX(), p.getZ());
		if (isWorldLimited) {
			if (cp.getX() > this.limit_long - 1 || cp.getZ() > this.limit_width - 1 || cp.getX() < 0 || cp.getZ() < 0)
				return false;

			int xInChunk = p.getX() - cp.getX() * Chunk.size;
			int zInChunk = p.getZ() - cp.getZ() * Chunk.size;
			return chunks.get(GLHelper.vert2ToLong(cp.getX(), cp.getZ()))
					.hasBlock(new BlockPos(xInChunk, p.getY(), zInChunk));
		}
		return false;
	}

	public static int chunkRenderDistance = 5;

	public int draw(Player cam, FloatBuffer buffer) {
		int vertCount = 0;
		int camX = (int) cam.pos.x;
		int camZ = (int) cam.pos.z;
		ChunkPos chunkPos = this.getChunkPosFromBlock(camX, camZ);
		for (int chunkX = chunkPos.getX() - chunkRenderDistance; chunkX < chunkPos.getX()
				+ chunkRenderDistance; chunkX++) {
			for (int chunkZ = chunkPos.getZ() - chunkRenderDistance; chunkZ < chunkPos.getZ()
					+ chunkRenderDistance; chunkZ++) {
				if (!GLHelper.isChunkInFrustum(chunkX, Chunk.height, chunkZ))
					continue;
				Chunk c = this.chunks.get(GLHelper.vert2ToLong(chunkX, chunkZ));
				if (c == null)
					continue;
				c.update();
				for (int xInChunk = 0; xInChunk < Chunk.size; xInChunk++) {
					for (int zInChunk = 0; zInChunk < Chunk.size; zInChunk++) {
						int yMax = c.heightMap[xInChunk][zInChunk];
						for (int y = 0; y <= yMax; y++) {
							int x = chunkX * Chunk.size + xInChunk;
							int z = chunkZ * Chunk.size + zInChunk;
							Block block = c.getBlock(xInChunk, y, zInChunk);
							if (block == null)
								continue;
							if (c.getBlock(xInChunk, y - 1, zInChunk) == null) {
								block.method.getDrawData(buffer, x, y, z, 5);
								vertCount += 6;
							}
							if (c.getBlock(xInChunk, y + 1, zInChunk) == null) {
								block.method.getDrawData(buffer, x, y, z, 4);
								vertCount += 6;
							}
							if (xInChunk - 1 < 0 ? this.getBlock(x - 1, y, z) == null
									: c.getBlock(xInChunk - 1, y, zInChunk) == null) {
								block.method.getDrawData(buffer, x, y, z, 3);
								vertCount += 6;
							}
							if (xInChunk + 1 >= Chunk.size ? this.getBlock(x + 1, y, z) == null
									: c.getBlock(xInChunk + 1, y, zInChunk) == null) {
								block.method.getDrawData(buffer, x, y, z, 1);
								vertCount += 6;
							}
							if (zInChunk - 1 < 0 ? this.getBlock(x, y, z - 1) == null
									: c.getBlock(xInChunk, y, zInChunk - 1) == null) {
								block.method.getDrawData(buffer, x, y, z, 0);
								vertCount += 6;
							}
							if (zInChunk + 1 >= Chunk.size ? this.getBlock(x, y, z + 1) == null
									: c.getBlock(xInChunk, y, zInChunk + 1) == null) {
								block.method.getDrawData(buffer, x, y, z, 2);
								vertCount += 6;
							}
						}
					}
				}
			}
		}

		return vertCount;
	}
	
	public void addEntity(Entity entity) {
		ChunkPos chunkPos = getChunkPosFromBlock(entity.pos.x,entity.pos.z);
		chunks.get(GLHelper.vert2ToLong(chunkPos.getX(), chunkPos.getZ())).entities.add(entity);
	}
	
	public void entity() {
		EntityRenderer.render();
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

}
