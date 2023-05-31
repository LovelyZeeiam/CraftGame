package xueli.game2.renderer.ui;

import static org.lwjgl.nanovg.NanoVG.NVG_IMAGE_NEAREST;
import static org.lwjgl.nanovg.NanoVGGL3.nvgluCreateFramebuffer;
import static org.lwjgl.nanovg.NanoVGGL3.nvgluDeleteFramebuffer;

import org.lwjgl.nanovg.NVGLUFramebuffer;

import xueli.gui.paint.FrameBuffer;
import xueli.gui.paint.GraphicDriver;

public class NanoFrameBuffer implements FrameBuffer {

	private final NanoGui gui;
	private int width, height;
	NVGLUFramebuffer rawFramebuffer;

	public NanoFrameBuffer(int width, int height, NanoGui gui) {
		this.gui = gui;
		this.width = width;
		this.height = height;
		this.rawFramebuffer = nvgluCreateFramebuffer(gui.nvg, width, height, NVG_IMAGE_NEAREST);
	}

	@Override
	public void resize(int width, int height) {
		nvgluDeleteFramebuffer(gui.nvg, rawFramebuffer);
		this.rawFramebuffer = nvgluCreateFramebuffer(gui.nvg, width, height, NVG_IMAGE_NEAREST);
		this.width = width;
		this.height = height;
		
	}

	@Override
	public GraphicDriver getGraphicDriver() {
		return this.gui;
	}

	@Override
	public int getImageId() {
		return this.rawFramebuffer.image();
	}

	@Override
	public void release() {
		nvgluDeleteFramebuffer(gui.nvg, rawFramebuffer);
		this.rawFramebuffer = null;
	}
	
	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

}
