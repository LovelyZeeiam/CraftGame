package xueli.mcremake.client.gui.universal;

import xueli.game2.renderer.ui.MyGui;
import xueli.game2.resource.ResourceHolder;
import xueli.game2.resource.ResourceLocation;
import xueli.mcremake.client.CraftGameClient;

public class UniversalGui implements ResourceHolder {

	public static final ResourceLocation FONT_RESOURCE_LOCATION = new ResourceLocation("minecraft", "font/default.ttf");
	private int gameFontId;

	private final UniversalBackgroundRenderer universalBg;

	private final CraftGameClient ctx;

	public UniversalGui(CraftGameClient ctx) {
		this.ctx = ctx;
		this.universalBg = new UniversalBackgroundRenderer(ctx);
		this.universalBg.init();

	}

	// Draw a universal background that is high-repeated
	// All the background image is there without random rotation
	public void drawUniversalBackground(int x, int y, int width, int height) {
		universalBg.draw(x, y, width, height);
	}

	private final UniversalBackgroundRenderer2 ubr = new UniversalBackgroundRenderer2();

	// All the background image is there with random rotation
	public void drawUniversalBackground2(MyGui gui, int x, int y, int width, int height) {
		ubr.draw(gui, x, y, width, height);
	}

	public void tick() {
		universalBg.tick();
	}

	@Override
	public void reload() {
		this.gameFontId = ctx.getFontResource().register(FONT_RESOURCE_LOCATION, true);
		ubr.reload(ctx);

	}

	public void release() {
	}

	public int getGameFontId() {
		return gameFontId;
	}

}
