package xueli.game.module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

public class Modules<T extends Module> {

	protected HashMap<String, T> modules = new HashMap<>();
	protected ArrayList<T> indices = new ArrayList<>();

	public Modules() {

	}

	public void init() {

	}

	public void add(T t) {
		if (!t.checkInvaild()) {
			Logger.getLogger(getClass().getName()).warning("Not an invaild module: " + t.toString());
			return;
		}
		modules.put(t.getNamespace(), t);
		indices.add(t);
	}

	public T getModule(String namespace) {
		T t = modules.get(namespace);
		if (t == null) {
			Logger.getLogger(getClass().getName()).warning("Found no module named: " + namespace);
			return null;
		}
		return t;
	}

	public T getById(int i) {
		return indices.get(i);
	}

	public int size() {
		return modules.size();
	}

	public ArrayList<T> get() {
		return indices;
	}

}
