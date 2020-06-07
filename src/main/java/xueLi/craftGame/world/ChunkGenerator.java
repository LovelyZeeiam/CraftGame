package xueLi.craftGame.world;

import xueLi.craftGame.block.Block;
import xueLi.craftGame.utils.BlockPos;
import xueLi.craftGame.utils.NoiseGenerator;
import xueLi.craftGame.world.biomes.Biome;
import xueLi.craftGame.world.biomes.BiomePlane;

public class ChunkGenerator {

	private static Biome biome = new BiomePlane();

	// 生成一个超平坦 可以在World类的构造器里面找到
	public static Chunk superflat(int chunkX, int chunkZ) {
		Chunk chunk = new Chunk(chunkX, chunkZ);
		for (int x = 0; x < Chunk.size; x++) {
			for (int z = 0; z < Chunk.size; z++) {
				for (int y = 0; y < 4; y++) {
					Block block = new Block(1);
					block.pos = new BlockPos(x, y, z);
					chunk.blockState[x][y][z] = block;

				}

				Block block = new Block(2);
				block.pos = new BlockPos(x, 4, z);
				chunk.blockState[x][4][z] = new Block(2);

				chunk.heightMap[x][z] = 4;
			}
		}
		return chunk;
	}

	public static Chunk gen(int chunkX, int chunkZ, World world) {
		Chunk chunk = new Chunk(chunkX, chunkZ);
		int[][] heightMap = biome.getHeightMap(chunkX, chunkZ, world);
		for (int x = 0; x < Chunk.size; x++) {
			for (int z = 0; z < Chunk.size; z++) {
				int height = heightMap[x][z];
				int topBlockHeight = (int) (height * NoiseGenerator.random.nextFloat());

				for (int y = 0; y < height - topBlockHeight; y++) {
					Block block = new Block(1);
					block.pos = new BlockPos(x, y, z);
					chunk.blockState[x][y][z] = block;
				}
				for (int y2 = height - topBlockHeight; y2 <= height; y2++) {
					Block block = new Block(2);
					block.pos = new BlockPos(x, y2, z);
					chunk.blockState[x][y2][z] = new Block(2);
				}

			}
		}
		chunk.heightMap = heightMap;
		return chunk;
	}

}
