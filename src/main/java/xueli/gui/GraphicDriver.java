package xueli.gui;

import java.awt.Color;
import java.io.IOException;

import xueli.game2.resource.Resource;

public interface GraphicDriver {
	
	public int registerImage(Resource res) throws IOException;
	public int registerFont(String name, Resource res) throws IOException;
	public void setTextLetterSpacing(float s);
	public float drawFont(float x, float y, float size, String str, int fontId, FontAlign... aligns);
	public void drawFilledRect(float x, float y, float width, float height, FillType type);
	public void drawFilledCircle(float x, float y, float radius, FillType type);
	public void setTexturedPaint(float x, float y, float width, float height, float angle, float alpha, int imageId);
	public void setColor(Color color);
	public void fill(FillType type);
	public void scissor(float x, float y, float width, float height);
	public void scissorReset();
	public float measureTextWidth(float size, String text, int fontId);
	
	default public void drawImage(float x, float y, float width, float height, float alpha, int imageId) {
		this.setTexturedPaint(x, y, width, height, 0, alpha, imageId);
		this.drawFilledRect(x, y, width, height, FillType.PAINT);

	}

	default public void drawImageCircle(float x, float y, float radius, int imageId, float angle, float alpha) {
		this.setTexturedPaint(x - radius, y - radius, radius * 2, radius * 2, angle, alpha, imageId);
		this.drawFilledCircle(x, y, radius, FillType.PAINT);

	}
	
	public static enum FontAlign {
		LEFT, CENTER, RIGHT, TOP, MIDDLE, BOTTOM, BASE_LINE;
	}
	
	public static enum FillType {
		COLOR, PAINT;
	}
	
}
