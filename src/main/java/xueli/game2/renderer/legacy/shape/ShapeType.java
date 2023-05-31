package xueli.game2.renderer.legacy.shape;

import org.lwjgl.opengl.GL11;

public enum ShapeType {

	POINT(GL11.GL_POINTS), LINE(GL11.GL_LINES), LINE_LOOP(GL11.GL_LINE_LOOP), LINE_STRIP(GL11.GL_LINE_STRIP),
	TRIANGLES(GL11.GL_TRIANGLES), TRIANGLE_FAN(GL11.GL_TRIANGLE_FAN), TRIANGLE_STRIP(GL11.GL_TRIANGLE_STRIP);

	private int glVal;

	ShapeType(int glVal) {
		this.glVal = glVal;
	}

	public int getGLValue() {
		return glVal;
	}

}
