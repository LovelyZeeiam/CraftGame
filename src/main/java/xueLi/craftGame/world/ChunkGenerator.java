package xueLi.craftGame.world;

import xueLi.craftGame.block.Tile;

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
					block.pos = new BlockPos(x, y, z);
					chunk.blockState[x][y][z] = block;

				}

				Tile block = new Tile("grass_block");
				block.pos = new BlockPos(x, 4, z);
				chunk.blockState[x][4][z] = block;

				chunk.heightMap[x][z] = 4;
			}
		}
		return chunk;
	}

}
