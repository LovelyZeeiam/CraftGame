package xueli.utils.buffer;

import java.nio.ByteBuffer;

import org.lwjgl.system.MemoryUtil;
import org.lwjgl.utils.vector.Vector2f;
import org.lwjgl.utils.vector.Vector3f;
import org.lwjgl.utils.vector.Vector4f;

/**
 * <p>A object managing memory, but the buffer won't be freed by GC!</p>
 * 
 * <p>Actually the class is named arbitrary :}</p>
 * 
 */
public class LotsOfByteBuffer {

	private static final int DEFAULT_CAPACITY = 32768;
	private static final int STEP_EXPAND = 65536;

	private ByteBuffer buffer;
	private int size;

	public LotsOfByteBuffer() {
		this(DEFAULT_CAPACITY);
	}

	public LotsOfByteBuffer(int initialCapacity) {
		// The direct memory should be freed explicitly, or the game will jam when it
		// comes to GC because it will free about 1GB at the same time in my computer.
		this.buffer = MemoryUtil.memAlloc(initialCapacity);

		this.size = initialCapacity;

	}

	private void predictAndExpand(int more) {
		if (buffer.position() + more >= buffer.capacity()) {
			int capacity = this.size = this.buffer.capacity() + STEP_EXPAND;
			ByteBuffer newBuffer = MemoryUtil.memAlloc(capacity);
			this.buffer.flip();
			newBuffer.put(this.buffer);
			this.buffer = newBuffer;
		}
	}
	
	public void position(int newPos) {
		this.buffer.position(newPos);
	}
	
	public int position() {
		return this.buffer.position();
	}
	
	public void putByte(byte v) {
		predictAndExpand(Byte.BYTES);
		buffer.put(v);
		
	}
	
	public byte readByte() {
		return buffer.get();
	}
	
	public byte readByte(int position) {
		return buffer.get(position);
	}
	
	public void putFloat(float v) {
		predictAndExpand(Float.BYTES);
		buffer.putFloat(v);
//		System.out.println(buffer.position());
	}
	
	public float readFloat() {
		return buffer.getFloat();
	}

	public float readFloat(int position) {
		return buffer.getFloat(position);
	}
	
	public void putInt(int v) {
		predictAndExpand(Integer.BYTES);
		buffer.putInt(v);
	}
	
	public int readInt() {
		return buffer.getInt();
	}
	
	public int readInt(int position) {
		return buffer.getInt(position);
	}

	public void putShort(short v) {
		predictAndExpand(Short.BYTES);
		buffer.putShort(v);
	}
	
	public short readShort() {
		return buffer.getShort();
	}
	
	public short readShort(int position) {
		return buffer.getShort(position);
	}

	// TODO: read the following data structures
	public void putVector3f(Vector3f v) {
		predictAndExpand(3 * Float.BYTES);
		buffer.putFloat(v.x);
		buffer.putFloat(v.y);
		buffer.putFloat(v.z);
	}

	public void putVector4f(Vector4f v) {
		predictAndExpand(4 * Float.BYTES);
		buffer.putFloat(v.x);
		buffer.putFloat(v.y);
		buffer.putFloat(v.z);
		buffer.putFloat(v.w);
	}

	public void putVector2f(Vector2f v) {
		predictAndExpand(2 * Float.BYTES);
		buffer.putFloat(v.x);
		buffer.putFloat(v.y);
	}

	public void clear() {
		buffer.clear();
	}

	private boolean isRead = false;

	public void setReadWrite(boolean read) {
		if (this.isRead == read)
			return;
		if (read) {
			buffer.flip();
		} else {
			buffer.compact();
		}
		this.isRead = read;

	}

	public ByteBuffer getBuffer() {
		if (this.buffer == null)
			throw new IllegalStateException();
		return buffer;
	}

	public int getSize() {
		return size;
	}

	public void release() {
		MemoryUtil.memFree(this.buffer);
		this.buffer = null;

	}

}
