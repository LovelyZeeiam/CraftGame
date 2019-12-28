package xueLi.craftGame.world;

import java.nio.FloatBuffer;

import xueLi.craftGame.block.Block;
import xueLi.craftGame.utils.BlockPos;
import xueLi.craftGame.utils.Vector;

public class Chunk {

	public static final int size = 128, height = 128;
	public Block[][][] blockState = new Block[size][height][size];

	public Chunk() {
		for(int x = 0;x < size;x++) {
			for(int z = 0;z < size;z++) {
				for(int y = 0;y < 4;y++) {
					blockState[x][y][z] = Block.blockDefault.get(1);
				}
				blockState[x][4][z] = Block.blockDefault.get(2);
			}
			
		}
		
	}

	public void update() {
		
	}
	
	public void setBlock(int x,int y,int z,int id) {
		if(x < 0 || x >= size || y < 0 || y >= height || z < 0 || z >= size)
			return;
		if(id == 0)
			blockState[x][y][z] = null;
		blockState[x][y][z] = Block.blockDefault.get(id);
	}
	
	public void setBlock(BlockPos pos,int id) {
		if(pos.getX() < 0 || pos.getX() >= size || pos.getY() < 0 || pos.getY() >= height || pos.getZ() < 0 || pos.getZ() >= size)
			return;
		if(id == 0)
			blockState[pos.getX()][pos.getY()][pos.getZ()] = null;
		blockState[pos.getX()][pos.getY()][pos.getZ()] = Block.blockDefault.get(id);
	}
	
	public Block getBlock(BlockPos pos) {
		if(pos.getX() < 0 || pos.getX() >= size || pos.getY() < 0 || pos.getY() >= height || pos.getZ() < 0 || pos.getZ() >= size)
			return null;
		return blockState[pos.getX()][pos.getY()][pos.getZ()];
	}
	
	public boolean hasBlock(BlockPos pos) {
		if(pos.getX() < 0 || pos.getX() >= size || pos.getY() < 0 || pos.getY() >= height || pos.getZ() < 0 || pos.getZ() >= size)
			return false;
		return blockState[pos.getX()][pos.getY()][pos.getZ()] != null;
	}
	
	public int draw(FloatBuffer vertices, FloatBuffer texCoords, Vector player_pos) {
		int vertCount = 0;
		for(int x = 0;x < size;x++) {
			for(int y = 0;y < height;y++) {
				for(int z = 0;z < size;z++) {
					Block block = blockState[x][y][z];
					if(block == null)
						continue;
					if(x - 1 < 0 || blockState[x-1][y][z] == null) {
						block.method.getDrawData(vertices, texCoords, x, y, z, 3);
						vertCount+=6;
					}
					if(x + 1 >= size || blockState[x+1][y][z] == null) {
						block.method.getDrawData(vertices, texCoords, x, y, z, 1);
						vertCount+=6;
					}
					if(z - 1 < 0 || blockState[x][y][z-1] == null) {
						block.method.getDrawData(vertices, texCoords, x, y, z, 0);
						vertCount+=6;
					}
					if(z + 1 >= size || blockState[x][y][z+1] == null) {
						block.method.getDrawData(vertices, texCoords, x, y, z, 2);
						vertCount+=6;
					}	
					if(y - 1 < 0 || blockState[x][y-1][z] == null) {
						block.method.getDrawData(vertices, texCoords, x, y, z, 5);
						vertCount+=6;
					}
					if(y + 1 >= height || blockState[x][y+1][z] == null) {
						block.method.getDrawData(vertices, texCoords, x, y, z, 4);
						vertCount+=6;
					}	
					
					
				}
			}
		}
		return vertCount;
	}
	
	

}
