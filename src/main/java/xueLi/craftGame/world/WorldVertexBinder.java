package xueLi.craftGame.world;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

public class WorldVertexBinder {

	private static int vbo;

	public static void init() {
		vbo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, 1073741824, GL15.GL_DYNAMIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}

	public static FloatBuffer map() {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		return GL15.glMapBuffer(GL15.GL_ARRAY_BUFFER, GL15.GL_WRITE_ONLY, null).asFloatBuffer();
	}

	public static void draw(int type, int vertex_count) {
		GL15.glUnmapBuffer(GL15.GL_ARRAY_BUFFER);
		GL11.glInterleavedArrays(GL11.GL_T2F_C3F_V3F, 0, 0);
		GL11.glDrawArrays(type, 0, vertex_count);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}

}
