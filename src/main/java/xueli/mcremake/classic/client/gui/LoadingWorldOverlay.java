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
		this.drawInternalBackground(gui);

	}

	@Override
	public void release() {

	}

	@Override
	public void reload() {

	}

}
