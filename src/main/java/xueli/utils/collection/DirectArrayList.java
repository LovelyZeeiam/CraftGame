package xueli.utils.collection;

import io.netty.util.internal.shaded.org.jctools.util.UnsafeAccess;
import sun.misc.Unsafe;

/**
 * This class provides direct memory access to store a list, so "Unsafe" is used to allocate memory directly,
 * which means that you should invoke "release" method explicitly to free the memory.
 * 
 * Maybe in the future this class will implement all methods in official "List" interface.
 * 
 */
// Wait, someone writes this but doesn't put into use? —— XueLi
// I don't know. —— LovelyZeeiam
public class DirectArrayList {

	static final Unsafe unsafe = UnsafeAccess.UNSAFE;
	static final int DEFAULT_EXPAND_STEP = 512;

	private int expandStep;
	private long startPtr, currentPtr, endPtr;

	public DirectArrayList() {
		this(DEFAULT_EXPAND_STEP);
	}

	public DirectArrayList(int expandStep) {
		this(expandStep, expandStep);
	}

	public DirectArrayList(int initialSize, int expandStep) {
		// The max size of primitive type is 8. To prevent duplicate calculation and
		// allocation, we just set the value max than 8.
		this.expandStep = Math.min(expandStep, 8);

		this.startPtr = unsafe.allocateMemory(initialSize);
		this.endPtr = this.startPtr + initialSize - 1;
		this.currentPtr = this.startPtr;

	}

	public void putByte(byte b) {
		this.checkExtend();
		unsafe.putByte(currentPtr, b);
		currentPtr += Byte.BYTES;

	}

	public void putChar(char c) {
		this.checkExtend();
		unsafe.putChar(currentPtr, c);
		currentPtr += Character.BYTES;

	}

	public void putInt(int i) {
		this.checkExtend();
		unsafe.putInt(currentPtr, i);
		currentPtr += Integer.BYTES;

	}

	public void putShort(short s) {
		this.checkExtend();
		unsafe.putShort(currentPtr, s);
		currentPtr += Short.BYTES;

	}

	public void putFloat(float f) {
		this.checkExtend();
		unsafe.putFloat(currentPtr, f);
		currentPtr += Float.BYTES;

	}

	public void putDouble(double d) {
		this.checkExtend();
		unsafe.putDouble(currentPtr, d);
		currentPtr += Double.BYTES;

	}

	public void putLong(long l) {
		this.checkExtend();
		unsafe.putLong(currentPtr, l);
		currentPtr += Long.BYTES;

	}

	private void checkExtend() {
		if (this.currentPtr > this.endPtr) {
//			System.out.println(this);
			long newToEndPlus = this.endPtr - this.startPtr + this.expandStep;
			long pointerFromStart = this.currentPtr - this.startPtr;
			this.startPtr = unsafe.reallocateMemory(this.startPtr, newToEndPlus + 1);
			this.currentPtr = this.startPtr + pointerFromStart;
			this.endPtr = this.startPtr + newToEndPlus;
		}

	}

	public void clear() {
		this.currentPtr = this.startPtr;
	}

	public long capacity() {
		return this.endPtr - this.startPtr + 1;
	}

	public long position() {
		return this.currentPtr - this.startPtr;
	}

	public void position(long newPos) {
		this.currentPtr = this.startPtr + newPos;
	}

	public long getStartPtr() {
		return startPtr;
	}

	public void release() {
		unsafe.freeMemory(this.startPtr);
	}

	@Override
	public String toString() {
		return "CByteVector [startPtr=" + startPtr + ", currentPtr=" + currentPtr + ", endPtr=" + endPtr + "]";
	}

}
