package xueli.game2.renderer.ui;

import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;
import xueli.game2.display.GameDisplay;
import xueli.game2.lifecycle.LifeCycle;

import java.awt.*;

import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.nanovg.NanoVGGL3.*;

public class MyGui implements LifeCycle {

	private final GameDisplay ctx;

	private long nvg;
	private final NVGColor colorBuf = NVGColor.create();
	private final NVGPaint paintBuf = NVGPaint.create();

	public MyGui(GameDisplay ctx) {
		this.ctx = ctx;

	}

	@Override
	public void init() {
		this.nvg = nvgCreate(NVG_STENCIL_STROKES | NVG_ANTIALIAS | NVG_DEBUG);
		if (this.nvg == 0) {
			ctx.announceCrash("NanoVG", new RuntimeException("Couldn't init NanoVG!"));
		}

	}

	private boolean hasBegun = false;

	public void begin() {
		float width = ctx.getWidth();
		float height = ctx.getHeight();
		nvgBeginFrame(nvg, width, height, width / height);

		this.hasBegun = true;

	}

	public void setTextLetterSpacing(float s) {
		nvgTextLetterSpacing(nvg, s);
	}

	public float drawFont(float x, float y, float size, String str, int fontId, FontAlign... aligns) {
		int align = 0;
		for (int i = 0; i < aligns.length; i++) {
			align |= aligns[i].nvgValue;
		}

		nvgFontSize(nvg, size);
		nvgFontFaceId(nvg, fontId);
		nvgTextAlign(nvg, align);
		return nvgText(nvg, x, y, str);
	}

	public void drawFilledRect(float x, float y, float width, float height, FillType type) {
		nvgBeginPath(nvg);
		nvgRect(nvg, x, y, width, height);
		this.fill(type);

	}

	public void drawFilledCircle(float x, float y, float radius, FillType type) {
		nvgBeginPath(nvg);
		nvgCircle(nvg, x, y, radius);
		this.fill(type);

	}

	public void setTexturedPaint(float x, float y, float width, float height, float angle, float alpha, int imageId) {
		nvgImagePattern(nvg, x, y, width, height, angle, imageId, alpha, paintBuf);

	}

	public void drawImage(float x, float y, float width, float height, float alpha, int imageId) {
		this.setTexturedPaint(x, y, width, height, 0, alpha, imageId);
		this.drawFilledRect(x, y, width, height, FillType.PAINT);

	}

	public void drawImageCircle(float x, float y, float radius, int imageId, float angle, float alpha) {
		this.setTexturedPaint(x - radius, y - radius, radius * 2, radius * 2, angle, alpha, imageId);
		this.drawFilledCircle(x, y, radius, FillType.PAINT);

	}

	public void setColor(Color color) {
		int c = color.getRGB();
		nvgRGBA((byte) ((c >> 16) & 0xFF), (byte) ((c >> 8) & 0xFF), (byte) (c & 0xFF), (byte) ((c >> 24) & 0xFF), this.colorBuf);
		nvgFillColor(nvg, this.colorBuf);

	}

	private void fill(FillType type) {
		switch (type) {
			case COLOR -> nvgFillColor(nvg, colorBuf);
			case PAINT -> nvgFillPaint(nvg, paintBuf);
		}
		nvgFill(nvg);
	}

	public void scissor(float x, float y, float width, float height) {
		nvgScissor(nvg, x, y, width, height);
	}

	public void scissorReset() {
		nvgResetScissor(nvg);
	}

	public float measureTextWidth(float size, String text, int fontId) {
		nvgFontSize(nvg, size);
		nvgTextAlign(nvg, NVG_ALIGN_LEFT);
		nvgFontFaceId(nvg, fontId);
		return nvgText(nvg, 0, -10000000, text);
	}

	public void finish() {
		nvgEndFrame(nvg);
	}

	@Override
	public void tick() {
	}

	public long getContext() {
		return nvg;
	}

	@Override
	public void release() {
		nvgDelete(nvg);

	}

	public static enum FontAlign {

		LEFT(NVG_ALIGN_LEFT),
		CENTER(NVG_ALIGN_CENTER),
		RIGHT(NVG_ALIGN_RIGHT),
		TOP(NVG_ALIGN_TOP),
		MIDDLE(NVG_ALIGN_MIDDLE),
		BOTTOM(NVG_ALIGN_BOTTOM),
		BASE_LINE(NVG_ALIGN_BASELINE);

		int nvgValue;
		FontAlign(int nvgValue) {
			this.nvgValue = nvgValue;
		}

	}

	public static enum FillType {
		COLOR, PAINT;
	}

}
