package xueli.craftgame.renderer.blocks;

import xueli.craftgame.block.BlockType;
import xueli.craftgame.renderer.WorldRenderer;
import xueli.craftgame.world.SubChunk;
import xueli.craftgame.world.World;

public class ChunkBuilder implements Runnable {

	private int x, z;
	private World world;
	private WorldRenderer renderer;

	private SubChunk chunk;

	public ChunkBuilder(int x, int z, World world, WorldRenderer renderer) {
		this.x = x;
		this.z = z;
		this.world = world;
		this.renderer = renderer;

		this.chunk = world.getChunk(x, z);

	}

	@Override
	public void run() {
		// TODO: Iterate all the BlockRenderable and allow them to call IBlockRenderer
		// "addQuad"
		// then when drawing, sync the buffer and render them
		renderer.getRenderers().forEach(r -> {
			r.getChunkBuffer(x, z).clear();
		});

		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				int maxY = chunk.heightmap[x][z];
				// System.out.println(maxY);
				for (int y = 0; y <= maxY; y++) {
					BlockType block = chunk.grid[x][y][z];
					// System.out.println(x + ", " + +y + ", " + z);

					if (block == null)
						continue;
					BlockRenderable renderable = block.getRenderable();
					if (renderable == null)
						continue;

					int realX = x + (this.x << 4);
					int realY = y;
					int realZ = z + (this.z << 4);

					renderable.render(realX, realY, realZ, renderer);

				}
			}
		}

		renderer.getRenderers().forEach(r -> {
			r.getChunkBuffer(x, z).shouldSyncBuffer = true;
		});

	}
	
	public World getWorld() {
		return world;
	}

}
