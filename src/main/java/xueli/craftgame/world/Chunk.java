package xueli.craftgame.world;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector3i;

import xueli.craftgame.block.data.BlockFace;
import xueli.game.utils.FloatList;
import xueli.game.vector.Vector;

public class Chunk {

	private int chunkX, chunkY, chunkZ;
	private Dimension dimension;

	Tile[][][] grid = new Tile[16][16][16];
	int[][] heightmap = new int[16][16];

	private ChunkBuffer buffer = new ChunkBuffer();

	public Chunk(int x, int y, int z, Dimension dimension) {
		this.chunkX = x;
		this.chunkY = y;
		this.chunkZ = z;
		this.dimension = dimension;

	}

	public void setBlock(int x, int y, int z, Tile tile) {
		grid[x][y][z] = tile;
		buffer.reportRebuild();

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

	}

	public Tile getBlock(int x, int y, int z) {
		return grid[x][y][z];
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
		private FloatList buffer = new FloatList(32768);
		
		private FloatList bufferAlpha = new FloatList(32768);
		private ArrayList<Vector3i> alphaTiles = new ArrayList<>();
		
		private int vertCount = 0, alphaCount = 0;
		private boolean shouldRebuild = true;
		
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
		
		public void updateBuffer(Vector camPos) {
			alphaCount = 0;
			this.bufferAlpha.clear();
			
			if (shouldRebuild) {
				this.buffer.clear();
				this.alphaTiles.clear();
				vertCount = 0;
				
				for (int x = 0; x < 16; x++) {
					for (int y = 0; y < 16; y++) {
						for (int z = 0; z < 16; z++) {
							Tile tile = grid[x][y][z];
							if (tile == null)
								continue;

							int realX = x + (chunkX << 4);
							int realY = y + (chunkY << 4);
							int realZ = z + (chunkZ << 4);
							
							if(tile.getBase().isAlpha()) {
								this.alphaTiles.add(new Vector3i(realX, realY, realZ));
							} else {
								if (tile.getBase().isComplete()) {
									if (dimension.getBlock(realX, realY + 1, realZ) == null
											|| (!dimension.getBlock(realX, realY + 1, realZ).getBase().isComplete()
													|| dimension.getBlock(realX, realY + 1, realZ).getBase().isAlpha())) {
										vertCount += tile.getBase().getRenderCubeData(buffer, realX, realY, realZ,
												BlockFace.TOP, Color.WHITE, dimension);
									}
									if (dimension.getBlock(realX, realY - 1, realZ) == null
											|| (!dimension.getBlock(realX, realY - 1, realZ).getBase().isComplete()
													|| dimension.getBlock(realX, realY - 1, realZ).getBase().isAlpha())) {
										vertCount += tile.getBase().getRenderCubeData(buffer, realX, realY, realZ,
												BlockFace.BOTTOM, Color.DARK_GRAY, dimension);
									}
									if (dimension.getBlock(realX + 1, realY, realZ) == null
											|| (!dimension.getBlock(realX + 1, realY, realZ).getBase().isComplete()
													|| dimension.getBlock(realX + 1, realY, realZ).getBase().isAlpha())) {
										vertCount += tile.getBase().getRenderCubeData(buffer, realX, realY, realZ,
												BlockFace.RIGHT, Color.GRAY, dimension);
									}
									if (dimension.getBlock(realX - 1, realY, realZ) == null
											|| (!dimension.getBlock(realX - 1, realY, realZ).getBase().isComplete()
													|| dimension.getBlock(realX - 1, realY, realZ).getBase().isAlpha())) {
										vertCount += tile.getBase().getRenderCubeData(buffer, realX, realY, realZ,
												BlockFace.LEFT, Color.GRAY, dimension);
									}
									if (dimension.getBlock(realX, realY, realZ + 1) == null
											|| (!dimension.getBlock(realX, realY, realZ + 1).getBase().isComplete()
													|| dimension.getBlock(realX, realY, realZ + 1).getBase().isAlpha())) {
										vertCount += tile.getBase().getRenderCubeData(buffer, realX, realY, realZ,
												BlockFace.BACK, Color.GRAY, dimension);
									}
									if (dimension.getBlock(realX, realY, realZ - 1) == null
											|| (!dimension.getBlock(realX, realY, realZ - 1).getBase().isComplete()
													|| dimension.getBlock(realX, realY, realZ - 1).getBase().isAlpha())) {
										vertCount += tile.getBase().getRenderCubeData(buffer, realX, realY, realZ,
												BlockFace.FRONT, Color.GRAY, dimension);
									}
								} else {
									vertCount += tile.getBase().getRenderCubeData(buffer, realX, realY, realZ, BlockFace.TOP,
											Color.WHITE, dimension);
									vertCount += tile.getBase().getRenderCubeData(buffer, realX, realY, realZ, BlockFace.BOTTOM,
											Color.DARK_GRAY, dimension);
									vertCount += tile.getBase().getRenderCubeData(buffer, realX, realY, realZ, BlockFace.RIGHT,
											Color.GRAY, dimension);
									vertCount += tile.getBase().getRenderCubeData(buffer, realX, realY, realZ, BlockFace.LEFT,
											Color.GRAY, dimension);
									vertCount += tile.getBase().getRenderCubeData(buffer, realX, realY, realZ, BlockFace.BACK,
											Color.GRAY, dimension);
									vertCount += tile.getBase().getRenderCubeData(buffer, realX, realY, realZ, BlockFace.FRONT,
											Color.GRAY, dimension);
								}
							}

						}
					}
				}

				shouldRebuild = false;
			}
			
			Collections.sort(alphaTiles, new Comparator<Vector3i>() {
				@Override
				public int compare(Vector3i v1, Vector3i v2) {
					Vector3f o1 = new Vector3f(v1.getX() + 0.5f, v1.getY() + 0.5f, v1.getZ() + 0.5f);
					Vector3f o2 = new Vector3f(v2.getX() + 0.5f, v2.getY() + 0.5f, v2.getZ() + 0.5f);
					double d1 =
								Math.abs(o1.getX() - camPos.x) +
								Math.abs(o1.getY() - camPos.y) +
								Math.abs(o1.getZ() - camPos.z)
							;
					double d2 = 
							Math.abs(o2.getX() - camPos.x) +
							Math.abs(o2.getY() - camPos.y) +
							Math.abs(o2.getZ() - camPos.z)
						;
					return d1 > d2 ? 1 : 0;
				}
			});
			for(Vector3i i : alphaTiles) {
				Tile tile = dimension.getBlock(i.getX(), i.getY(), i.getZ());
				if (tile.getBase().isComplete()) {
					if (dimension.getBlock(i.getX(), i.getY() + 1, i.getZ()) == null
							|| (!dimension.getBlock(i.getX(), i.getY() + 1, i.getZ()).getBase().equals(tile.getBase()))) {
						alphaCount += tile.getBase().getRenderCubeData(bufferAlpha, i.getX(), i.getY(), i.getZ(),
								BlockFace.TOP, Color.GRAY, dimension);
					}
					if (dimension.getBlock(i.getX(), i.getY() - 1, i.getZ()) == null
							|| (!dimension.getBlock(i.getX(), i.getY() - 1, i.getZ()).getBase().equals(tile.getBase()))) {
						alphaCount += tile.getBase().getRenderCubeData(bufferAlpha, i.getX(), i.getY(), i.getZ(),
								BlockFace.BOTTOM, Color.GRAY, dimension);
					}
					if (dimension.getBlock(i.getX() + 1, i.getY(), i.getZ()) == null
							|| (!dimension.getBlock(i.getX() + 1, i.getY(), i.getZ()).getBase().equals(tile.getBase()))) {
						alphaCount += tile.getBase().getRenderCubeData(bufferAlpha, i.getX(), i.getY(), i.getZ(),
								BlockFace.RIGHT, Color.GRAY, dimension);
					}
					if (dimension.getBlock(i.getX() - 1, i.getY(), i.getZ()) == null
							|| (!dimension.getBlock(i.getX() - 1, i.getY(), i.getZ()).getBase().equals(tile.getBase()))) {
						alphaCount += tile.getBase().getRenderCubeData(bufferAlpha, i.getX(), i.getY(), i.getZ(),
								BlockFace.LEFT, Color.GRAY, dimension);
					}
					if (dimension.getBlock(i.getX(), i.getY(), i.getZ() + 1) == null
							|| (!dimension.getBlock(i.getX(), i.getY(), i.getZ() + 1).getBase().equals(tile.getBase()))) {
						alphaCount += tile.getBase().getRenderCubeData(bufferAlpha, i.getX(), i.getY(), i.getZ(),
								BlockFace.BACK, Color.GRAY, dimension);
					}
					if (dimension.getBlock(i.getX(), i.getY(), i.getZ() - 1) == null
							|| (!dimension.getBlock(i.getX(), i.getY(), i.getZ() - 1).getBase().equals(tile.getBase()))) {
						alphaCount += tile.getBase().getRenderCubeData(bufferAlpha, i.getX(), i.getY(), i.getZ(),
								BlockFace.FRONT, Color.GRAY, dimension);
					}
				} else {
					alphaCount += tile.getBase().getRenderCubeData(bufferAlpha, i.getX(), i.getY(), i.getZ(), BlockFace.TOP,
							Color.GRAY, dimension);
					alphaCount += tile.getBase().getRenderCubeData(bufferAlpha, i.getX(), i.getY(), i.getZ(), BlockFace.BOTTOM,
							Color.GRAY, dimension);
					alphaCount += tile.getBase().getRenderCubeData(bufferAlpha, i.getX(), i.getY(), i.getZ(), BlockFace.RIGHT,
							Color.GRAY, dimension);
					alphaCount += tile.getBase().getRenderCubeData(bufferAlpha, i.getX(), i.getY(), i.getZ(), BlockFace.LEFT,
							Color.GRAY, dimension);
					alphaCount += tile.getBase().getRenderCubeData(bufferAlpha, i.getX(), i.getY(), i.getZ(), BlockFace.BACK,
							Color.GRAY, dimension);
					alphaCount += tile.getBase().getRenderCubeData(bufferAlpha, i.getX(), i.getY(), i.getZ(), BlockFace.FRONT,
							Color.GRAY, dimension);
				}
			}
			//System.out.println(alphaCount);
			
		}
		
	}

}
