package xueLi.craftGame;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import xueLi.craftGame.world.Chunk;

public class MeshBuilderThread implements Runnable {

	public boolean running = false;

	public int vao, vbo;
	public ByteBuffer mappedBuffer;
	public int vertexCount = 0;

	public static ArrayList<Chunk> updateChunks = new ArrayList<Chunk>();

	public MeshBuilderThread() {
		vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);

		vbo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, 1073741824, GL15.GL_DYNAMIC_DRAW);
		mappedBuffer = GL15.glMapBuffer(GL15.GL_ARRAY_BUFFER, GL15.GL_WRITE_ONLY);

		// UV
		GL20.glVertexAttribPointer(1, 1, GL11.GL_INT, false, 0, 0);
		GL20.glEnableVertexAttribArray(1);
		// 颜色
		GL20.glVertexAttribPointer(2, 3, GL11.GL_BYTE, false, 0, 4);
		GL20.glEnableVertexAttribArray(2);
		// 坐标
		GL20.glVertexAttribPointer(0, 3, GL11.GL_INT, false, 0, 4 + 3);
		GL20.glEnableVertexAttribArray(0);

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

		GL30.glBindVertexArray(0);

		running = true;

	}

	@Override
	public void run() {
	}

}
