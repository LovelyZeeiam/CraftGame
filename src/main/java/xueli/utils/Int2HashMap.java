package xueli.utils;

import java.util.concurrent.ConcurrentHashMap;

public class Int2HashMap<V> extends ConcurrentHashMap<Long, V> {

	private static final long serialVersionUID = 1L;

	private static long vert2Long(int x, int y) {
		return (long) x & 4294967295L | ((long) y & 4294967295L) << 32;
	}

	public V put(int x, int y, V value) {
		return this.put(vert2Long(x, y), value);
	}

	public V get(int x, int y) {
		return this.get(vert2Long(x, y));
	}

	public boolean containsKey(int x, int y) {
		return this.containsKey(vert2Long(x, y));
	}

}
