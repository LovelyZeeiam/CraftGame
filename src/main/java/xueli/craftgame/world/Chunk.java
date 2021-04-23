package xueli.craftgame.world;

import java.awt.Color;

import xueli.craftgame.block.Tile;
import xueli.craftgame.block.data.BlockFace;
import xueli.game.utils.FloatList;

public class Chunk {

	private int chunkX, chunkY, chunkZ;
	private Dimension dimension;
	
	Tile[][][] grid = new Tile[16][16][16];
	int[][] heightmap = new int[16][16];
	
	private FloatList buffer = new FloatList(32768);
	private int vertCount = 0;
	private boolean shouldRebuild = true;
	
	public Chunk(int x, int y, int z, Dimension dimension) {
		this.chunkX = x;
		this.chunkY = y;
		this.chunkZ = z;
		this.dimension = dimension;
		
	}
	
	public void setBlock(int x, int y, int z, Tile tile) {
		grid[x][y][z] = tile;
		reportRebuild();
		
		if(y > heightmap[x][z] && tile != null) {
			heightmap[x][z] = y; 
		}
		if(y == heightmap[x][z] && tile == null) {
			for(int i = heightmap[x][z]; i >= 0;i--) {
				heightmap[x][z] = i;
				if(getBlock(x, i, z) != null) break;
			}
		}
		
	}
	
	public Tile getBlock(int x, int y, int z) {
		return grid[x][y][z];
	}
	
	private void reportRebuild() {
		this.shouldRebuild = true;
		if(dimension.getChunk(chunkX + 1, chunkY, chunkZ) != null)
			dimension.getChunk(chunkX + 1, chunkY, chunkZ).shouldRebuild = true;
		if(dimension.getChunk(chunkX - 1, chunkY, chunkZ) != null)
			dimension.getChunk(chunkX - 1, chunkY, chunkZ).shouldRebuild = true;
		if(dimension.getChunk(chunkX, chunkY + 1, chunkZ) != null)
			dimension.getChunk(chunkX, chunkY + 1, chunkZ).shouldRebuild = true;
		if(dimension.getChunk(chunkX, chunkY - 1, chunkZ) != null)
			dimension.getChunk(chunkX, chunkY - 1, chunkZ).shouldRebuild = true;
		if(dimension.getChunk(chunkX, chunkY, chunkZ + 1) != null)
			dimension.getChunk(chunkX, chunkY, chunkZ + 1).shouldRebuild = true;
		if(dimension.getChunk(chunkX, chunkY, chunkZ - 1) != null)
			dimension.getChunk(chunkX, chunkY, chunkZ - 1).shouldRebuild = true;
	}
	
	public void updateBuffer() {
		if(shouldRebuild) {
			this.buffer.clear();
			vertCount = 0;
			
			for(int x = 0; x < 16; x++) {
				for(int y = 0; y < 16; y++) {
					for(int z = 0; z < 16; z++) {
						Tile tile = grid[x][y][z];
						if(tile == null)
							continue;
						
						int realX = x + (chunkX << 4);
						int realY = y + (chunkY << 4);
						int realZ = z + (chunkZ << 4);
						
						if(dimension.getBlock(realX, realY + 1, realZ) == null) {
							vertCount += tile.getBase().getRenderCubeData(buffer, realX, realY, realZ, BlockFace.TOP, Color.WHITE);
						}
						if(dimension.getBlock(realX, realY - 1, realZ) == null) {
							vertCount += tile.getBase().getRenderCubeData(buffer, realX, realY, realZ, BlockFace.BOTTOM, Color.DARK_GRAY);
						}
						if(dimension.getBlock(realX + 1, realY, realZ) == null) {
							vertCount += tile.getBase().getRenderCubeData(buffer, realX, realY, realZ, BlockFace.RIGHT, Color.LIGHT_GRAY);
						}
						if(dimension.getBlock(realX - 1, realY, realZ) == null) {
							vertCount += tile.getBase().getRenderCubeData(buffer, realX, realY, realZ, BlockFace.LEFT, Color.GRAY);
						}
						if(dimension.getBlock(realX, realY, realZ + 1) == null) {
							vertCount += tile.getBase().getRenderCubeData(buffer, realX, realY, realZ, BlockFace.BACK, Color.LIGHT_GRAY);
						}
						if(dimension.getBlock(realX, realY, realZ - 1) == null) {
							vertCount += tile.getBase().getRenderCubeData(buffer, realX, realY, realZ, BlockFace.FRONT, Color.GRAY);
						}
						
					}
				}
			}
			
			//System.out.println(vertCount);
			
			shouldRebuild = false;
		}
		
	}
	
	public FloatList getBuffer() {
		return buffer;
	}
	
	public int getVertCount() {
		return vertCount;
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
	
}
