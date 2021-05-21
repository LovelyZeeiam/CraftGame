package xueli.craftgame.renderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class TestVertexPointer extends VertexPointer {

	public TestVertexPointer() {
		super();
	}

	public TestVertexPointer(int bufferSize, int bufferType) {
		super(bufferSize, bufferType);
	}

	@Override
	protected void registerVertex() {
		// UV
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 5 * 4, 0);
		GL20.glEnableVertexAttribArray(1);
		// 坐标
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 5 * 4, 2 * 4);
		GL20.glEnableVertexAttribArray(0);
	}

}
