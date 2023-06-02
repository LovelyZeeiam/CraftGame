package xueli.game2.renderer.ui;

import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_BASELINE;
import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_BOTTOM;
import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_CENTER;
import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_LEFT;
import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_MIDDLE;
import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_RIGHT;
import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_TOP;
import static org.lwjgl.nanovg.NanoVG.NVG_IMAGE_NEAREST;
import static org.lwjgl.nanovg.NanoVG.nvgBeginFrame;
import static org.lwjgl.nanovg.NanoVG.nvgBeginPath;
import static org.lwjgl.nanovg.NanoVG.nvgCircle;
import static org.lwjgl.nanovg.NanoVG.nvgCreateFontMem;
import static org.lwjgl.nanovg.NanoVG.nvgCreateImageMem;
import static org.lwjgl.nanovg.NanoVG.nvgEndFrame;
import static org.lwjgl.nanovg.NanoVG.nvgFill;
import static org.lwjgl.nanovg.NanoVG.nvgFillColor;
import static org.lwjgl.nanovg.NanoVG.nvgFillPaint;
import static org.lwjgl.nanovg.NanoVG.nvgFontFaceId;
import static org.lwjgl.nanovg.NanoVG.nvgFontSize;
import static org.lwjgl.nanovg.NanoVG.nvgImagePattern;
import static org.lwjgl.nanovg.NanoVG.nvgRGBA;
import static org.lwjgl.nanovg.NanoVG.nvgRect;
import static org.lwjgl.nanovg.NanoVG.nvgResetScissor;
import static org.lwjgl.nanovg.NanoVG.nvgScissor;
import static org.lwjgl.nanovg.NanoVG.nvgText;
import static org.lwjgl.nanovg.NanoVG.nvgTextAlign;
import static org.lwjgl.nanovg.NanoVG.nvgTextLetterSpacing;
import static org.lwjgl.nanovg.NanoVGGL3.NVG_ANTIALIAS;
import static org.lwjgl.nanovg.NanoVGGL3.NVG_DEBUG;
import static org.lwjgl.nanovg.NanoVGGL3.NVG_STENCIL_STROKES;
import static org.lwjgl.nanovg.NanoVGGL3.nvgCreate;
import static org.lwjgl.nanovg.NanoVGGL3.nvgDelete;
import static org.lwjgl.nanovg.NanoVGGL3.nvglCreateImageFromHandle;

import java.awt.Color;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.opengl.GL11;

import xueli.game2.resource.Resource;
import xueli.game2.resource.ResourceHolder;
import xueli.game2.resource.submanager.render.BufferUtils;
import xueli.game2.resource.submanager.render.texture.Texture;
import xueli.gui.driver.FrameBuffer;
import xueli.gui.driver.GraphicDriver;
import xueli.utils.logger.InvokeDaemon;

public class NanoGui implements ResourceHolder, GraphicDriver {
	
	private final InvokeDaemon daemon = new InvokeDaemon(getClass());
	
	long nvg = 0;
	FrameBufferStack frameBufferStack = new FrameBufferStack(this);

	private final NVGColor colorBuf = NVGColor.create();
	private final NVGPaint paintBuf = NVGPaint.create();

	public NanoGui() {
	}

	@Override
	public void reload() {
		daemon.announce();
		
		if (this.nvg != 0) {
			nvgDelete(nvg);
		}

		this.nvg = nvgCreate(NVG_STENCIL_STROKES | NVG_ANTIALIAS | NVG_DEBUG);
		if (this.nvg == 0) {
			throw new RuntimeException("Couldn't init NanoVG!");
		}

	}

	@Override
	public int registerImage(Resource res) throws IOException {
		daemon.announce();
		
		byte[] bytes = res.readAll();

		ByteBuffer buffer = BufferUtils.createByteBuffer(bytes.length);
		buffer.put(bytes);
		buffer.flip();

		int id = nvgCreateImageMem(nvg, NVG_IMAGE_NEAREST, buffer);
		if (id <= 0) {
			throw new IOException("Can't register image: " + res.toString());
		}
		return id;
	}

	public int registerImage(Texture tex) {
		daemon.announce();
		return nvglCreateImageFromHandle(nvg, tex.id(), tex.width(), tex.height(), NVG_IMAGE_NEAREST);
	}

	@Override
	public int registerFont(String name, Resource res) throws IOException {
		daemon.announce();
		
		byte[] bytes = res.readAll();

		ByteBuffer buffer = BufferUtils.createByteBuffer(bytes.length);
		buffer.put(bytes);
		buffer.flip();

		int id = nvgCreateFontMem(nvg, name, buffer, 0);

		if (id < 0) {
			throw new IOException("Can't register font: " + name);
		}

		return id;
	}
	
	@Override
	public void clearColor(float r, float g, float b, float a) {
		daemon.announce();
		
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);
		GL11.glClearColor(r, g, b, a);
		
	}

	@Override
	public void begin(int width, int height) {
		daemon.announce();
		nvgBeginFrame(nvg, width, height, (float) width / height);
	}

	@Override
	public void setTextLetterSpacing(float s) {
		daemon.announce();
		nvgTextLetterSpacing(nvg, s);
	}

	private int getFontAlignNvgValue(FontAlign align) {
		daemon.announce();
		return switch (align) {
		case LEFT -> NVG_ALIGN_LEFT;
		case CENTER -> NVG_ALIGN_CENTER;
		case RIGHT -> NVG_ALIGN_RIGHT;
		case TOP -> NVG_ALIGN_TOP;
		case MIDDLE -> NVG_ALIGN_MIDDLE;
		case BOTTOM -> NVG_ALIGN_BOTTOM;
		case BASE_LINE -> NVG_ALIGN_BASELINE;
		};
	}

	@Override
	public float drawFont(float x, float y, float size, String str, int fontId, FontAlign... aligns) {
		daemon.announce();
		
		int align = 0;
		for (int i = 0; i < aligns.length; i++) {
			align |= getFontAlignNvgValue(aligns[i]);
		}

		nvgFontSize(nvg, size);
		nvgFontFaceId(nvg, fontId);
		nvgTextAlign(nvg, align);
		return nvgText(nvg, x, y, str);
	}

	@Override
	public void drawFilledRect(float x, float y, float width, float height, FillType type) {
		daemon.announce();
		
		nvgBeginPath(nvg);
		nvgRect(nvg, x, y, width, height);
		this.fill(type);

	}

	@Override
	public void drawFilledCircle(float x, float y, float radius, FillType type) {
		daemon.announce();
		
		nvgBeginPath(nvg);
		nvgCircle(nvg, x, y, radius);
		this.fill(type);

	}

	@Override
	public void setTexturedPaint(float x, float y, float width, float height, float angle, float alpha, int imageId) {
		daemon.announce();
		
		nvgImagePattern(nvg, x, y, width, height, angle, imageId, alpha, paintBuf);

	}

	@Override
	public void drawImage(float x, float y, float width, float height, float alpha, int imageId) {
		daemon.announce();
		
		this.setTexturedPaint(x, y, width, height, 0, alpha, imageId);
		this.drawFilledRect(x, y, width, height, FillType.PAINT);

	}

	@Override
	public void drawImageCircle(float x, float y, float radius, int imageId, float angle, float alpha) {
		daemon.announce();
		
		this.setTexturedPaint(x - radius, y - radius, radius * 2, radius * 2, angle, alpha, imageId);
		this.drawFilledCircle(x, y, radius, FillType.PAINT);

	}

	@Override
	public void setColor(Color color) {
		daemon.announce();
		
		int c = color.getRGB();
		nvgRGBA((byte) ((c >> 16) & 0xFF), (byte) ((c >> 8) & 0xFF), (byte) (c & 0xFF), (byte) ((c >> 24) & 0xFF),
				this.colorBuf);
		nvgFillColor(nvg, this.colorBuf);

	}

	@Override
	public void fill(FillType type) {
		daemon.announce();
		
		switch (type) {
		case COLOR -> nvgFillColor(nvg, colorBuf);
		case PAINT -> nvgFillPaint(nvg, paintBuf);
		}
		nvgFill(nvg);
	}

	@Override
	public void scissor(float x, float y, float width, float height) {
		daemon.announce();
		nvgScissor(nvg, x, y, width, height);
	}

	@Override
	public void scissorReset() {
		daemon.announce();
		nvgResetScissor(nvg);
	}

	@Override
	public float measureTextWidth(float size, String text, int fontId) {
		daemon.announce();
		
		nvgFontSize(nvg, size);
		nvgTextAlign(nvg, NVG_ALIGN_LEFT);
		nvgFontFaceId(nvg, fontId);
		return nvgText(nvg, 0, -10000000, text);
	}

	@Override
	public FrameBuffer createFrameBuffer(int width, int height) {
		daemon.announce();
		return new NanoFrameBuffer(width, height, this);
	}

	@Override
	public void pushFrameBuffer(FrameBuffer buffer) {
		daemon.announce();
		frameBufferStack.push(((NanoFrameBuffer) buffer));
	}

	@Override
	public void popFrameBuffer() {
		daemon.announce();
		frameBufferStack.pop();
	}

	@Override
	public void finish() {
		daemon.announce();
		nvgEndFrame(nvg);
	}

	public long getContext() {
		return nvg;
	}

	public void release() {
		nvgDelete(nvg);

	}

}
