package xueli.utils.map;

import java.io.Serial;
import java.util.HashMap;

@Deprecated
public class Int2HashMap<V> extends HashMap<Long, V> {

	@Serial
	private static final long serialVersionUID = 1L;

	public V put(int x, int y, V value) {
		return this.put(vert2Long(x, y), value);
	}

	public V get(int x, int y) {
		return this.get(vert2Long(x, y));
	}

	public boolean containsKey(int x, int y) {
		return this.containsKey(vert2Long(x, y));
	}

	private static long vert2Long(int x, int y) {
		return (long) x & 4294967295L | ((long) y & 4294967295L) << 32;
	}

}
