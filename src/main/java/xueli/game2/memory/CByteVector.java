package xueli.game2.memory;

import io.netty.util.internal.shaded.org.jctools.util.UnsafeAccess;
import sun.misc.Unsafe;

/**
 * This is a Java port of "std::vector" in C++ actually,
 * so "Unsafe" is used to allocate memory directly.
 */
public class CByteVector {
	
	private static final Unsafe unsafe = UnsafeAccess.UNSAFE;
	private static final int DEFAULT_EXPAND_STEP = 512;
	
	private int expandStep;
	private long startPtr, currentPtr, endPtr;
	
	public CByteVector() {
		this(DEFAULT_EXPAND_STEP);
	}
	
	public CByteVector(int expandStep) {
		this(expandStep, expandStep);
	}
	
	public CByteVector(int initialSize, int expandStep) {
		this.expandStep = expandStep;
		
		this.startPtr = unsafe.allocateMemory(initialSize);
		this.endPtr = this.startPtr + initialSize - 1;
		this.currentPtr = this.startPtr;
		
	}
	
	public void put(byte b) {
		if(this.currentPtr > this.endPtr) {
//			System.out.println(this);
			long newToEndPlus = this.endPtr - this.startPtr + this.expandStep;
			long pointerFromStart = this.currentPtr - this.startPtr;
			this.startPtr = unsafe.reallocateMemory(this.startPtr, newToEndPlus + 1);
			this.currentPtr = this.startPtr + pointerFromStart;
			this.endPtr = this.startPtr + newToEndPlus;
			
		}
		
		unsafe.putByte(currentPtr++, b);
		
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
	
	public void release() {
		unsafe.freeMemory(this.startPtr);
	}

	@Override
	public String toString() {
		return "CByteVector [startPtr=" + startPtr + ", currentPtr=" + currentPtr + ", endPtr=" + endPtr + "]";
	}
	
}
