package xueLi.craftGame.world;

import java.util.Random;

import xueLi.craftGame.block.BlockGrass;
import xueLi.craftGame.block.BlockStone;
import xueLi.craftGame.utils.NoiseGenerator;

public class ChunkGenerator {

	public static Chunk superflat(int chunkX, int chunkZ) {
		Chunk chunk = new Chunk(chunkX,chunkZ);
		for (int x = 0; x < Chunk.size; x++) {
			for (int z = 0; z < Chunk.size; z++) {
				for (int y = 0; y < 4; y++) {
					chunk.blockState[x][y][z] = new BlockStone();
				}
				chunk.blockState[x][4][z] = new BlockGrass();
				chunk.heightMap[x][z] = 4;
			}
		}
		return chunk;
	}
	
	private static long mseed;
	
	public static void setSeed(long seed) {
		mseed = seed;
	}
	
	public static Chunk gen(int chunkX,int chunkZ) {
		Chunk chunk = new Chunk(chunkX,chunkZ);
		//NoiseGenerator n = new NoiseGenerator(mseed);
		for (int x = 0; x < Chunk.size; x++) {
			for (int z = 0; z < Chunk.size; z++) {
				//int height = (int)(n.noise(chunkX * 16 + x, 1, chunkZ * 16 + z));
				//chunk.heightMap[x][z] = height;
				int height = 5;
				chunk.setBlock(x, height, z, new BlockGrass());
				for(int y = 0;y < height;y++)
					chunk.setBlock(x, y, z, new BlockStone());
			}
		}
		return chunk;
	}
	

	
	

}
