package xueli.game2.renderer.ui;

import xueli.game2.display.GameDisplay;
import xueli.game2.lifecycle.LifeCycle;

public class OverlayManager implements LifeCycle {

	private GameDisplay display;

	private Overlay overlay = null;

	public OverlayManager(GameDisplay display) {
		this.display = display;

	}

	@Override
	public void init() {
	}

	@Override
	public void tick() {
		if(overlay != null) {
			overlay.tick();
		}

	}

	@Override
	public void release() {
		if(this.overlay != null) {
			this.overlay.release();
		}

	}

	public void setOverlay(Overlay overlay) {
		if(this.overlay != null) {
			this.overlay.release();
		}
		this.overlay = overlay;
		if(this.overlay != null) {
			this.overlay.init();
		}

	}

	public boolean hasOverlay() {
		return this.overlay != null;
	}

}
