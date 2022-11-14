package xueli.mcremake.classic.client;

import xueli.game.utils.GLHelper;
import xueli.game2.display.GameDisplay;
import xueli.mcremake.classic.client.renderer.gui.MyGui;

import java.awt.*;

public class CraftGameClient extends GameDisplay {

	private final MyGui gui;

	public CraftGameClient() {
		super(800, 600, "Minecraft Classic Forever");
		this.gui = new MyGui(this);

	}

	@Override
	protected void renderInit() {
		this.gui.init();

	}

	@Override
	protected void render() {
		this.gui.drawFont(30, 30, 36.0f, "Hello world", Color.CYAN);

		this.gui.tick();
		GLHelper.checkGLError("Render");


	}

	@Override
	protected void renderRelease() {
		this.gui.release();

	}

}
