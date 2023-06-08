package xueli.gui.driver;

import java.awt.Color;
import java.io.IOException;

import org.lwjgl.utils.vector.Matrix2f;
import org.lwjgl.utils.vector.Matrix3f;
import xueli.game2.resource.Resource;

public interface GraphicDriver {
	
	public void clearColor(float r, float g, float b, float a);
	
	public int registerImage(Resource res) throws IOException;

	public int registerFont(String name, Resource res) throws IOException;

	public void setTextLetterSpacing(float s);

	public float drawFont(float x, float y, float size, String str, int fontId, FontAlign... aligns);

	public void drawFilledRect(float x, float y, float width, float height, FillType type);

	public void drawFilledCircle(float x, float y, float radius, FillType type);

	public void setTexturedPaint(float x, float y, float width, float height, float angle, float alpha, int imageId);

	public void setColor(Color color);

	public void fill(FillType type);

	public void scissorPush(float x, float y, float width, float height);

	public void scissorPop();

	public void scissorReset();

	public float measureTextWidth(float size, String text, int fontId);

	public FrameBuffer createFrameBuffer(int width, int height);

	public void pushFrameBuffer(FrameBuffer buffer);

	public void popFrameBuffer();

	public void pushMatrix(Matrix3f matrix);

	public void popMatrix();
	
	public void begin(int width, int height);
	
	public void finish();

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
