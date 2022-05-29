package xueli.game.utils;

import java.nio.FloatBuffer;
import java.util.Arrays;

import org.lwjgl.utils.vector.Vector2f;
import org.lwjgl.utils.vector.Vector3f;
import org.lwjgl.utils.vector.Vector4f;

import xueli.game.vector.Vector4b;

/**
 * 经过测试，性能没有直接的floatBuffer高， 但是还是挺好用的 因为floatBuffer大小已经限制死了
 */
public class FloatList {

	private float[] data;
	private int capacity;

	private int pointer;
	private int size;

	private boolean hasChanged = false;
	private float[] realDataCache = null;

	public FloatList() {
		this(32);

	}

	public FloatList(int capacity) {
		if (capacity <= 0)
			throw new IllegalArgumentException("Capacity not bigger than zero: " + capacity);

		this.data = new float[capacity];
		this.pointer = 0;
		this.size = 0;
		this.capacity = capacity;

	}

	public void clear() {
		this.pointer = 0;
		this.size = 0;
		this.hasChanged = true;

	}

	public FloatList put(float v) {
		data[pointer] = v;
		pointer++;
		size = Math.max(pointer, size);

		if (pointer == data.length) {
			this.capacity += 4096;
			float[] data2 = new float[this.capacity];
			System.arraycopy(data, 0, data2, 0, data.length);
			this.data = data2;

		}

		hasChanged = true;

		return this;
	}

	public FloatList put(Vector4b v) {
		int bits = (v.x << 24) | (v.y << 16) | (v.z << 8) | v.w;
		return put(Float.intBitsToFloat(bits));
	}

	public FloatList put(Vector3f v) {
		return put(v.x).put(v.y).put(v.z);
	}

	public FloatList put(Vector2f v) {
		return put(v.x).put(v.y);
	}

	public FloatList put(Vector4f v) {
		return put(v.x).put(v.y).put(v.z).put(v.w);
	}

	public int getPointer() {
		return pointer;
	}

	public void setPointer(int pointer) {
		if (this.pointer >= this.capacity)
			throw new IllegalArgumentException("pointer bigger than capacity: " + pointer);
		this.pointer = pointer;
	}

	public int getSize() {
		return size;
	}

	public float[] getData() {
		if (realDataCache == null || this.hasChanged) {
			float[] realData = new float[size];
			System.arraycopy(data, 0, realData, 0, size);
			this.realDataCache = realData;
			hasChanged = false;

		}
		return this.realDataCache;
	}

	public float[] getDataPointer() {
		return data;
	}

	public void storeInBuffer(FloatBuffer buffer) {
		if (data == null)
			return;
		buffer.put(data, 0, size);
	}

	public void postDispose() {
		this.data = null;
		realDataCache = null;

	}

	@Override
	public String toString() {
		return Arrays.toString(getData());
	}

}
