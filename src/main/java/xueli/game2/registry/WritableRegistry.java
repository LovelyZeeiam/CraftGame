package xueli.game2.registry;

import xueli.game2.resource.ResourceIdentifier;

public interface WritableRegistry<T> extends Registry<T> {

	public void register(ResourceIdentifier name, T t);

	public void addTag(ResourceIdentifier name, ResourceIdentifier... tags);

	public Registry<T> freeze();

}
