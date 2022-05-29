package xueli.craftgame.renderer.view;

import org.lwjgl.glfw.GLFW;

import xueli.craftgame.event.EventRaiseGUI;
import xueli.craftgame.renderer.GameViewRenderer;

public class InventoryView extends IngameView {

	public InventoryView(GameViewRenderer master) {
		super(master);

	}

	@Override
	public void init(long nvg) {

	}

	@Override
	protected void stroke(long nvg) {
		if (inputHolder.isKeyDownOnce(GLFW.GLFW_KEY_ESCAPE) || inputHolder.isKeyDownOnce(GLFW.GLFW_KEY_E)) {
			getMaster().getContext().submitEvent(new EventRaiseGUI(null));
		}

	}

	@Override
	public void release(long nvg) {
		super.release(nvg);

	}

	@Override
	public void onSize(long nvg, int width, int height) {
		super.onSize(nvg, width, height);
	}

}
