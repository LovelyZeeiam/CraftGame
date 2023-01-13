package xueli.game2.renderer.ui;

public interface Overlay {

	public void init(Gui gui);
	public void reload(Gui gui);
	public void render(Gui gui);
	public void release(Gui gui);

}
