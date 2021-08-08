package xueli.utils.properties;

public interface Parsable<T> {
	public T parse(String s) throws Exception;
}
