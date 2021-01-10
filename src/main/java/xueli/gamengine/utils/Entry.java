package xueli.gamengine.utils;

public class Entry<K, V> {

	private K k;
	private V v;

	public Entry(K k, V v) {
		this.k = k;
		this.v = v;

	}

	public K getK() {
		return k;
	}

	public V getV() {
		return v;
	}
}
