package xueLi.craftGame.world;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import xueLi.craftGame.block.Block;
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

	public Block getBlock(int x, int y, int z) {
		ChunkPos cp = getChunkPosFromBlock(x, z);
		if (isWorldLimited) {
			if (cp.getX() > this.limit_long - 1 || cp.getZ() > this.limit_width - 1
					|| cp.getX() < 0 || cp.getZ() < 0)
				return null;

			int xInChunk = x - cp.getX() * Chunk.size;
			int zInChunk = z - cp.getZ() * Chunk.size;
			return chunks.get(GLHelper.vert2ToLong(cp.getX(), cp.getZ())).blockState[xInChunk][y][zInChunk];
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
			if (cp.getX() > this.limit_long - 1 || cp.getZ() > this.limit_width - 1
					|| cp.getX() < 0 || cp.getZ() < 0)
				return;

			int xInChunk = x - cp.getX() * Chunk.size;
			int zInChunk = z - cp.getZ() * Chunk.size;
			chunks.get(GLHelper.vert2ToLong(cp.getX(), cp.getZ())).blockState[xInChunk][y][zInChunk] = block;
		}
	}

	public boolean hasBlock(BlockPos p) {
		ChunkPos cp = getChunkPosFromBlock(p.getX(), p.getZ());
		if (isWorldLimited) {
			if (cp.getX() > this.limit_long - 1 || cp.getZ() > this.limit_width - 1
					|| cp.getX() < 0 || cp.getZ() < 0)
				return false;

			int xInChunk = p.getX() - cp.getX() * Chunk.size;
			int zInChunk = p.getZ() - cp.getZ() * Chunk.size;
			return chunks.get(GLHelper.vert2ToLong(cp.getX(), cp.getZ()))
					 .hasBlock(new BlockPos(xInChunk, p.getY(), zInChunk));
		}
		return false;
	}

	private int renderDistance = 40;

	public int draw(Vector cam, FloatBuffer vertices, FloatBuffer texCoords) {
		int vertCount = 0;
		int camX = (int) cam.x;
		int camZ = (int) cam.z;
		if (isWorldLimited) {
			for (int x = camX - renderDistance; x < camX + renderDistance; x++) {
				for (int y = 0; y < Chunk.height; y++) {
					for (int z = camZ - renderDistance; z < camZ + renderDistance; z++) {
						Block block = this.getBlock(x, y, z);
						if (block == null)
							continue;
						if (x - 1 < 0 || this.getBlock(x - 1, y, z) == null) {
							block.method.getDrawData(vertices, texCoords, x, y, z, 3);
							vertCount += 6;
						}
						if (x + 1 >= wlimit_long || this.getBlock(x + 1, y, z) == null) {
							block.method.getDrawData(vertices, texCoords, x, y, z, 1);
							vertCount += 6;
						}
						if (z - 1 < 0 || this.getBlock(x, y, z - 1) == null) {
							block.method.getDrawData(vertices, texCoords, x, y, z, 0);
							vertCount += 6;
						}
						if (z + 1 >= wlimit_width || this.getBlock(x, y, z + 1) == null) {
							block.method.getDrawData(vertices, texCoords, x, y, z, 2);
							vertCount += 6;
						}
						if (y - 1 < 0 || this.getBlock(x, y - 1, z) == null) {
							block.method.getDrawData(vertices, texCoords, x, y, z, 5);
							vertCount += 6;
						}
						if (y + 1 >= Chunk.height || this.getBlock(x, y + 1, z) == null) {
							block.method.getDrawData(vertices, texCoords, x, y, z, 4);
							vertCount += 6;
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
