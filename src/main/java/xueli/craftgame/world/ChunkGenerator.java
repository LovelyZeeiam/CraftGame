package xueli.craftgame.world;

import xueli.craftgame.block.Tile;

public class ChunkGenerator {

	private World world;

	public ChunkGenerator(World world) {
		this.world = world;

	}

	public Chunk superflat(int chunkX, int chunkZ) {
		Chunk chunk = new Chunk(chunkX, chunkZ, world);
		for (int x = 0; x < Chunk.size; x++) {
			for (int z = 0; z < Chunk.size; z++) {
				for (int y = 0; y < 4; y++) {
					Tile block = new Tile("stone");
					// block.pos = new BlockPos(x, y, z);
					chunk.blockState[x][y][z] = block;

				}

				Tile block = new Tile("grass_block");
				// block.pos = new BlockPos(x, 4, z);
				chunk.blockState[x][4][z] = block;

				chunk.heightMap[x][z] = 4;
			}
		}
		return chunk;
	}

	public Chunk normal(int chunkX, int chunkZ) {
		Chunk chunk = new Chunk(chunkX, chunkZ, world);
		for (int x = 0; x < Chunk.size; x++) {
			for (int z = 0; z < Chunk.size; z++) {
				int y_max = Math.abs((x + z + chunkX * Chunk.size + chunkZ * Chunk.size) / 10);

				for (int y = 0; y < y_max; y++) {
					Tile block = new Tile("stone");
					chunk.blockState[x][y][z] = block;

				}

				Tile block = new Tile("grass_block");
				chunk.blockState[x][y_max][z] = block;

				chunk.heightMap[x][z] = y_max;
			}
		}
		return chunk;
	}

}
