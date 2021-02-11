package xueli.gamengine.particle;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import xueli.gamengine.utils.renderer.Renderer;

public class ParticleRenderer extends Renderer {

	public ParticleRenderer() {
		super();
	}

	public ParticleRenderer(int bufferSize, int bufferType) {
		super(bufferSize, bufferType);
	}

	@Override
	protected void registerVertex() {
		// 颜色
		GL20.glVertexAttribPointer(1, 3, GL11.GL_FLOAT, false, 6 * 4, 3 * 4);
		GL20.glEnableVertexAttribArray(1);
		// 坐标
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 6 * 4, 0 * 4);
		GL20.glEnableVertexAttribArray(0);
	}

}
