package xueli.gamengine.utils.renderer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;

import java.nio.IntBuffer;

/**
 * 默认都是绘制矩形的面
 */
public class Face {
	
	private static IntBuffer texCoordBuffer;
	
	static {
		texCoordBuffer = BufferUtils.createIntBuffer(8);
		texCoordBuffer.put(new int[] { 1, 1, 1, 0, 0, 0, 0, 1, });
		texCoordBuffer.flip();
		
	}

	private int textureID;
	
	private int vao;
	private int vbo;
	private int tbo;

	// 方便FrameFace类使用
	protected Face(float[] data) {
		dataStoreIntoVao(data);
	}

	protected void setTbo(int tbo) {
		this.tbo = tbo;
	}

	public Face(int textureID, float[] data) {
		this.textureID = textureID;
		dataStoreIntoVao(data);
		
	}
	
	private void dataStoreIntoVao(float[] data) {
		this.vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(this.vao);
		
		this.vbo = GL30.glGenBuffers();
		GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, this.vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
		GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0);

		this.tbo = GL30.glGenBuffers();
		GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, this.tbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, texCoordBuffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 0, 0);
		GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0);

		GL30.glBindVertexArray(0);
		
	}
	
	public void draw() {
		GL30.glBindVertexArray(this.vao);

		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);

		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);

		GL30.glBindVertexArray(0);
		
	}
	
	public void release() {
		GL30.glDeleteBuffers(this.vbo);
		GL30.glDeleteBuffers(this.tbo);
		GL30.glDeleteVertexArrays(this.vao);


	}
	

}
