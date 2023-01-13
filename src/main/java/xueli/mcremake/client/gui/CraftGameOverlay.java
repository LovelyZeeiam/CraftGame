package xueli.mcremake.client.gui;

import xueli.game2.renderer.ui.Gui;
import xueli.game2.renderer.ui.Overlay;
import xueli.mcremake.client.CraftGameClient;
import xueli.mcremake.client.gui.universal.UniversalGui;

public abstract class CraftGameOverlay implements Overlay {

	private final CraftGameClient ctx;
	private final UniversalGui universalGui;

	public CraftGameOverlay(CraftGameClient ctx) {
		this.ctx = ctx;
		this.universalGui = ctx.getUniversalGui();

	}

	protected void drawUniversalBackground(Gui gui, int x, int y, int width, int height) {
		universalGui.drawUniversalBackground2(gui, x, y, width, height);
	}

	@Override
	public void render(Gui gui) {
		universalGui.tick();
	}

	public CraftGameClient getContext() {
		return ctx;
	}

}
