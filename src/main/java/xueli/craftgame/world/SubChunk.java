package xueli.craftgame.world;

import java.util.ArrayList;

import xueli.craftgame.block.Block;

public class SubChunk {

	private static final int AIR = 0;

	ArrayList<Block> namespaces = new ArrayList<Block>();
	int[][][] grid = new int[16][16][16];

	int[][] heightMap = new int[16][16];

	private Chunk belongChunk;
	private int y;

	public SubChunk(Chunk chunk, int y) {
		this.belongChunk = chunk;
		this.y = y;

		this.namespaces.add(null);

	}

	public Block getBlock(int x, int y, int z) {
		int i = grid[x][y][z];
		if (i == AIR)
			return null;
		return namespaces.get(i);
	}

	public void setBlock(int x, int y, int z, Block block) {
		int i = namespaces.indexOf(block);
		if (i == -1) {
			i = namespaces.size();
			namespaces.add(block);

		}

		grid[x][y][z] = i;

		if (y > heightMap[x][z]) {
			if (block == null) {
				for (int yy = y; yy >= 0; yy--) {
					if (this.getBlock(x, yy, z) != null) {
						heightMap[x][z] = yy;
						break;
					}
				}
			} else
				heightMap[x][z] = y;
		}

	}

	public int getY() {
		return y;
	}

	public Chunk getBelongChunk() {
		return belongChunk;
	}

}
