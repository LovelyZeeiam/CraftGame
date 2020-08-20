package xueLi.craftGame.world;

import java.nio.FloatBuffer;

public class DrawData {

	private FloatBuffer buffer;
	private int vertCount;

	public DrawData(FloatBuffer buffer, int vertCount) {
		this.buffer = buffer;
		this.vertCount = vertCount;
	}

	public FloatBuffer getBuffer() {
		return buffer;
	}

	public int getVertCount() {
		return vertCount;
	}

	public void setVertCount(int vertCount) {
		this.vertCount = vertCount;
	}

}
