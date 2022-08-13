package xueli.game2.renderer.legacy.buffer;

import org.lwjgl.opengl.GL11;

public enum VertexType {

	INT(4, GL11.GL_INT),
	FLOAT(4, GL11.GL_FLOAT),
	DOUBLE(8, GL11.GL_DOUBLE),
	SHORT(2, GL11.GL_SHORT);

	private int size;
	private int glValue;
	VertexType(int size, int glValue) {
		this.size = size;
		this.glValue = glValue;
	}

	public int getSize() {
		return size;
	}

	public int getGlValue() {
		return glValue;
	}

}
