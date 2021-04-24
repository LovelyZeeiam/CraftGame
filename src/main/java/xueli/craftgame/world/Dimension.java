package xueli.craftgame.world;

import java.util.HashMap;

import org.lwjgl.util.vector.Vector3i;

import xueli.craftgame.block.Tile;
import xueli.craftgame.renderer.world.WorldRenderer;
import xueli.game.vector.Vector;

public class Dimension {

	private HashMap<Vector3i, Chunk> chunks = new HashMap<>();

	private ChunkGenerator generator;
	private WorldRenderer renderer;

	public Dimension() {
		this.generator = new ChunkGenerator(this);
		this.renderer = new WorldRenderer(this);

	}

	public void requireGenChunk(int x, int y, int z) {
		this.chunks.put(new Vector3i(x, y, z), generator.genChunk(x, y, z));
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

	public WorldRenderer getRenderer() {
		return renderer;
	}

}
