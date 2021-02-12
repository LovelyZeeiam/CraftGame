package xueli.gamengine.utils;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import java.nio.FloatBuffer;

public class FloatBufferWrapper {
	
	private FloatBuffer buffer;
	
	public FloatBufferWrapper(FloatBuffer buffer) {
		this.buffer = buffer;
		
	}
	
	public FloatBufferWrapper putVector3f(Vector3f v) {
		buffer.put(v.x).put(v.y).put(v.z);
		return this;
	}
	
	public FloatBufferWrapper putVector4f(Vector4f v) {
		buffer.put(v.x).put(v.y).put(v.z).put(v.w);
		return this;
	}
	
	public FloatBufferWrapper putVector2f(Vector2f v) {
		buffer.put(v.x).put(v.y);
		return this;
	}

}
