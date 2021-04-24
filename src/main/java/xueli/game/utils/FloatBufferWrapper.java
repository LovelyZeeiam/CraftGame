package xueli.game.utils;

import java.nio.FloatBuffer;

public class FloatBufferWrapper {

	private FloatBuffer buffer;

	public FloatBufferWrapper(FloatBuffer buffer) {
		this.buffer = buffer;

	}

	public void putVector3f(float x, float y, float z) {
		buffer.put(x);
		buffer.put(y);
		buffer.put(z);
	}

	public void putVector4f(float x, float y, float z, float w) {
		buffer.put(x);
		buffer.put(y);
		buffer.put(z);
		buffer.put(w);
	}

	public void putVector2f(float x, float y) {
		buffer.put(x);
		buffer.put(y);
	}

}
