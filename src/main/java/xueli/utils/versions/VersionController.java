package xueli.utils.versions;

import java.util.HashMap;

public abstract class VersionController<T, I> {

	private HashMap<Integer, Versionable<T, I>> map = new HashMap<>();

	public VersionController() {
		init();
	}

	protected abstract void init();

	public Versionable<T, I> getByVersion(int v) {
		return map.get(v);
	}

	public void registerVersion(int version, Versionable<T, I> v) {
		this.map.put(version, v);
	}

}
