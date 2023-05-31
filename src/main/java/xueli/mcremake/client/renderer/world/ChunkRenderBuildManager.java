package xueli.mcremake.client.renderer.world;

import java.util.HashMap;

import org.lwjgl.utils.vector.Vector2i;

import xueli.game2.renderer.legacy.BackRenderBuffer;

public abstract class ChunkRenderBuildManager {

	private final Vector2i chunkPos;
	private final HashMap<Class<? extends ChunkRenderType>, BackRenderBuffer> backBuffers = new HashMap<>();

	public ChunkRenderBuildManager(Vector2i chunkPos) {
		this.chunkPos = chunkPos;
	}

	public BackRenderBuffer getRenderBuffer(Class<? extends ChunkRenderType> clazz) {
		return backBuffers.computeIfAbsent(clazz, c -> {
			ChunkRenderType type = this.getRenderType(clazz);
			return type.getRenderBuffer(chunkPos).createBackBuffer();
		});
	}

	public abstract <T extends ChunkRenderType> T getRenderType(Class<T> clazz);

	public void flip() {
		backBuffers.values().forEach(BackRenderBuffer::flip);
	}

}
