package xueli.craftgame.renderer.blocks;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL15;

import xueli.craftgame.CraftGameContext;
import xueli.game.utils.WrappedFloatBuffer;
import xueli.game2.display.GameDisplay;
import xueli.game2.renderer.legacy.RenderMaster;
import xueli.game2.renderer.legacy.buffer.VertexPointer;
import xueli.game2.renderer.legacy.system.RenderSystem;

public class ChunkBuffer {

	private int x, z;
	private IBlockRenderer blockRenderer;

	private RenderMaster renderer;

	public ChunkBuffer(int x, int z, IBlockRenderer blockRenderer) {
		this.x = x;
		this.z = z;
		this.blockRenderer = blockRenderer;

		this.renderer = new RenderMaster();

	}

	public void draw() {
		this.renderer.tick();

	}

	public void release() {
		this.renderer.release();

	}

	public void reportRebuilt() {
		renderer.reportRebuilt();
	}

	public IBlockRenderer getBlockRenderer() {
		return blockRenderer;
	}

	public RenderMaster getRenderer() {
		return renderer;
	}

	public int getZ() {
		return z;
	}

	public int getX() {
		return x;
	}

}
