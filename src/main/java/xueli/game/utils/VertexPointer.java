package xueli.game.utils;

import java.nio.ByteBuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class VertexPointer {

	private final int vao, vbo;

	public VertexPointer() {
		// 注册vao, vbo
		vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);

		vbo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, 67108864, GL15.GL_DYNAMIC_DRAW);

		registerVertex();

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

		GL30.glBindVertexArray(0);

	}

	public VertexPointer(int bufferSize, int bufferType) {
		// 注册vao, vbo
		vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);

		vbo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, bufferSize, bufferType);

		registerVertex();

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

		GL30.glBindVertexArray(0);

	}

	protected void registerVertex() {
		// UV
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 8 * 4, 0);
		GL20.glEnableVertexAttribArray(1);
		// 颜色
		GL20.glVertexAttribPointer(2, 3, GL11.GL_FLOAT, false, 8 * 4, 2 * 4);
		GL20.glEnableVertexAttribArray(2);
		// 坐标
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 8 * 4, 5 * 4);
		GL20.glEnableVertexAttribArray(0);

	}

	public void initDraw() {
		GL30.glBindVertexArray(vao);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GLHelper.checkGLError("World: Pre-render");

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

	public void postDraw() {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);
	}

	public void delete() {
		GL30.glDeleteVertexArrays(vao);
		GL15.glDeleteBuffers(vbo);

	}

}
