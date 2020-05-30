package xueLi.craftGame.world;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;

import xueLi.craftGame.utils.Display;

public class WorldVertexBinder {

	private static int vao, vbo;

	public static WorldShader shader;

	public static void init() {
		shader = new WorldShader();

		useShader();	
		WorldVertexBinder.shader.setProjMatrix(Display.d_width, Display.d_height, 90.0f);

		vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);

		vbo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, 2359296L * World.chunkRenderDistance * World.chunkRenderDistance,
				GL15.GL_DYNAMIC_DRAW);

		// UV
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 8 * 4, 0);
		GL20.glEnableVertexAttribArray(1);
		// 颜色
		GL20.glVertexAttribPointer(2, 3, GL11.GL_FLOAT, false, 8 * 4, 2 * 4);
		GL20.glEnableVertexAttribArray(2);
		// 坐标
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 8 * 4, 5 * 4);
		GL20.glEnableVertexAttribArray(0);

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

		GL30.glBindVertexArray(0);
		unbindShader();
	}

	public static FloatBuffer map() {
		GL30.glBindVertexArray(vao);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		return GL15.glMapBuffer(GL15.GL_ARRAY_BUFFER, GL15.GL_WRITE_ONLY, null).asFloatBuffer();
	}

	public static void unmap() {
		GL15.glUnmapBuffer(GL15.GL_ARRAY_BUFFER);
	}

	public static void useShader() {
		shader.use();
	}

	public static void setSkyColor(Vector3f color) {
		shader.setSkyColor(color);
	}

	public static void draw(int vertex_count) {
		// GL11.glInterleavedArrays(GL11.GL_T2F_C3F_V3F, 0, 0);
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertex_count);
	}

	public static void unbindShader() {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);
		shader.unbind();
	}

	public static void delete() {
		GL15.glDeleteBuffers(vbo);
		shader.release();

	}

}
