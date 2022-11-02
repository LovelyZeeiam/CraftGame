package xueli.game2.renderer.ui;

import xueli.game2.lifecycle.LifeCycle;

public abstract class Overlay implements LifeCycle {

	protected final long nvg;

	public Overlay() {
		this.nvg = NanoVGContext.INSTANCE.getNvg();
	}

}
