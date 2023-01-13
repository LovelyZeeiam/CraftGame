package xueli.mcremake.client.gui;

import xueli.game2.renderer.ui.Gui;
import xueli.mcremake.client.CraftGameClient;

public class LoadingWorldOverlay extends CraftGameOverlay {

	public LoadingWorldOverlay(CraftGameClient ctx) {
		super(ctx);
	}

	@Override
	public void init() {
	}

	@Override
	public void render(Gui gui) {
		this.drawUniversalBackground(gui, 0, 0, getContext().getWidth(), getContext().getHeight());

		super.render(gui);

	}

	@Override
	public void release() {

	}

	@Override
	public void reload() {

	}

}
