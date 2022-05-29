package xueli.craftgame.renderer;

public interface IGameRenderer {

	public void init();

	public void render();

	public void release();

	public default void onSize(int width, int height) {
	}

}
