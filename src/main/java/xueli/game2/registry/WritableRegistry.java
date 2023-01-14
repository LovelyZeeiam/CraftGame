package xueli.game2.registry;

import xueli.game2.resource.ResourceLocation;

public interface WritableRegistry<T> extends Registry<T> {
	
	public void register(ResourceLocation name, T t);
	
	public void addTag(ResourceLocation name, ResourceLocation... tags);
	
	public Registry<T> freeze();
	
}
