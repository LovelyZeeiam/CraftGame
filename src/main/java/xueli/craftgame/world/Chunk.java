package xueli.craftgame.world;

import org.lwjgl.utils.vector.Vector3i;
import xueli.craftgame.block.BlockFace;
import xueli.game.utils.FloatList;
import xueli.game.utils.Light;
import xueli.game.vector.Vector;

import java.util.ArrayList;

public class Chunk {

	protected int chunkX, chunkY, chunkZ;
	protected Dimension dimension;

	public Tile[][][] grid = new Tile[16][16][16];
	public Light[][][] light = new Light[16][16][16];
	public int[][] heightmap = new int[16][16];

	ChunkBuffer buffer = new ChunkBuffer();

	public boolean somethingHasChanged = true;

	{
		for (int x = 0; x < 16; x++) {
			for (int y = 0; y < 16; y++) {
				for (int z = 0; z < 16; z++) {
					light[x][y][z] = new Light((byte) 15, (byte) 15, (byte) 15, (byte) 15);
				}
			}
		}
	}

	public Chunk(int x, int y, int z, Dimension dimension) {
		this.chunkX = x;
		this.chunkY = y;
		this.chunkZ = z;
		this.dimension = dimension;

	}

	public void setBlock(int x, int y, int z, Tile tile) {
		grid[x][y][z] = tile;

		buffer.reportRebuild();
		new WorldUpdater.LightUpdater(this).run();

		if (y > heightmap[x][z] && tile != null) {
			heightmap[x][z] = y;
		}
		if (y == heightmap[x][z] && tile == null) {
			for (int i = heightmap[x][z]; i >= 0; i--) {
				heightmap[x][z] = i;
				if (getBlock(x, i, z) != null)
					break;
			}
		}

		somethingHasChanged = true;

	}

	public Tile getBlock(int x, int y, int z) {
		return grid[x][y][z];
	}

	public Light getLight(int x, int y, int z) {
		return this.light[x][y][z];
	}

	public int getChunkX() {
		return chunkX;
	}

	public int getChunkY() {
		return chunkY;
	}

	public int getChunkZ() {
		return chunkZ;
	}

	public Dimension getDimension() {
		return dimension;
	}

	public ChunkBuffer getBuffer() {
		return buffer;
	}

	public class ChunkBuffer {
		private FloatList buffer = new FloatList(32768 * 4);

		private FloatList bufferAlpha = new FloatList(32768);
		private FloatList bufferNotNeedCull = new FloatList(8192);
		private ArrayList<Vector3i> alphaTiles = new ArrayList<>();

		private int vertCount = 0, alphaCount = 0, incompleteCount = 0;
		boolean shouldRebuild = true;

		private boolean hasPostRelease = false;

		public ChunkBuffer() {

		}

		public void reportRebuild() {
			this.shouldRebuild = true;
			if (dimension.getChunk(chunkX + 1, chunkY, chunkZ) != null)
				dimension.getChunk(chunkX + 1, chunkY, chunkZ).buffer.shouldRebuild = true;
			if (dimension.getChunk(chunkX - 1, chunkY, chunkZ) != null)
				dimension.getChunk(chunkX - 1, chunkY, chunkZ).buffer.shouldRebuild = true;
			if (dimension.getChunk(chunkX, chunkY + 1, chunkZ) != null)
				dimension.getChunk(chunkX, chunkY + 1, chunkZ).buffer.shouldRebuild = true;
			if (dimension.getChunk(chunkX, chunkY - 1, chunkZ) != null)
				dimension.getChunk(chunkX, chunkY - 1, chunkZ).buffer.shouldRebuild = true;
			if (dimension.getChunk(chunkX, chunkY, chunkZ + 1) != null)
				dimension.getChunk(chunkX, chunkY, chunkZ + 1).buffer.shouldRebuild = true;
			if (dimension.getChunk(chunkX, chunkY, chunkZ - 1) != null)
				dimension.getChunk(chunkX, chunkY, chunkZ - 1).buffer.shouldRebuild = true;

			if (dimension.getChunk(chunkX + 1, chunkY + 1, chunkZ + 1) != null)
				dimension.getChunk(chunkX + 1, chunkY + 1, chunkZ + 1).buffer.shouldRebuild = true;
			if (dimension.getChunk(chunkX + 1, chunkY + 1, chunkZ) != null)
				dimension.getChunk(chunkX + 1, chunkY + 1, chunkZ).buffer.shouldRebuild = true;
			if (dimension.getChunk(chunkX + 1, chunkY + 1, chunkZ - 1) != null)
				dimension.getChunk(chunkX + 1, chunkY + 1, chunkZ - 1).buffer.shouldRebuild = true;

			if (dimension.getChunk(chunkX + 1, chunkY - 1, chunkZ + 1) != null)
				dimension.getChunk(chunkX + 1, chunkY - 1, chunkZ + 1).buffer.shouldRebuild = true;
			if (dimension.getChunk(chunkX + 1, chunkY - 1, chunkZ) != null)
				dimension.getChunk(chunkX + 1, chunkY - 1, chunkZ).buffer.shouldRebuild = true;
			if (dimension.getChunk(chunkX + 1, chunkY - 1, chunkZ - 1) != null)
				dimension.getChunk(chunkX + 1, chunkY - 1, chunkZ - 1).buffer.shouldRebuild = true;

			if (dimension.getChunk(chunkX, chunkY + 1, chunkZ + 1) != null)
				dimension.getChunk(chunkX, chunkY + 1, chunkZ + 1).buffer.shouldRebuild = true;
			if (dimension.getChunk(chunkX, chunkY + 1, chunkZ) != null)
				dimension.getChunk(chunkX, chunkY + 1, chunkZ).buffer.shouldRebuild = true;
			if (dimension.getChunk(chunkX, chunkY + 1, chunkZ - 1) != null)
				dimension.getChunk(chunkX, chunkY + 1, chunkZ - 1).buffer.shouldRebuild = true;

			if (dimension.getChunk(chunkX, chunkY - 1, chunkZ + 1) != null)
				dimension.getChunk(chunkX, chunkY - 1, chunkZ + 1).buffer.shouldRebuild = true;
			if (dimension.getChunk(chunkX, chunkY - 1, chunkZ) != null)
				dimension.getChunk(chunkX, chunkY - 1, chunkZ).buffer.shouldRebuild = true;
			if (dimension.getChunk(chunkX, chunkY - 1, chunkZ - 1) != null)
				dimension.getChunk(chunkX, chunkY - 1, chunkZ - 1).buffer.shouldRebuild = true;

			if (dimension.getChunk(chunkX - 1, chunkY + 1, chunkZ + 1) != null)
				dimension.getChunk(chunkX - 1, chunkY + 1, chunkZ + 1).buffer.shouldRebuild = true;
			if (dimension.getChunk(chunkX - 1, chunkY + 1, chunkZ) != null)
				dimension.getChunk(chunkX - 1, chunkY + 1, chunkZ).buffer.shouldRebuild = true;
			if (dimension.getChunk(chunkX - 1, chunkY + 1, chunkZ - 1) != null)
				dimension.getChunk(chunkX - 1, chunkY + 1, chunkZ - 1).buffer.shouldRebuild = true;

			if (dimension.getChunk(chunkX - 1, chunkY - 1, chunkZ + 1) != null)
				dimension.getChunk(chunkX - 1, chunkY - 1, chunkZ + 1).buffer.shouldRebuild = true;
			if (dimension.getChunk(chunkX - 1, chunkY - 1, chunkZ) != null)
				dimension.getChunk(chunkX - 1, chunkY - 1, chunkZ).buffer.shouldRebuild = true;
			if (dimension.getChunk(chunkX - 1, chunkY - 1, chunkZ - 1) != null)
				dimension.getChunk(chunkX - 1, chunkY - 1, chunkZ - 1).buffer.shouldRebuild = true;

		}

		public FloatList getBuffer() {
			return buffer;
		}

		public int getVertCount() {
			return vertCount;
		}

		public FloatList getBufferAlpha() {
			return bufferAlpha;
		}

		public int getAlphaCount() {
			return alphaCount;
		}

		public FloatList getBufferNotNeedCull() {
			return bufferNotNeedCull;
		}

		public int getIncompleteCount() {
			return incompleteCount;
		}

		public void updateBuffer(Vector camPos) {
			if (shouldRebuild) {
				this.buffer.clear();
				this.alphaTiles.clear();
				vertCount = 0;
				alphaCount = 0;
				incompleteCount = 0;
				this.bufferAlpha.clear();
				this.bufferNotNeedCull.clear();

				for (int x = 0; x < 16; x++) {
					for (int y = 0; y < 16; y++) {
						for (int z = 0; z < 16; z++) {
							Tile tile = grid[x][y][z];
							if (tile == null)
								continue;

							int realX = x + (chunkX << 4);
							int realY = y + (chunkY << 4);
							int realZ = z + (chunkZ << 4);

							FloatList target = tile.getBase().isComplete() ? buffer : bufferNotNeedCull;
							int tempCount = 0;

							if (!tile.getBase().isAlpha()) {
								if (dimension.getBlock(realX, realY + 1, realZ) == null
										|| (!dimension.getBlock(realX, realY + 1, realZ).getBase().isComplete()
												|| dimension.getBlock(realX, realY + 1, realZ).getBase().isAlpha())) {
									tempCount += tile.getBase().getRenderCubeData(target, realX, realY, realZ,
											BlockFace.TOP, dimension);
								}
								if (dimension.getBlock(realX, realY - 1, realZ) == null
										|| (!dimension.getBlock(realX, realY - 1, realZ).getBase().isComplete()
												|| dimension.getBlock(realX, realY - 1, realZ).getBase().isAlpha())) {
									tempCount += tile.getBase().getRenderCubeData(target, realX, realY, realZ,
											BlockFace.BOTTOM, dimension);
								}
								if (dimension.getBlock(realX + 1, realY, realZ) == null
										|| (!dimension.getBlock(realX + 1, realY, realZ).getBase().isComplete()
												|| dimension.getBlock(realX + 1, realY, realZ).getBase().isAlpha())) {
									tempCount += tile.getBase().getRenderCubeData(target, realX, realY, realZ,
											BlockFace.RIGHT, dimension);
								}
								if (dimension.getBlock(realX - 1, realY, realZ) == null
										|| (!dimension.getBlock(realX - 1, realY, realZ).getBase().isComplete()
												|| dimension.getBlock(realX - 1, realY, realZ).getBase().isAlpha())) {
									tempCount += tile.getBase().getRenderCubeData(target, realX, realY, realZ,
											BlockFace.LEFT, dimension);
								}
								if (dimension.getBlock(realX, realY, realZ + 1) == null
										|| (!dimension.getBlock(realX, realY, realZ + 1).getBase().isComplete()
												|| dimension.getBlock(realX, realY, realZ + 1).getBase().isAlpha())) {
									tempCount += tile.getBase().getRenderCubeData(target, realX, realY, realZ,
											BlockFace.BACK, dimension);
								}
								if (dimension.getBlock(realX, realY, realZ - 1) == null
										|| (!dimension.getBlock(realX, realY, realZ - 1).getBase().isComplete()
												|| dimension.getBlock(realX, realY, realZ - 1).getBase().isAlpha())) {
									tempCount += tile.getBase().getRenderCubeData(target, realX, realY, realZ,
											BlockFace.FRONT, dimension);
								}
							} else {
								alphaCount += tile.getBase().getRenderCubeData(bufferAlpha, realX, realY, realZ,
										(byte) -1, dimension);
							}

							if (tile.getBase().isComplete())
								vertCount += tempCount;
							else
								incompleteCount += tempCount;

						}
					}
				}

				shouldRebuild = false;
			}

		}

		public void postRelease() {
			hasPostRelease = true;
			this.buffer.postDispose();
			this.bufferAlpha.postDispose();
		}

		public boolean hasPostRelease() {
			return hasPostRelease;
		}

	}

}
