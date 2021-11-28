package xueli.game.module;

import xueli.utils.logger.MyLogger;

import java.util.ArrayList;
import java.util.HashMap;

public class Modules<T extends Module> {

	protected HashMap<String, T> modules = new HashMap<>();
	protected ArrayList<T> indices = new ArrayList<>();

	public Modules() {

	}

	public void init() {

	}

	public void add(T t) {
		if (!t.checkInvaild()) {
			MyLogger.getInstance().error("Not an invaild module: " + t);
			return;
		}
		modules.put(t.getNamespace(), t);
		indices.add(t);
	}

	public T getModule(String namespace) {
		T t = modules.get(namespace);
		if (t == null) {
			MyLogger.getInstance().warning("Found no module named: " + namespace);
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
