package xueli.craftgame.renderer.view;

public interface INVGRenderer {

	public void init(long nvg);

	public void render(long nvg);

	public void release(long nvg);

	public default void onSize(long nvg, int width, int height) {
	}

}
