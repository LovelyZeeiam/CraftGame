package xueli.game2.renderer.legacy.buffer;

import java.nio.ByteBuffer;

import org.lwjgl.system.MemoryUtil;
import org.lwjgl.utils.vector.Vector2f;
import org.lwjgl.utils.vector.Vector3f;
import org.lwjgl.utils.vector.Vector4f;

public class LotsOfByteBuffer {

	private static final int DEFAULT_CAPACITY = 32768;
	private static final int STEP_EXPAND = 65536;
	
	private ByteBuffer buffer;
	private int size;

	public LotsOfByteBuffer() {
		this(DEFAULT_CAPACITY);
	}

	public LotsOfByteBuffer(int initialCapacity) {
		// The direct memory should be freed explicitly, or the game will jam when it comes to GC because it will free about 1GB at the same time in my computer.
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

	public void put(float v) {
		predictAndExpand(Float.BYTES);
		buffer.putFloat(v);
//		System.out.println(buffer.position());
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

	public int getSize() {
		return size;
	}

	public void release() {
//		System.out.println("free");
		MemoryUtil.memFree(this.buffer);
		this.buffer = null;

	}

}
