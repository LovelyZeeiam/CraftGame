package xueli.craftgame.world;

import xueli.craftgame.block.Blocks;

public class WorldGenerator {

	private World world;

	public WorldGenerator(World world) {
		this.world = world;

	}

	public SubChunk genChunk(int x, int z) {
		SubChunk chunk = new SubChunk(x, z, world);

		for (int m = 0; m < 16; m++) {
			for (int n = 0; n < 16; n++) {
				chunk.heightmap[m][n] = 4;
				chunk.grid[m][0][n] = Blocks.BLOCK_BEDROCK;
				chunk.grid[m][4][n] = Blocks.BLOCK_GRASS;
				for (int j = 1; j <= 3; j++)
					chunk.grid[m][j][n] = Blocks.BLOCK_DIRT;
			}
		}

		return chunk;
	}

}
