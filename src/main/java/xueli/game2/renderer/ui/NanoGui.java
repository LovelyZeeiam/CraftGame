package xueli.game2.renderer.ui;

import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.opengl.GL11;
import org.lwjgl.utils.vector.Matrix3f;
import org.lwjgl.utils.vector.Vector2f;
import xueli.game2.resource.Resource;
import xueli.game2.resource.ResourceHolder;
import xueli.game2.resource.submanager.render.BufferUtils;
import xueli.game2.resource.submanager.render.texture.Texture;
import xueli.gui.driver.FrameBuffer;
import xueli.gui.driver.GraphicDriver;
import xueli.utils.logger.InvokeDaemon;

import java.awt.*;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.nanovg.NanoVGGL3.*;

public class NanoGui implements ResourceHolder, GraphicDriver {
	
	private final InvokeDaemon daemon = new InvokeDaemon(getClass());
	
	long nvg = 0;
	final FrameBufferStack frameBufferStack = new FrameBufferStack(this);
	final MatrixStack matrixStack = new MatrixStack();
	final ScissorStack scissorStack = new ScissorStack(matrixStack);

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
		Vector2f transformedPosition = this.matrixStack.transform(new Vector2f(x, y));
//		Vector2f transformedSize = this.matrixStack.delta(new Vector2f());

		int align = 0;
		for (int i = 0; i < aligns.length; i++) {
			align |= getFontAlignNvgValue(aligns[i]);
		}

		nvgFontSize(nvg, size);
		nvgFontFaceId(nvg, fontId);
		nvgTextAlign(nvg, align);
		return nvgText(nvg, transformedPosition.x, transformedPosition.y, str);
	}

	@Override
	public void drawFilledRect(float x, float y, float width, float height, FillType type) {
		daemon.announce();
		Vector2f transformedPosition = this.matrixStack.transform(new Vector2f(x, y));
		Vector2f transformedSize = this.matrixStack.delta(new Vector2f(width, height));
		
		nvgBeginPath(nvg);
		nvgRect(nvg, transformedPosition.x, transformedPosition.y, transformedSize.x, transformedSize.y);
		this.fill(type);

	}

	@Override
	public void drawFilledCircle(float x, float y, float radius, FillType type) {
		daemon.announce();
		Vector2f transformedPosition = this.matrixStack.transform(new Vector2f(x, y));
//		Vector2f transformedSize = this.matrixStack.delta(new Vector2f());
		
		nvgBeginPath(nvg);
		nvgCircle(nvg, transformedPosition.x, transformedPosition.y, radius);
		this.fill(type);

	}

	@Override
	public void setTexturedPaint(float x, float y, float width, float height, float angle, float alpha, int imageId) {
		daemon.announce();
		Vector2f transformedPosition = this.matrixStack.transform(new Vector2f(x, y));
		Vector2f transformedSize = this.matrixStack.delta(new Vector2f(width, height));
		
		nvgImagePattern(nvg, transformedPosition.x, transformedPosition.y, transformedSize.x, transformedSize.y, angle, imageId, alpha, paintBuf);

	}

	@Override
	public void drawImage(float x, float y, float width, float height, float alpha, int imageId) {
		daemon.announce();
		Vector2f transformedPosition = this.matrixStack.transform(new Vector2f(x, y));
		Vector2f transformedSize = this.matrixStack.delta(new Vector2f(width, height));

		this.setTexturedPaint(transformedPosition.x, transformedPosition.y, transformedSize.x, transformedSize.y, 0, alpha, imageId);
		this.drawFilledRect(transformedPosition.x, transformedPosition.y, transformedSize.x, transformedSize.y, FillType.PAINT);

	}

	@Override
	public void drawImageCircle(float x, float y, float radius, int imageId, float angle, float alpha) {
		daemon.announce();

		// No need to calculate from matrix!
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

	// The scissor rectangle is transformed by the current transform.
	@Override
	public void scissorPush(float x, float y, float width, float height) {
		daemon.announce();

		var scissorResult = scissorStack.push(x, y, width, height);
		nvgResetScissor(nvg);
		nvgScissor(nvg, scissorResult.x(), scissorResult.y(), scissorResult.width(), scissorResult.height());

	}

	@Override
	public void scissorPop() {
		daemon.announce();
		nvgResetScissor(nvg);

		var scissorResult = scissorStack.pop();
		if(scissorResult == null) return;
		nvgScissor(nvg, scissorResult.x(), scissorResult.y(), scissorResult.width(), scissorResult.height());

	}

	@Override
	public void scissorReset() {
		daemon.announce();
		nvgResetScissor(nvg);
		scissorStack.reset();

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
		// TODO: change the state of this state manager!
	}

	@Override
	public void popFrameBuffer() {
		daemon.announce();
		frameBufferStack.pop();

	}

	@Override
	public void pushMatrix(Matrix3f matrix) {
		daemon.announce();
		matrixStack.pushMatrix(matrix);
	}

	@Override
	public void popMatrix() {
		daemon.announce();
		matrixStack.popMatrix();
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
