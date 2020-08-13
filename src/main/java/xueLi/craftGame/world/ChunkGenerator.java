package xueLi.craftGame.world;

import xueLi.craftGame.block.Tile;

public class ChunkGenerator {

	public static Chunk superflat(int chunkX, int chunkZ) {
		Chunk chunk = new Chunk(chunkX, chunkZ);
		for (int x = 0; x < Chunk.size; x++) {
			for (int z = 0; z < Chunk.size; z++) {
				for (int y = 0; y < 4; y++) {
					Tile block = new Tile("grass_block");
					block.pos = new BlockPos(x, y, z);
					chunk.blockState[x][y][z] = block;

				}

				Tile block = new Tile("dirt");
				block.pos = new BlockPos(x, 4, z);
				chunk.blockState[x][4][z] = new Tile("stone");

				chunk.heightMap[x][z] = 4;
			}
		}
		return chunk;
	}

}
