package xueli.mcremake.classic.world;

import xueli.game.vector.Vector2i;

import java.util.HashMap;

public class WorldDimension {

	private final HashMap<Vector2i, Chunk> chunkMap = new HashMap<>();
	private final ChunkProvider provider;

	public WorldDimension(ChunkProvider provider) {
		this.provider = provider;
	}



}
