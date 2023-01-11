package xueli.game2.registry;

import java.util.function.BiConsumer;

import xueli.game2.resource.ResourceLocation;

public interface Registry<T> {

	public T getByName(ResourceLocation name);

	public T getById(int id);

	public int getId(T t);

	public void forEach(BiConsumer<ResourceLocation, T> c);

	public WritableRegistry<T> cloneToWritable();

}
