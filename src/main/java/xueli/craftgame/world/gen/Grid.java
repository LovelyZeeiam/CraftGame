package xueli.craftgame.world.gen;

import xueli.craftgame.world.Tile;

public class Grid {

	Tile[][][] grid = new Tile[16][16][16];
	int[][] heightmap = new int[16][16];

	public Tile[][][] getGrid() {
		return grid;
	}

	public int[][] getHeightmap() {
		return heightmap;
	}

}
