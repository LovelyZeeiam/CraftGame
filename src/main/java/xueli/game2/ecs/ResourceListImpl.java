package xueli.game2.ecs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ResourceListImpl implements ResourceList {
	
	private final HashMap<Class<?>, Integer> classToIndexMap = new HashMap<>();
	private final ArrayList<Object> components = new ArrayList<>();
	
	public ResourceListImpl() {
	}
	
	@Override
	public void add(Object t) {
		Class<?> clazz = t.getClass();
		Integer previousIndex = classToIndexMap.get(clazz);
		if(previousIndex != null) {
			components.set(previousIndex, t);
			return;
		}
		
		int index = components.size();
		components.add(t);
		classToIndexMap.put(clazz, index);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(Class<T> clazz) {
		Integer index = classToIndexMap.get(clazz);
		if(index == null) {
			return null;
		}
		return (T) components.get(index);
	}

	@Override
	public <T> void remove(Class<T> clazz) {
		Integer indexBoxed = classToIndexMap.get(clazz);
		if(indexBoxed == null) {
			return;
		}
		int index = indexBoxed;
		
		int componentsLastIndex = components.size() - 1;
		if(index != components.size()) {
			// Pick up the last component
			Object lastComponent = components.get(componentsLastIndex);
			// Switch it to the place where our leaving element is
			components.set(index, lastComponent);
			
			// Don't forget to change the value in the map
			Class<?> lastCompClazz = lastComponent.getClass();
			classToIndexMap.put(lastCompClazz, indexBoxed);
			
		}
		
		components.remove(componentsLastIndex);
		classToIndexMap.remove(clazz);
		
	}
	
	@Override
	public List<Object> values() {
		return components;
	}

	@Override
	public String toString() {
		return "ComponentListImpl [classToIndexMap=" + classToIndexMap + ", components=" + components + "]";
	}

}
