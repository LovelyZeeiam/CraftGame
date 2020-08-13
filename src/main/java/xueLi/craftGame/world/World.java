package xueLi.craftGame.world;

import java.util.HashMap;

import xueLi.craftGame.block.Tile;
import xueLi.gamengine.utils.MathUtils;

public class World {

	public volatile HashMap<Long, Chunk> chunks = new HashMap<Long, Chunk>();

	public World() {

	}

	private static Chunk tempChunk;

	public Tile getBlock(int x, int y, int z) {
		ChunkPos cp = getChunkPosFromBlock(x, z);

		int xInChunk = x - cp.getX() << Chunk.size_yiwei;
		int zInChunk = z - cp.getZ() << Chunk.size_yiwei;

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

		int xInChunk = x - cp.getX() << Chunk.size_yiwei;
		int zInChunk = z - cp.getZ() << Chunk.size_yiwei;
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

}
