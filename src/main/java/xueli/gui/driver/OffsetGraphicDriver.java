package xueli.gui.driver;

import java.awt.Color;
import java.io.IOException;

import xueli.game2.resource.Resource;

public class OffsetGraphicDriver implements GraphicDriver {

	private float offsetX, offsetY;
	private final GraphicDriver father;

	public OffsetGraphicDriver(float offsetX, float offsetY, GraphicDriver father) {
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.father = father;

	}

	@Override
	public int registerImage(Resource res) throws IOException {
		return father.registerImage(res);
	}

	@Override
	public int registerFont(String name, Resource res) throws IOException {
		return registerFont(name, res);
	}

	@Override
	public void setTextLetterSpacing(float s) {
		father.setTextLetterSpacing(s);
	}

	@Override
	public float drawFont(float x, float y, float size, String str, int fontId, FontAlign... aligns) {
		return father.drawFont(x + offsetX, y + offsetY, size, str, fontId, aligns);
	}

	@Override
	public void drawFilledRect(float x, float y, float width, float height, FillType type) {
		father.drawFilledRect(x + offsetX, y + offsetY, width, height, type);
	}

	@Override
	public void drawFilledCircle(float x, float y, float radius, FillType type) {
		father.drawFilledCircle(x + offsetX, y + offsetY, radius, type);
	}

	@Override
	public void setTexturedPaint(float x, float y, float width, float height, float angle, float alpha, int imageId) {
		father.setTexturedPaint(x + offsetX, y + offsetY, width, height, angle, alpha, imageId);
	}

	@Override
	public void setColor(Color color) {
		father.setColor(color);
	}

	@Override
	public void fill(FillType type) {
		father.fill(type);
	}

	@Override
	public void scissor(float x, float y, float width, float height) {
		father.scissor(x + offsetX, y + offsetY, width, height);
	}

	@Override
	public void scissorReset() {
		father.scissorReset();
	}

	@Override
	public float measureTextWidth(float size, String text, int fontId) {
		return father.measureTextWidth(size, text, fontId);
	}

	@Override
	public FrameBuffer createFrameBuffer(int width, int height) {
		return father.createFrameBuffer(width, height);
	}

	@Override
	public void pushFrameBuffer(FrameBuffer buffer) {
		father.pushFrameBuffer(buffer);
	}

	@Override
	public void popFrameBuffer() {
		father.popFrameBuffer();
	}

	public void setOffset(float offsetX, float offsetY) {
		this.offsetX = offsetX;
		this.offsetY = offsetY;

	}

	@Override
	public void begin(int width, int height) {
		father.begin(width, height);
	}

	@Override
	public void finish() {
		father.finish();
	}

}
