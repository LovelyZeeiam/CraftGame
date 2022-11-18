package xueli.mcremake.classic.client;

import xueli.game2.display.GameDisplay;
import xueli.mcremake.classic.client.gui.LoadingWorldOverlay;
import xueli.mcremake.classic.client.gui.universal.UniversalGui;

public class CraftGameClient extends GameDisplay {

	private final UniversalGui universalGui = new UniversalGui(this);

	public CraftGameClient() {
		super(800, 600, "Minecraft Classic Forever");

	}

	@Override
	protected void renderInit() {
		getResourceManager().addResourceHolder(universalGui);
		universalGui.init();

		getOverlayManager().setOverlay(new LoadingWorldOverlay(this));

	}

	@Override
	protected void render() {


	}

	@Override
	protected void renderRelease() {


	}

	public UniversalGui getUniversalGui() {
		return universalGui;
	}

}
