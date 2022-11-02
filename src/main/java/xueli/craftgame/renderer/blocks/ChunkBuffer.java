package xueli.craftgame.renderer.blocks;

import org.lwjgl.opengl.GL11;
import xueli.craftgame.renderer.WorldRenderer;
import xueli.craftgame.renderer.blocks.buffer.BufferProvider;
import xueli.craftgame.renderer.blocks.buffer.IBufferProvider;
import xueli.game2.renderer.legacy.system.RenderState;
import xueli.game2.renderer.legacy.system.RenderSystem;

import java.util.concurrent.ConcurrentHashMap;

public class ChunkBuffer {

	private int x, z;
	private IBlockRenderer blockRenderer;
	private WorldRenderer worldRenderer;

	private ConcurrentHashMap<Integer, BufferProvider> texturesSystems = new ConcurrentHashMap<>();

	public ChunkBuffer(int x, int z, IBlockRenderer blockRenderer) {
		this.x = x;
		this.z = z;
		this.blockRenderer = blockRenderer;

		this.worldRenderer = blockRenderer.getManager();

	}

	public void clear() {
		try {
			worldRenderer.getThreadSafeExecutor().execute(this::release).call();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		texturesSystems.clear();
	}

	public IBufferProvider getProvider(int textureId) {
		return texturesSystems.computeIfAbsent(textureId, id -> {
			try {
				return worldRenderer.getThreadSafeExecutor().execute(() -> new BufferProvider(RenderSystem.withState(RenderState.easyState(blockRenderer.getShader(), GL11.GL_TRIANGLES, id)))).call();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}

	public void draw() {
		texturesSystems.values().forEach(BufferProvider::tick);
	}

	public void release() {
		texturesSystems.values().forEach(BufferProvider::release);
	}

	public WorldRenderer getWorldRenderer() {
		return worldRenderer;
	}
}
