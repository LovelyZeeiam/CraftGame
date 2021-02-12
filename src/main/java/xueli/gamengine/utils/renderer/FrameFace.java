package xueli.gamengine.utils.renderer;

import org.lwjgl.opengl.GL11;

import xueli.craftgame.WorldLogic;
import xueli.gamengine.utils.framebuffer.FrameBuffer;

public abstract class FrameFace extends Face {

	private WorldLogic logic;

	private FrameBuffer buffer;

	public FrameFace(int width, int height, float[] data, WorldLogic logic) {
		super(data);
		setTbo(genFrameBufferAndGetTextureID(width, height));

		this.logic = logic;

	}

	private int genFrameBufferAndGetTextureID(int width, int height) {
		this.buffer = new FrameBuffer();
		this.buffer.create(width, height);
		return this.buffer.getTbo();
	}

	private void bindFrameBuffer() {
		buffer.bind();
	}

	private void unbindFrameBuffer() {
		buffer.unbind();
	}

	@Override
	public void draw() {
		bindFrameBuffer();
		GL11.glViewport(0, 0, buffer.getWidth(), buffer.getHeight());

		renderToFrameBuffer();

		unbindFrameBuffer();
		logic.setNormalViewPort();

		super.draw();

	}

	public abstract void renderToFrameBuffer();

}
