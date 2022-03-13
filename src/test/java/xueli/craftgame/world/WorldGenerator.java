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
				for (int j = 0; j <= 4; j++)
					chunk.grid[m][j][n] = Blocks.BLOCK_STONE;
			}
		}

		return chunk;
	}

}
