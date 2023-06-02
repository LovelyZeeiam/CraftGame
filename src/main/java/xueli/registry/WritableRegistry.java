package xueli.registry;

public interface WritableRegistry<T> extends Registry<T> {

	public void register(Identifier name, T t);

	public void addTag(Identifier name, Identifier... tags);

	public Registry<T> freeze();

}
