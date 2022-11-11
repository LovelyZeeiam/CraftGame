package xueli.game2.renderer.legacy;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import xueli.game.utils.GLHelper;
import xueli.game2.renderer.legacy.buffer.BufferStorable;
import xueli.game2.renderer.legacy.buffer.LotsOfByteBuffer;

@Deprecated
public class DefaultRenderBuffer implements RenderBuffer {

	private final ShapeType shapeType;

	private final LotsOfByteBuffer buf;
	private final DefaultVertexPointer vertexPointer;

	public DefaultRenderBuffer(ShapeType shapeType) {
		this.shapeType = shapeType;

		this.vertexPointer = new DefaultVertexPointer(0, GL15.GL_DYNAMIC_DRAW);
		this.buf = new LotsOfByteBuffer(6000);

	}

	private int acceptTime = 0;

	@Override
	public void reset() {
		this.buf.clear();
		this.acceptTime = 0;

	}

	@Override
	public void sync() {
	}

	@Override
	public void acceptVertex(BufferStorable storable) {
		storable.store(this.buf);
		this.acceptTime++;

	}

	@Override
	public void render() {
		this.vertexPointer.bind();
		this.vertexPointer.bufferData(this.buf.getBuffer());
		this.vertexPointer.draw(this.shapeType.getGLValue(), 0, this.acceptTime / 3);
		this.vertexPointer.unbind();

	}

	@Override
	public void release() {
		this.vertexPointer.delete();

	}

	public static class DefaultVertexPointer {

		private int glUsage = GL15.GL_DYNAMIC_DRAW;
		private final int vao, vbo;

		public DefaultVertexPointer() {
			vao = GL30.glGenVertexArrays();
			GL30.glBindVertexArray(vao);

			vbo = GL15.glGenBuffers();
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, 67108864, glUsage);

			registerVertex();

			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

			GL30.glBindVertexArray(0);

		}

		public DefaultVertexPointer(int bufferSize, int bufferType) {
			this.glUsage = bufferType;

			vao = GL30.glGenVertexArrays();
			GL30.glBindVertexArray(vao);

			vbo = GL15.glGenBuffers();
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
			if (bufferSize > 0)
				GL15.glBufferData(GL15.GL_ARRAY_BUFFER, bufferSize, bufferType);

			registerVertex();

			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

			GL30.glBindVertexArray(0);

		}

		protected void registerVertex() {
			// Vertex
			GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 9 * 4, 0 * 4);
			GL20.glEnableVertexAttribArray(0);
			// UV
			GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 9 * 4, 3 * 4);
			GL20.glEnableVertexAttribArray(1);
			// Color
			GL20.glVertexAttribPointer(2, 4, GL11.GL_FLOAT, false, 9 * 4, 5 * 4);
			GL20.glEnableVertexAttribArray(2);

		}

		public void bind() {
			GL30.glBindVertexArray(vao);
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
			// System.out.println(vao +", " + vbo);

			GLHelper.checkGLError("World: Pre-render");

		}

		public void bufferData(float[] data) {
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data, glUsage);
		}

		public void bufferData(FloatBuffer data) {
			// System.out.println(vao + ", " + vbo);
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data, glUsage);
		}

		public void bufferData(ByteBuffer data) {
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data, glUsage);
		}

		public ByteBuffer mapBuffer() {
			ByteBuffer buffer = GL15.glMapBuffer(GL15.GL_ARRAY_BUFFER, GL15.GL_WRITE_ONLY, null);
			if (buffer == null) {
				throw new RuntimeException("Buffer map error!");
			}
			buffer.clear();
			buffer.position(0);
			return buffer;
		}

		public void unmap() {
			GL15.glUnmapBuffer(GL15.GL_ARRAY_BUFFER);
		}

		public void draw(int vertCount) {
			GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertCount);
		}

		public void draw(int type, int offset, int vertCount) {
			GL11.glDrawArrays(type, offset, vertCount);
		}

		public void unbind() {
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
			GL30.glBindVertexArray(0);
		}

		public void delete() {
			GL30.glDeleteVertexArrays(vao);
			GL15.glDeleteBuffers(vbo);

		}

	}
}
