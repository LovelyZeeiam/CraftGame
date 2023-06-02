package xueli.registry;

import java.util.Set;
import java.util.function.BiConsumer;

public interface Registry<T> {

	public T getByName(Identifier name);

	public T getById(int id);

	public int getId(T t);

	public Set<Identifier> getAllContainTag(Identifier tag);

	public Set<Identifier> getTags(Identifier name);

	public void forEach(BiConsumer<Identifier, T> c);

	public WritableRegistry<T> cloneToWritable();

}
