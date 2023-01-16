package xueli.mcremake.client.gui;

import xueli.game2.renderer.ui.Gui;
import xueli.mcremake.client.CraftGameClient;

public class LoadingWorldOverlay extends CraftGameOverlay {

	public LoadingWorldOverlay(CraftGameClient ctx) {
		super(ctx);
	}

	@Override
	public void init(Gui gui) {
		super.init(gui);
		
	}

	@Override
	public void render() {
		this.drawUniversalBackground(0, 0, getContext().getWidth(), getContext().getHeight());

	}

	@Override
	public void release() {

	}

	@Override
	public void reload() {

	}

}
