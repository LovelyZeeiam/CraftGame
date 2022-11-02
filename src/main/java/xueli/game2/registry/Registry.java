package xueli.game2.registry;

import xueli.game2.resource.ResourceLocation;

public interface Registry<T> {

	public T getByName(ResourceLocation name);
	public T getById(int id);
	public int getId(T t);

}
