package xueli.game2.ecs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ResourceListImpl<T> implements ResourceList<T> {
	
	private final HashMap<Class<? extends T>, Integer> classToIndexMap = new HashMap<>();
	private final ArrayList<T> components = new ArrayList<>();
	
	public ResourceListImpl() {
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void add(T t) {
		Class<? extends T> clazz = (Class<? extends T>) t.getClass();
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
	public <S extends T> S get(Class<S> clazz) {
		Integer index = classToIndexMap.get(clazz);
		if(index == null) {
			return null;
		}
		return (S) components.get(index);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <S extends T> void remove(Class<S> clazz) {
		Integer indexBoxed = classToIndexMap.get(clazz);
		if(indexBoxed == null) {
			return;
		}
		int index = indexBoxed;
		
		int componentsLastIndex = components.size() - 1;
		if(index != components.size()) {
			// Pick up the last component
			T lastComponent = components.get(componentsLastIndex);
			// Switch it to the place where our leaving element is
			components.set(index, lastComponent);
			
			// Don't forget to change the value in the map
			Class<? extends T> lastCompClazz = (Class<? extends T>) lastComponent.getClass();
			classToIndexMap.put(lastCompClazz, indexBoxed);
			
		}
		
		components.remove(componentsLastIndex);
		classToIndexMap.remove(clazz);
		
	}
	
	@Override
	public List<T> values() {
		return components;
	}

	@Override
	public String toString() {
		return "ComponentListImpl [classToIndexMap=" + classToIndexMap + ", components=" + components + "]";
	}

}
