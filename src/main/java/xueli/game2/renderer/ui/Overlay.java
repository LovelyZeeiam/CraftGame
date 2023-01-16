package xueli.game2.renderer.ui;

public interface Overlay {
	
	public void init(Gui gui);
	public void reload();
	public void render();
	public void release();

}
