package xueLi.craftGame.world;

import xueLi.craftGame.block.Block;

public class ChunkGenerator {

	//生成一个超平坦 可以在World类的构造器里面找到
	public static Chunk superflat(int chunkX, int chunkZ) {
		Chunk chunk = new Chunk(chunkX,chunkZ);
		for (int x = 0; x < Chunk.size; x++) {
			for (int z = 0; z < Chunk.size; z++) {
				for (int y = 0; y < 4; y++) {
					chunk.blockState[x][y][z] = new Block(1);
					
				}
				chunk.blockState[x][4][z] = new Block(2);
				
				chunk.heightMap[x][z] = 4;
			}
		}
		return chunk;
	}
	
	//这里是世界生成有关的 但没有使用
	public static Chunk gen(int chunkX,int chunkZ) {
		Chunk chunk = new Chunk(chunkX,chunkZ);
		for (int x = 0; x < Chunk.size; x++) {
			for (int z = 0; z < Chunk.size; z++) {
				
				for(int y = 0;y < Chunk.height;y++)
				{
					
				}
					
			}
		}
		return chunk;
	}
	

	
	

}
