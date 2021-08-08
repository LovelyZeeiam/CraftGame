package xueli.craftgame.world;

import ch.project.inter.SimplexNoise;
import org.lwjgl.utils.vector.Vector3i;
import xueli.craftgame.init.Blocks;

public class ChunkGenerator {

	private Dimension dimension;
	private Blocks blocks;

	public ChunkGenerator(Dimension dimension) {
		this.dimension = dimension;
		this.blocks = dimension.blocks;

	}

	private void addChunk(Chunk chunk) {
		dimension.chunks.put(new Vector3i(chunk.getChunkX(), chunk.getChunkY(), chunk.getChunkZ()), chunk);
	}

	public void genChunk(int x, int y, int z) {
		if (y == 0) {
			Chunk chunk = new Chunk(x, y, z, dimension);

			for (int xInChunk = 0; xInChunk < 16; xInChunk++) {
				for (int zInChunk = 0; zInChunk < 16; zInChunk++) {
					int maxHeight = (int) ((SimplexNoise.noise((x * 16 + xInChunk) / 32.0f, (z * 16 + zInChunk) / 32.0f)
							+ 1.0f) * 5.0f);

					chunk.grid[xInChunk][maxHeight][zInChunk] = new Tile(blocks.getModule("craftgame:grass"));
					chunk.heightmap[xInChunk][zInChunk] = maxHeight;
					for (int i = 0; i < maxHeight; i++) {
						chunk.grid[xInChunk][i][zInChunk] = new Tile(blocks.getModule("craftgame:dirt"));

					}

				}
			}

			addChunk(chunk);
		} else
			genSuperFlat(x, y, z);

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

}
