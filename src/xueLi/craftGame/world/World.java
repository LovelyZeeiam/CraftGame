package xueLi.craftGame.world;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import xueLi.craftGame.block.Block;
import xueLi.craftGame.entity.Player;
import xueLi.craftGame.utils.BlockPos;
import xueLi.craftGame.utils.ChunkPos;
import xueLi.craftGame.utils.GLHelper;
import xueLi.craftGame.utils.Vector;

public class World {

	private Map<Long, Chunk> chunks = new HashMap<Long, Chunk>();

	private boolean isWorldLimited = false;
	private int wlimit_long, wlimit_width;
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
			
			if(tempChunk == null || cp.getX() != tempChunk.chunkX || cp.getZ() != tempChunk.chunkZ)
				tempChunk = chunks.get(GLHelper.vert2ToLong(cp.getX(), cp.getZ()));
			if(tempChunk == null)
				return null;
			return tempChunk.getBlock(xInChunk, y, zInChunk);
		}
		return null;
	}

	public void setBlock(BlockPos p, int id) {
		setBlock(p.getX(), p.getY(), p.getZ(), Block.blockDefault.get(id));
	}

	public void setBlock(int x, int y, int z, int id) {
		setBlock(x, y, z, Block.blockDefault.get(id));
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

	private ChunkPos getChunkPosFromBlock(int x, int z) {
		int chunkX = x / 16;
		int chunkZ = z / 16;
		return new ChunkPos(chunkX - (x < 0 ? 1 : 0), chunkZ - (z < 0 ? 1 : 0));
	}

}
