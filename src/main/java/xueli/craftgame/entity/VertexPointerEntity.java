package xueli.craftgame.entity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import xueli.craftgame.renderer.VertexPointer;

public class VertexPointerEntity extends VertexPointer {

	public VertexPointerEntity(int bufferSize, int bufferType) {
		super(bufferSize, bufferType);

	}

	@Override
	protected void registerVertex() {
		// 坐标
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 5 * 4, 0 * 4);
		GL20.glEnableVertexAttribArray(0);
		// uv
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 5 * 4, 3 * 4);
		GL20.glEnableVertexAttribArray(1);

	}

}
