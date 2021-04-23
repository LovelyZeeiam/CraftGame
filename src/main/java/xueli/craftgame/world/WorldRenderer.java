package xueli.craftgame.world;

import java.nio.FloatBuffer;

import xueli.game.utils.VertexPointer;
import xueli.game.utils.math.MatrixHelper;
import xueli.game.vector.Vector;

public class WorldRenderer {
	
	private static int DRAW_DISTANCE = 10;

	private Dimension dimension;
	
	private VertexPointer pointer;
	
	public WorldRenderer(Dimension dimension) {
		this.dimension = dimension;
		this.pointer = new VertexPointer();
		
	}
	
	public void draw(Vector playerPos) {
		int playerInChunkX = (int) playerPos.x >> 4;
		int playerInChunkY = (int) playerPos.y >> 4;
		int playerInChunkZ = (int) playerPos.z >> 4;
		
		pointer.initDraw();
		
		FloatBuffer buffer = pointer.mapBuffer().asFloatBuffer();
		int vertCount = 0;
		
		for(int x = playerInChunkX - DRAW_DISTANCE; x < playerInChunkX + DRAW_DISTANCE; x++) {
			for(int y = playerInChunkY - DRAW_DISTANCE; y < playerInChunkY + DRAW_DISTANCE; y++) {
				for(int z = playerInChunkZ - DRAW_DISTANCE; z < playerInChunkZ + DRAW_DISTANCE; z++) {
					if(!MatrixHelper.isChunkInFrustum(x, y, z))
						continue;
					Chunk chunk = dimension.getChunk(x, y, z);
					if(chunk == null) 
						continue;
					chunk.updateBuffer();
					
					chunk.getBuffer().storeInBuffer(buffer);
					vertCount += chunk.getVertCount();
					
				}
			}
		}
		
		pointer.unmap();
		pointer.draw(vertCount);
		pointer.postDraw();
		
	}

}
