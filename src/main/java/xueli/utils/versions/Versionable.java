package xueli.utils.versions;

public interface Versionable<T, I> {

	public T get(I input);

	public I write(T target);

}
