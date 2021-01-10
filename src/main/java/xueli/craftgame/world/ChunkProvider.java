package xueli.craftgame.world;

import xueli.gamengine.utils.MathUtils;

public class ChunkProvider {

	private static int generateDistance = 5;

	private World world;
	private ChunkGenerator chunkGenerator;

	public ChunkProvider(World world) {
		this.world = world;
		this.chunkGenerator = new ChunkGenerator(world);

	}

	public void postGenChunk(int chunkX, int chunkY) {
		world.chunks.put(MathUtils.vert2ToLong(chunkX, chunkY), chunkGenerator.normal(chunkX, chunkY));

	}

	public void postStop() {

	}
}
