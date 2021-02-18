package xueli.craftgame.world;

import java.util.ArrayList;

import xueli.craftgame.CraftGame;
import xueli.craftgame.block.BlockData;

public class SubChunk {

	private static final int AIR = -1;

	ArrayList<String> namespaces = new ArrayList<String>();
	int[][][] grid = new int[16][16][16];
	long[][][] details = new long[16][16][16];

	int[][] heightMap = new int[16][16];

	private Chunk belongChunk;
	private int y;

	public SubChunk(Chunk chunk, int y) {
		this.belongChunk = chunk;
		this.y = y;

	}

	public BlockData getBlockData(int x, int y, int z) {
		int i = grid[x][y][z];
		if (i == AIR)
			return null;
		return CraftGame.INSTANCE_CRAFT_GAME.getBlockResource().getBlockData(namespaces.get(i));
	}

	public long getDetails(int x, int y, int z) {
		return details[x][y][z];
	}

	public void setBlock(int x, int y, int z, String namespace, long detail) {
		int i = namespaces.indexOf(namespace);
		if (i == -1) {
			i = namespaces.size();
			namespaces.add(namespace);
		}

		grid[x][y][z] = i;
		details[x][y][z] = detail;

		if (y > heightMap[x][z]) {
			if (namespace == null) {
				for (int yy = y; yy >= 0; yy--) {
					if (this.getBlockData(x, yy, z) != null) {
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
