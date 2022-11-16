package xueli.mcremake.classic.client;

import xueli.game.utils.GLHelper;
import xueli.game2.display.GameDisplay;
import xueli.game2.resource.ResourceLocation;

import java.awt.*;

public class CraftGameClient extends GameDisplay {

	public static final ResourceLocation FONT_RESOURCE_LOCATION = new ResourceLocation("minecraft", "font/default.ttf");

	public CraftGameClient() {
		super(800, 600, "Minecraft Classic Forever");


	}

	@Override
	protected void renderInit() {


	}

	@Override
	protected void render() {
		getGuiManager().begin();
		getGuiManager().setColor(Color.WHITE);
		getGuiManager().drawFont(30, 30, 36.0f, "Hello world", getFontResource().register(FONT_RESOURCE_LOCATION, true));

		GLHelper.checkGLError("Render");


	}

	@Override
	protected void renderRelease() {


	}

}
