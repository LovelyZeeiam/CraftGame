package xueLi.craftGame.world;

import java.util.HashMap;

import xueLi.craftGame.block.Tile;
import xueLi.gamengine.utils.MathUtils;

public class World {

	volatile HashMap<Long, Chunk> chunks = new HashMap<Long, Chunk>();

	public World() {

	}

	private static Chunk tempChunk;

	public Tile getBlock(int x, int y, int z) {
		ChunkPos cp = getChunkPosFromBlock(x, z);

		int xInChunk = x - cp.getX() * Chunk.size;
		int zInChunk = z - cp.getZ() * Chunk.size;

		if (tempChunk == null || cp.getX() != tempChunk.chunkX || cp.getZ() != tempChunk.chunkZ)
			tempChunk = chunks.get(MathUtils.vert2ToLong(cp.getX(), cp.getZ()));
		if (tempChunk == null)
			return null;
		return tempChunk.getBlock(xInChunk, y, zInChunk);
	}

	public void setBlock(int x, int y, int z, Tile block) {
		ChunkPos cp = getChunkPosFromBlock(x, z);
		Chunk chunk = chunks.get(MathUtils.vert2ToLong(cp.getX(), cp.getZ()));
		if (chunk == null)
			return;

		int xInChunk = x - cp.getX() * Chunk.size;
		int zInChunk = z - cp.getZ() * Chunk.size;
		if (block == null) {
			Tile tile = chunk.getBlock(xInChunk, y, zInChunk);
			tile.getListener().onDestroy(tile, chunk, this);

		} else {
			block.getListener().onCreate(block, chunk, this);

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

	public void onRightClickOnBlock(BlockPos pos) {
		Tile block = getBlock(pos.getX(), pos.getY(), pos.getZ());
		block.getListener().onRightClick(block, tempChunk, this);

	}

	private ChunkPos getChunkPosFromBlock(int x, int z) {
		int chunkX = (int) (Math.floor((float) x / Chunk.size));
		int chunkZ = (int) (Math.floor((float) z / Chunk.size));
		return new ChunkPos(chunkX, chunkZ);
	}

	private ChunkPos getChunkPosFromBlock(float x, float z) {
		int chunkX = (int) (x / 16);
		int chunkZ = (int) (z / 16);
		return new ChunkPos(chunkX, chunkZ);
	}

}
