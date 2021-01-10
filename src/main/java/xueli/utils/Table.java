package xueli.utils;

import java.util.HashMap;

public class Table<T> extends HashMap<Integer, HashMap<Integer, T>> {

	private static final long serialVersionUID = 3543645039023993865L;

	public void put(int x, int y, T value) {
		if (!super.containsKey(x))
			super.put(x, new HashMap<Integer, T>());
		super.get(x).put(y, value);
	}

	public T get(int x, int y) {
		HashMap<Integer, T> map = super.get(x);
		if (map == null)
			return null;
		return map.get(y);
	}

}
