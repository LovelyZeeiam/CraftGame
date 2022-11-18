package xueli.mcremake.classic.client.gui;

import xueli.game2.renderer.ui.MyGui;
import xueli.game2.renderer.ui.Overlay;
import xueli.mcremake.classic.client.CraftGameClient;

public abstract class CraftGameOverlay implements Overlay {

	private final CraftGameClient ctx;
	private final UniversalGui universalGui;

	public CraftGameOverlay(CraftGameClient ctx) {
		this.ctx = ctx;
		this.universalGui = ctx.getUniversalGui();

	}

	protected void drawInternalBackground(MyGui gui) {
		gui.setTexturedPaint(0, 0, 100, 100, 0, 1.0f, universalGui.getTextureUniversalBgId());
		gui.drawFilledRect(0, 0, ctx.getWidth(), ctx.getHeight(), MyGui.FillType.PAINT);

	}

	public CraftGameClient getContext() {
		return ctx;
	}

}
