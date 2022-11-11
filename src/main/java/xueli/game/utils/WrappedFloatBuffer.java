package xueli.game.utils;

import org.lwjgl.BufferUtils;
import org.lwjgl.utils.vector.Vector2f;
import org.lwjgl.utils.vector.Vector3f;
import org.lwjgl.utils.vector.Vector4f;

import java.nio.FloatBuffer;

public class WrappedFloatBuffer {

	private static final int DEFAULT_CAPACITY = 65536;
	private static final int STEP_EXPAND = 65536;

	private FloatBuffer buffer;
	private boolean isExternal = false;

	public WrappedFloatBuffer() {
		this(DEFAULT_CAPACITY);

	}

	public WrappedFloatBuffer(int capacity) {
		this.buffer = alloc(capacity);
		this.buffer.position(0);

	}

	public WrappedFloatBuffer(FloatBuffer buffer) {
		this.buffer = buffer;
		isExternal = true;

	}

	public WrappedFloatBuffer put(float v) {
		predictAndExpand(1);
		buffer.put(v);
		return this;
	}

	public WrappedFloatBuffer put(float... vs) {
		predictAndExpand(vs.length);
		for (int i = 0; i < vs.length; i++) {
			float f = vs[i];
			buffer.put(f);
		}
		return this;
	}

	public WrappedFloatBuffer put(Vector3f v) {
		predictAndExpand(3);
		buffer.put(v.x);
		buffer.put(v.y);
		buffer.put(v.z);
		return this;
	}

	public WrappedFloatBuffer put(Vector4f v) {
		predictAndExpand(4);
		buffer.put(v.x);
		buffer.put(v.y);
		buffer.put(v.z);
		buffer.put(v.w);
		return this;
	}

	public WrappedFloatBuffer put(Vector2f v) {
		predictAndExpand(2);
		buffer.put(v.x);
		buffer.put(v.y);
		return this;
	}

	private void predictAndExpand(int more) {
		// System.out.println(buffer.position() +", " + more + ", " +
		// buffer.capacity());
		if (!isExternal && buffer.position() + more >= buffer.capacity()) {
			FloatBuffer newBuffer = alloc(this.buffer.capacity() + STEP_EXPAND);
			newBuffer.put(this.buffer);
			this.buffer = newBuffer;
		}
	}

	private FloatBuffer alloc(int capacity) {
		// NO MORE FLOATBUFFER.ALLOC BECAUSE IT'S HEAP SPACE THAT COULDN'T BE LOADED BY
		// OPENGL
		return BufferUtils.createFloatBuffer(capacity);
	}

	public FloatBuffer getBuffer() {
		return buffer;
	}

}
