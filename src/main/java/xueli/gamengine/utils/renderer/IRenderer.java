package xueli.gamengine.utils.renderer;

import xueli.gamengine.utils.vector.Vector;

public interface IRenderer {

	public void init();

	public void size();

	public void render(Vector camPos);

	public void release();

}
