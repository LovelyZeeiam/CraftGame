package xueli.game.resource;

public interface ResourceLoader<T> {

	public T load(ResourceHolder<T> holder) throws Exception;

}
