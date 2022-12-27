package xueli.mcremake.classic.client.renderer.world;

import xueli.game.vector.Vector2i;
import xueli.game2.renderer.legacy.BackRenderBuffer;

import java.util.HashMap;

public abstract class ChunkRebuiltManager {

	private final Vector2i chunkPos;
	private final HashMap<Class<? extends ChunkRenderType>, BackRenderBuffer> backBuffers = new HashMap<>();

	public ChunkRebuiltManager(Vector2i chunkPos) {
		this.chunkPos = chunkPos;
	}

	public BackRenderBuffer getRenderBuffer(Class<? extends ChunkRenderType> clazz) {
		return backBuffers.computeIfAbsent(clazz, c -> {
			ChunkRenderType type = this.getRenderType(clazz);
			return type.getRenderBuffer(chunkPos).createBackBuffer();
		});
	}

	protected abstract <T extends ChunkRenderType> T getRenderType(Class<T> clazz);

	public void flip() {
		backBuffers.values().forEach(BackRenderBuffer::flip);
	}

}
