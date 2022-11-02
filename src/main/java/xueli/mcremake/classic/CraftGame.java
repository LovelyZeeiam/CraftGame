package xueli.mcremake.classic;

import xueli.game2.display.GameDisplay;

public class CraftGame extends GameDisplay {

	public CraftGame() {
		super(800, 600, "Minecraft Classic Forever");
	}

	@Override
	protected void renderInit() {

	}

	@Override
	protected void render() {
		if(getOverlayManager().hasOverlay()) {
			getDisplay().setMouseGrabbed(false);
			return;
		}
		getDisplay().setMouseGrabbed(true);




	}

	@Override
	protected void renderRelease() {

	}

}
