package xueli.game2.renderer.ui;

import xueli.game2.resource.ResourceHolder;

public interface Overlay extends ResourceHolder {

	public void init();
	public void render(MyGui gui);
	public void release();

}
