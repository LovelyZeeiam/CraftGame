package xueli.gamengine.particle;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import xueli.gamengine.utils.renderer.VertexPointer;

public class VertexPointerParticle extends VertexPointer {

	private ParticleManager manager;

	public VertexPointerParticle(int bufferSize, int bufferType, ParticleManager manager) {
		super(bufferSize, bufferType);
		this.manager = manager;

	}

	@Override
	protected void registerVertex() {
		// 颜色
		GL20.glVertexAttribPointer(1, 4, GL11.GL_FLOAT, false, 9 * 4, 3 * 4);
		GL20.glEnableVertexAttribArray(1);
		// 坐标
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 9 * 4, 0 * 4);
		GL20.glEnableVertexAttribArray(0);
		// uv
		GL20.glVertexAttribPointer(2, 2, GL11.GL_FLOAT, false, 9 * 4, 7 * 4);
		GL20.glEnableVertexAttribArray(2);

	}

}
