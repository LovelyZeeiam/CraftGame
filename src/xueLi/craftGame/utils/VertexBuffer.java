package xueLi.craftGame.utils;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class VertexBuffer {

	private static int vao;
	private static int vbo;
	private static int tbo;
	private static boolean enableTexture = false;
	private static int cbo;
	private static int vertex_count;

	public static void send(FloatBuffer vertices, FloatBuffer texCoords, int Vertexcount) {
		vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);

		vbo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertices, GL15.GL_DYNAMIC_DRAW);
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
		GL20.glEnableVertexAttribArray(0);

		tbo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, tbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, texCoords, GL15.GL_DYNAMIC_DRAW);
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 0, 0);
		GL20.glEnableVertexAttribArray(1);

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);

		vertex_count = Vertexcount;
		enableTexture = true;
	}

	public static void send(FloatBuffer vertices, int Vertexcount, FloatBuffer color) {
		vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);

		vbo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertices, GL15.GL_DYNAMIC_DRAW);
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
		GL20.glEnableVertexAttribArray(0);

		cbo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, cbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, color, GL15.GL_DYNAMIC_DRAW);
		GL20.glVertexAttribPointer(2, 4, GL11.GL_FLOAT, false, 0, 0);
		GL20.glEnableVertexAttribArray(2);

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);

		vertex_count = Vertexcount;
	}

	public static void send(FloatBuffer vertices, int Vertexcount) {
		vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);

		vbo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertices, GL15.GL_DYNAMIC_DRAW);
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
		GL20.glEnableVertexAttribArray(0);

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);

		vertex_count = Vertexcount;
	}

	public static void bind() {
		GL30.glBindVertexArray(vao);
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(2);
		if (enableTexture)
			GL20.glEnableVertexAttribArray(1);
	}

	public static void draw(int type) {
		GL11.glDrawArrays(type, 0, vertex_count);
	}

	public static void unbind() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(2);
		if (enableTexture)
			GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
	}

	public static void clear() {
		GL30.glDeleteVertexArrays(vao);
		GL15.glDeleteBuffers(vbo);
		GL15.glDeleteBuffers(cbo);
		if (enableTexture)
			GL15.glDeleteBuffers(tbo);
	}

}
