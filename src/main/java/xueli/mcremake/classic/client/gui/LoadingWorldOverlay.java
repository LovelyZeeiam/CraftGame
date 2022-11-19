package xueli.mcremake.classic.client.gui;

import xueli.game2.renderer.ui.MyGui;
import xueli.mcremake.classic.client.CraftGameClient;

public class LoadingWorldOverlay extends CraftGameOverlay {

	public LoadingWorldOverlay(CraftGameClient ctx) {
		super(ctx);
	}

	@Override
	public void init() {
	}

	@Override
	public void render(MyGui gui) {
//		this.drawUniversalBackground(gui, 0, 0, getContext().getWidth(), getContext().getHeight());
		this.drawUniversalBackground(gui, 30, 30, 300, 300);

		super.render(gui);

	}

	@Override
	public void release() {

	}

	@Override
	public void reload() {

	}

}
