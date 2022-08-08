package xueli.game2.renderer.buffer;

import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.utils.vector.Vector2f;
import org.lwjgl.utils.vector.Vector3f;
import org.lwjgl.utils.vector.Vector4f;

public class LotsOfByteBuffer {

	private static final int DEFAULT_CAPACITY = 65536;
	private static final int STEP_EXPAND = 65536;

	private ByteBuffer buffer;

	public LotsOfByteBuffer() {
		this(DEFAULT_CAPACITY);
	}

	public LotsOfByteBuffer(int initialCapacity) {
		this.buffer = BufferUtils.createByteBuffer(initialCapacity);
	}

	private void predictAndExpand(int more) {
		if (buffer.position() + more >= buffer.capacity()) {
			ByteBuffer newBuffer = BufferUtils.createByteBuffer(this.buffer.capacity() + STEP_EXPAND);
			newBuffer.put(this.buffer);
			this.buffer = newBuffer;
		}
	}

	public void put(float v) {
		predictAndExpand(Float.BYTES);
		buffer.putFloat(v);
	}

	public void put(int v) {
		predictAndExpand(Integer.BYTES);
		buffer.putInt(v);
	}

	public void put(short v) {
		predictAndExpand(Short.BYTES);
		buffer.putShort(v);
	}

	public void put(Vector3f v) {
		predictAndExpand(3 * Float.BYTES);
		buffer.putFloat(v.x);
		buffer.putFloat(v.y);
		buffer.putFloat(v.z);
	}

	public void put(Vector4f v) {
		predictAndExpand(4 * Float.BYTES);
		buffer.putFloat(v.x);
		buffer.putFloat(v.y);
		buffer.putFloat(v.z);
		buffer.putFloat(v.w);
	}

	public void put(Vector2f v) {
		predictAndExpand(2 * Float.BYTES);
		buffer.putFloat(v.x);
		buffer.putFloat(v.y);
	}

	public void clear() {
		buffer.clear();
	}
	
	private boolean isRead = false;
	
	public void setReadWrite(boolean read) {
		if(this.isRead == read)
			return;
		if(read) {
			buffer.flip();
		} else {
			buffer.compact();
		}
		this.isRead = read;
		
	}

	public ByteBuffer getBuffer() {
		return buffer;
	}

}
