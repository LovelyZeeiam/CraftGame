package xueli.game2.ecs;

import java.util.List;

interface ResourceList<T> {
	
	public void add(T t);
	
	public <S extends T> S get(Class<S> clazz);
	
	public <S extends T> void remove(Class<S> clazz);

	public List<T> values();
	
}
