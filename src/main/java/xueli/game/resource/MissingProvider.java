package xueli.game.resource;

public interface MissingProvider<T> {

	public void onMissing(ResourceHolder<T> holder);

}
