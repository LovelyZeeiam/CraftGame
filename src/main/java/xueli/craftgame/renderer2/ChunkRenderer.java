package xueli.craftgame.renderer2;

import xueli.craftgame.block.BlockType;
import xueli.craftgame.world.SubChunk;
import xueli.game2.lifecycle.LifeCycle;

import java.util.concurrent.atomic.AtomicBoolean;

public class ChunkRenderer implements LifeCycle {

	private int x, z;
	private SubChunk chunk;

	private AtomicBoolean markNeedRebuilt = new AtomicBoolean(true);

	private WorldRenderer renderer;

	public ChunkRenderer(int x, int z, WorldRenderer renderer) {
		this.x = x;
		this.z = z;
		this.renderer = renderer;

		this.chunk = renderer.getWorld().getChunk(x, z);

	}

	@Override
	public void init() {

	}

	public void reportRebuilt() {
		this.markNeedRebuilt.set(true);
	}

	@Override
	public void tick() {
		if(markNeedRebuilt.get()) {
			markNeedRebuilt.set(false);
			this.rebuilt();
		}

		renderSystem.tick();

	}

	private void rebuilt() {
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				int maxY = chunk.heightmap[x][z];
				// System.out.println(maxY);
				for (int y = 0; y <= maxY; y++) {
					BlockType block = chunk.grid[x][y][z];
					// System.out.println(x + ", " + +y + ", " + z);

					if (block == null)
						continue;



					int realX = x + (this.x << 4);
					int realY = y;
					int realZ = z + (this.z << 4);



				}
			}
		}

	}

	@Override
	public void release() {
		renderSystem.release();

	}

}
