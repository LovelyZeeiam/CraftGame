package xueli.game2.registry;

import java.util.Set;
import java.util.function.BiConsumer;

import xueli.game2.resource.ResourceIdentifier;

public interface Registry<T> {

	public T getByName(ResourceIdentifier name);

	public T getById(int id);

	public int getId(T t);
	
	public Set<ResourceIdentifier> getAllContainTag(ResourceIdentifier tag);
	
	public Set<ResourceIdentifier> getTags(ResourceIdentifier name);
	
	public void forEach(BiConsumer<ResourceIdentifier, T> c);

	public WritableRegistry<T> cloneToWritable();

}
