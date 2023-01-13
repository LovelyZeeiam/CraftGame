package xueli.game2.ecs;

import java.util.List;

interface ResourceList {
	
	public void add(Object t);
	
	public <T> T get(Class<T> clazz);
	
	public <T> void remove(Class<T> clazz);
	
	public List<Object> values();
	
}
