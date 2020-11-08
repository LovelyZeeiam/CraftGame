package xueli.gamengine.utils;

import java.util.Arrays;

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

    }

    public FloatList put(float v) {
        data[pointer] = v;
        pointer++;
        size++;

        if (pointer == data.length) {
            this.capacity += 32;
            float[] data2 = new float[this.capacity];
            System.arraycopy(data, 0, data2, 0, data.length);
            this.data = data2;

        }

        hasChanged = true;

        return this;
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

    @Override
    public String toString() {
        return Arrays.toString(getData());
    }

}
