package xueli.utils;

import xueli.game.vector.Vector2i;

import java.util.concurrent.ConcurrentHashMap;

public class Int2HashMap<V> extends ConcurrentHashMap<Vector2i, V> {

	private static final long serialVersionUID = 1L;

	public V put(int x, int y, V value) {
		return this.put(new Vector2i(x, y), value);
	}

	public V get(int x, int y) {
		return this.get(new Vector2i(x, y));
	}

	public V remove(int x, int y) {
		return this.remove(new Vector2i(x, y));
	}

	public boolean containsKey(int x, int y) {
		return this.containsKey(new Vector2i(x, y));
	}

}
