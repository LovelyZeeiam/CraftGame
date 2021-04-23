package xueli.craftgame.world;

import xueli.craftgame.block.Tile;
import xueli.craftgame.init.Blocks;
import xueli.craftgame.state.StateWorld;

public class ChunkGenerator {

	private Dimension dimension;
	private Blocks blocks;
	
	public ChunkGenerator(Dimension dimension) {
		this.dimension = dimension;
		this.blocks = StateWorld.getInstance().getBlocks();
		
	}

	public Chunk genChunk(int x, int y, int z) {
		Chunk chunk = new Chunk(x, y, z, dimension);
		if(y == 0) {
			for(int m = 0; m < 16; m++) {
				for(int n = 0; n < 16; n++) {
					chunk.heightmap[m][n] = 15;
					chunk.grid[m][15][n] = new Tile(blocks.getBlockBase("craftgame:grass"));
					for(int j = 0; j < 15; j++) chunk.grid[m][j][n] = new Tile(blocks.getBlockBase("craftgame:dirt"));
				}
			}
		} else if(y < 0) {
			for(int m = 0; m < 16; m++) {
				for(int n = 0; n < 16; n++) {
					chunk.heightmap[m][n] = 15;
					for(int j = 0; j < 16; j++) chunk.grid[m][j][n] = new Tile(blocks.getBlockBase("craftgame:stone"));
				}
			}
		}
		return chunk;
	}
	
}
