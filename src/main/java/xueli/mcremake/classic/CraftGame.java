package xueli.mcremake.classic;

import xueli.game2.display.GameDisplay;

public class CraftGame extends GameDisplay {

	public CraftGame() {
		super(800, 600, "Minecraft Classic Forever");
	}

	@Override
	protected void renderInit() {
		this.announceCrash("TEST", new Throwable());

	}

	@Override
	protected void render() {


	}

	@Override
	protected void renderRelease() {

	}

}
