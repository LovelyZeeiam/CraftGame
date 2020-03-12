package xueLi.craftGame.world;

import xueLi.craftGame.block.BlockGrass;
import xueLi.craftGame.block.BlockStone;

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
		for (int x = 0; x < Chunk.size; x++) {
			for (int z = 0; z < Chunk.size; z++) {
				int height = (int)(Math.sin(mseed * chunkX * x) * 23 + Math.cos(mseed + chunkZ * z) * 23);
				chunk.heightMap[x][z] = height;
				chunk.setBlock(x, chunk.heightMap[x][z], z, new BlockGrass());
				for(int y = 0;y < height;y++)
					chunk.setBlock(x, y, z, new BlockStone());
			}
		}
		return chunk;
	}
	
	private static double noise(int x) {
		x = (x<<13) ^ x;
		return ( 1.0 - ( (x * (x * x * 15731 + 789221) + 1376312589) & 0x7fffffff) / 1073741824.0); 
	}
	
	

}
