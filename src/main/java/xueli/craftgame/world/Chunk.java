package xueli.craftgame.world;

import xueli.craftgame.block.Block;
import xueli.game.player.PlayerStat;

public class Chunk {

	public static final int SUBCHUNK_COUNT = 8;

	private final World world;
	private final int x, z;

	SubChunk[] subChunks = new SubChunk[SUBCHUNK_COUNT];

	private ChunkMeshBuilder meshBuilder;

	public Chunk(int x, int z, World world) {
		this.x = x;
		this.z = z;
		this.world = world;
		this.meshBuilder = new ChunkMeshBuilder(this);

	}

	public void setBlock(int x, int y, int z, Block block, PlayerStat playerStat) {
		int subChunkY = y >> 4; // 相当于除以16
		SubChunk subChunk = subChunks[subChunkY];

		if (subChunk == null) {
			subChunk = new SubChunk(this, subChunkY);
			subChunks[subChunkY] = subChunk;
		}

		int y_in_subchunk = y % 16;
		subChunk.setBlock(x, y_in_subchunk, z, block);

		meshBuilder.postRebuild();

	}

	public Block getBlock(int x, int y, int z) {
		int subChunkY = y >> 4; // 相当于除以16
		SubChunk subChunk = subChunks[subChunkY];

		if (subChunk == null) {
			return null;
		}

		int y_in_subchunk = y % 16;
		return subChunk.getBlock(x, y_in_subchunk, z);
	}

	public ChunkMeshBuilder getMeshBuilder() {
		return meshBuilder;
	}

	public int getX() {
		return x;
	}

	public int getZ() {
		return z;
	}

	public World getWorld() {
		return world;
	}

}
