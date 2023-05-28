package xueli.game2.renderer.ui;

import xueli.game2.display.GameDisplay;
import xueli.game2.lifecycle.LifeCycle;
import xueli.game2.resource.ResourceHolder;

public class OverlayManager implements LifeCycle, ResourceHolder {

	private final GameDisplay display;
//	private final Gui gui;

	private Overlay overlay = null;

	public OverlayManager(GameDisplay display) {
		this.display = display;
//		this.gui = display.getGuiManager();

	}

	@Override
	public void init() {
//		if(overlay != null) {
//			overlay.init(gui);
//		}
	}

	@Override
	public void tick() {
//		if(overlay != null) {
//			gui.begin(display.getWidth(), display.getHeight());
//			overlay.render();
//			gui.finish();
//		}

	}

	@Override
	public void reload() {
//		if(overlay != null) {
//			overlay.reload();
//		}

	}

	@Override
	public void release() {
//		if(this.overlay != null) {
//			this.overlay.release();
//		}

	}

	public void setOverlay(Overlay overlay) {
//		if(this.overlay != null) {
//			this.overlay.release();
//		}
//		this.overlay = overlay;
//		if(this.overlay != null) {
//			this.overlay.init(gui);
//			this.overlay.reload();
//		}

	}

	public boolean hasOverlay() {
		return this.overlay != null;
	}

}
