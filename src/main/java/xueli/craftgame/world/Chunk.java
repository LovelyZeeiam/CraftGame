package xueli.craftgame.world;

import xueli.craftgame.block.BlockData;
import xueli.craftgame.player.PlayerStat;

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

	public void setBlock(int x, int y, int z, String namespace, long detail, PlayerStat playerStat) {
		int subChunkY = y >> 4; // 相当于除以16
		SubChunk subChunk = subChunks[subChunkY];

		if (subChunk == null) {
			subChunk = new SubChunk(this, subChunkY);
			subChunks[subChunkY] = subChunk;
		}

		int y_in_subchunk = y % 16;
		subChunk.setBlock(x, y_in_subchunk, z, namespace, detail);

		meshBuilder.postRebuild();

	}

	public BlockData getBlock(int x, int y, int z) {
		int subChunkY = y >> 4; // 相当于除以16
		SubChunk subChunk = subChunks[subChunkY];

		if (subChunk == null) {
			return null;
		}

		int y_in_subchunk = y % 16;
		return subChunk.getBlockData(x, y_in_subchunk, z);
	}

	public long getDetail(int x, int y, int z) {
		int subChunkY = y >> 4; // 相当于除以16
		SubChunk subChunk = subChunks[subChunkY];

		if (subChunk == null) {
			return 0;
		}

		int y_in_subchunk = y % 16;
		return subChunk.getDetails(x, y_in_subchunk, z);
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
