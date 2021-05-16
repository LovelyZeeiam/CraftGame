package xueli.craftgame.world;

import org.lwjgl.util.vector.Vector3i;

import xueli.craftgame.init.Blocks;

public class ChunkGenerator {

	private Dimension dimension;
	private Blocks blocks;

	public ChunkGenerator(Dimension dimension) {
		this.dimension = dimension;
		this.blocks = dimension.blocks;

	}

	private static final int GROUP_SIZE = 8, GROUP_HEIGHT = 6;

	public void genChunk(int x, int y, int z) {
		
		
	}

	public void genSuperFlat(int x, int y, int z) {
		Chunk chunk = new Chunk(x, y, z, dimension);
		if (y == 0) {
			for (int m = 0; m < 16; m++) {
				for (int n = 0; n < 16; n++) {
					chunk.heightmap[m][n] = 15;
					chunk.grid[m][15][n] = new Tile(blocks.getModule("craftgame:grass"));
					for (int j = 0; j < 15; j++)
						chunk.grid[m][j][n] = new Tile(blocks.getModule("craftgame:dirt"));
				}
			}
		} else if (y < 0) {
			for (int m = 0; m < 16; m++) {
				for (int n = 0; n < 16; n++) {
					chunk.heightmap[m][n] = 15;
					for (int j = 0; j < 16; j++)
						chunk.grid[m][j][n] = new Tile(blocks.getModule("craftgame:stone"));
				}
			}
		}
		addChunk(chunk);

	}

	private void addChunk(Chunk chunk) {
		dimension.chunks.put(new Vector3i(chunk.getChunkX(), chunk.getChunkY(), chunk.getChunkZ()), chunk);
	}

}
