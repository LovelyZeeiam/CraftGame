package xueli.game2.ecs;

interface ComponentList {
	
	public void add(Object t);
	
	public <T> T get(Class<T> clazz);
	
	public <T> void remove(Class<T> clazz);
	
}
