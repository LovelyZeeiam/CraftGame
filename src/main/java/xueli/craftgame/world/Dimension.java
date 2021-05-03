package xueli.craftgame.world;

import java.util.HashMap;

import org.lwjgl.util.vector.Vector3i;

import xueli.craftgame.init.Blocks;
import xueli.craftgame.renderer.world.WorldRenderer;
import xueli.game.vector.Vector;

public class Dimension {

	HashMap<Vector3i, Chunk> chunks = new HashMap<>();

	private ChunkProvider provider;
	private WorldRenderer renderer;

	Blocks blocks;

	public Dimension(boolean isToBeRenderer, Blocks blocks) {
		this.blocks = blocks;
		if (isToBeRenderer)
			this.renderer = new WorldRenderer(this);
		this.provider = new ChunkProvider(this);

	}

	public Tile getBlock(int x, int y, int z) {
		Chunk chunk = chunks.get(new Vector3i(x >> 4, y >> 4, z >> 4));
		if (chunk == null)
			return null;
		return chunk.getBlock(x - (chunk.getChunkX() << 4), y - (chunk.getChunkY() << 4), z - (chunk.getChunkZ() << 4));
	}

	public void setBlock(int x, int y, int z, Tile tile) {
		Chunk chunk = chunks.get(new Vector3i(x >> 4, y >> 4, z >> 4));
		if (chunk == null)
			return;
		chunk.setBlock(x - (chunk.getChunkX() << 4), y - (chunk.getChunkY() << 4), z - (chunk.getChunkZ() << 4), tile);
	}

	public Chunk getChunk(int x, int y, int z) {
		return chunks.get(new Vector3i(x, y, z));
	}

	public void draw(Vector playerPos) {
		renderer.draw(playerPos);
	}

	public void tick(Vector playerPos) {
		this.provider.tick(playerPos);

	}

	public void close() {
		for (Vector3i v : chunks.keySet()) {
			this.provider.save(v.getX(), v.getY(), v.getZ());
		}
		this.provider.release();
	}

	public WorldRenderer getRenderer() {
		return renderer;
	}

	public Blocks getBlocks() {
		return blocks;
	}

}
