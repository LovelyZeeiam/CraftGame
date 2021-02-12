package xueli.gamengine.utils.renderer;

import org.lwjgl.nanovg.NVGLUFramebuffer;

import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.nanovg.NanoVGGL3.*;

public abstract class FrameViewFace extends Face {

	protected long nvg;
	private NVGLUFramebuffer framebuffer;

	private int width, height;

	public FrameViewFace(long nvg, int width, int height, float[] data) {
		super(data);

		this.nvg = nvg;
		this.width = width;
		this.height = height;

		setTbo(genFrameBufferAndGetTextureID(width, height));

	}

	private int genFrameBufferAndGetTextureID(int width, int height) {
		framebuffer = nvgluCreateFramebuffer(nvg, width, height, NVG_IMAGE_NEAREST);
		assert framebuffer != null;
		return framebuffer.texture();
	}

	@Override
	public void draw() {
		nvgluBindFramebuffer(nvg, framebuffer);

		nvgBeginFrame(nvg, width, height, (float) width / (float) height);
		renderToFrameBuffer();
		nvgEndFrame(nvg);

		nvgluBindFramebuffer(nvg, null);

		super.draw();

	}

	public abstract void renderToFrameBuffer();

	@Override
	public void release() {
		super.release();

		nvgluDeleteFramebuffer(nvg, framebuffer);

	}

}
