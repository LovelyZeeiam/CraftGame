package xueli.craftgame.client.world;

import com.flowpowered.nbt.CompoundMap;
import org.lwjgl.opengl.GL15;
import xueli.craftgame.block.BlockBase;
import xueli.craftgame.block.BlockFace;
import xueli.craftgame.client.renderer.VertexPointer;
import xueli.craftgame.client.renderer.world.WorldRenderer;
import xueli.craftgame.world.Chunk;
import xueli.craftgame.world.Dimension;
import xueli.game.utils.FloatList;

public class RenderableChunk {

	private WorldRenderer renderer;
	private Dimension dimension;
	
	protected Chunk chunk;
	public boolean needRebuilt = true;
	
	private VertexPointer pointer;
	
	private int chunkX, chunkY, chunkZ;

	public RenderableChunk(Chunk chunk, WorldRenderer renderer) {
		this.chunk = chunk;
		this.renderer = renderer;
		this.dimension = chunk.getDimension();
		
		this.chunkX = chunk.getChunkX();
		this.chunkY = chunk.getChunkY();
		this.chunkZ = chunk.getChunkZ();
		
		this.pointer = new VertexPointer(0, GL15.GL_DYNAMIC_DRAW);
				
	}

	private int vertCount = 0;

	private void callRebuilt() {
		pointer.initDraw();
		vertCount = 0;
		FloatList data = new FloatList(131072);

		for (int x = 0; x < 16; x++) {
			for (int y = 0; y < 16; y++) {
				for (int z = 0; z < 16; z++) {
					BlockBase tile = chunk.grid[x][y][z];
					CompoundMap tag = chunk.tags[x][y][z];
					if (tile == null)
						continue;

					int realX = x + (chunkX << 4);
					int realY = y + (chunkY << 4);
					int realZ = z + (chunkZ << 4);

						if (dimension.getBlock(realX, realY + 1, realZ) == null
								|| (!dimension.getBlock(realX, realY + 1, realZ).isComplete()
								|| dimension.getBlock(realX, realY + 1, realZ).isAlpha())) {
							vertCount += tile.getRenderCubeData(data, realX, realY, realZ,
									BlockFace.TOP, tag,dimension);
						}
						if (dimension.getBlock(realX, realY - 1, realZ) == null
								|| (!dimension.getBlock(realX, realY - 1, realZ).isComplete()
								|| dimension.getBlock(realX, realY - 1, realZ).isAlpha())) {
							vertCount += tile.getRenderCubeData(data, realX, realY, realZ,
									BlockFace.BOTTOM,tag, dimension);
						}
						if (dimension.getBlock(realX + 1, realY, realZ) == null
								|| (!dimension.getBlock(realX + 1, realY, realZ).isComplete()
								|| dimension.getBlock(realX + 1, realY, realZ).isAlpha())) {
							vertCount += tile.getRenderCubeData(data, realX, realY, realZ,
									BlockFace.RIGHT,tag, dimension);
						}
						if (dimension.getBlock(realX - 1, realY, realZ) == null
								|| (!dimension.getBlock(realX - 1, realY, realZ).isComplete()
								|| dimension.getBlock(realX - 1, realY, realZ).isAlpha())) {
							vertCount += tile.getRenderCubeData(data, realX, realY, realZ,
									BlockFace.LEFT, tag,dimension);
						}
						if (dimension.getBlock(realX, realY, realZ + 1) == null
								|| (!dimension.getBlock(realX, realY, realZ + 1).isComplete()
								|| dimension.getBlock(realX, realY, realZ + 1).isAlpha())) {
							vertCount += tile.getRenderCubeData(data, realX, realY, realZ,
									BlockFace.BACK,tag, dimension);
						}
						if (dimension.getBlock(realX, realY, realZ - 1) == null
								|| (!dimension.getBlock(realX, realY, realZ - 1).isComplete()
								|| dimension.getBlock(realX, realY, realZ - 1).isAlpha())) {
							vertCount += tile.getRenderCubeData(data, realX, realY, realZ,
									BlockFace.FRONT,tag, dimension);
						}

				}
			}
		}

		pointer.bufferData(data.getDataPointer());
		data.postDispose();

		pointer.postDraw();

	}
	
	public void draw() {
		if(needRebuilt) {
			callRebuilt();
			needRebuilt = false;
		}

		pointer.initDraw();
		pointer.draw(vertCount);
		pointer.postDraw();

	}
	
	public void delete() {
		pointer.delete();
	}

	public int getChunkX() {
		return chunkX;
	}

	public int getChunkY() {
		return chunkY;
	}

	public int getChunkZ() {
		return chunkZ;
	}
	
}
