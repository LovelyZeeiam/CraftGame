package xueli.craftgame.renderer.blocks;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL15;

import xueli.game.utils.WrappedFloatBuffer;
import xueli.game2.renderer.VertexPointer;

public class ChunkBuffer {

	int x, z;

	VertexPointer pointer;
	WrappedFloatBuffer buffer;
	private int vertCount = 0;
	volatile int tempCount = 0;

	volatile boolean shouldSyncBuffer = false;

	public ChunkBuffer(int x, int z) {
		this.x = x;
		this.z = z;

		this.pointer = new VertexPointer(0, GL15.GL_DYNAMIC_DRAW);
		this.buffer = new WrappedFloatBuffer();

	}

	public void clear() {
		buffer.getBuffer().clear();
		this.tempCount = 0;
	}

	public void draw() {
		pointer.initDraw();
		if (shouldSyncBuffer) {
			FloatBuffer buf = buffer.getBuffer();
			buf.flip();
			pointer.bufferData(buf);
			this.vertCount = tempCount;
			shouldSyncBuffer = false;
		}
		pointer.draw(vertCount);
		pointer.postDraw();

	}

	public void release() {
		buffer.getBuffer().clear();
		buffer = null;

		pointer.delete();

	}

}
