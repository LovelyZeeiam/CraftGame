package xueli.game2.ecs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ResourceListGeneric<T> {

	private final HashMap<Class<?>, Integer> classToIndexMap = new HashMap<>();
	private final ArrayList<T> components = new ArrayList<>();

	public ResourceListGeneric() {
	}

	public <E extends T> void add(E t) {
		Class<?> clazz = t.getClass();
		Integer previousIndex = classToIndexMap.get(clazz);
		if (previousIndex != null) {
			components.set(previousIndex, t);
			return;
		}

		int index = components.size();
		components.add(t);
		classToIndexMap.put(clazz, index);

	}

	@SuppressWarnings("unchecked")
	public <E extends T> E get(Class<E> clazz) {
		Integer index = classToIndexMap.get(clazz);
		if (index == null) {
			return null;
		}
		return (E) components.get(index);
	}

	public void remove(Class<T> clazz) {
		Integer indexBoxed = classToIndexMap.get(clazz);
		if (indexBoxed == null) {
			return;
		}
		int index = indexBoxed;

		int componentsLastIndex = components.size() - 1;
		if (index != components.size()) {
			// Pick up the last component
			T lastComponent = components.get(componentsLastIndex);
			// Switch it to the place where our leaving element is
			components.set(index, lastComponent);

			// Don't forget to change the value in the map
			Class<?> lastCompClazz = lastComponent.getClass();
			classToIndexMap.put(lastCompClazz, indexBoxed);

		}

		components.remove(componentsLastIndex);
		classToIndexMap.remove(clazz);

	}

	public List<T> values() {
		return components;
	}

}
