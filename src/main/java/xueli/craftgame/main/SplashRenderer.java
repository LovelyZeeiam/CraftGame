package xueli.craftgame.main;

import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_CENTER;
import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_MIDDLE;
import static org.lwjgl.nanovg.NanoVG.nvgBeginPath;
import static org.lwjgl.nanovg.NanoVG.nvgCreateFont;
import static org.lwjgl.nanovg.NanoVG.nvgFill;
import static org.lwjgl.nanovg.NanoVG.nvgFillColor;
import static org.lwjgl.nanovg.NanoVG.nvgFontFace;
import static org.lwjgl.nanovg.NanoVG.nvgFontSize;
import static org.lwjgl.nanovg.NanoVG.nvgRect;
import static org.lwjgl.nanovg.NanoVG.nvgText;
import static org.lwjgl.nanovg.NanoVG.nvgTextAlign;

import xueli.game.utils.NVGColors;
import xueli.game.utils.renderer.NVGRenderer;

public class SplashRenderer extends NVGRenderer {

	private static final String fontName = "GAME";

	public SplashRenderer() {
		super();

		nvgCreateFont(nvg, fontName, ModCraftGame.MAIN_GAME.getResFolder() + "/fonts/Minecraft-Ascii.ttf");

	}

	@Override
	public void stroke() {
		nvgBeginPath(nvg);
		nvgRect(nvg, 0, 0, game.getWidth(), game.getHeight());
		nvgFillColor(nvg, NVGColors.WINDOW_THEME_COLOR);
		nvgFill(nvg);

		nvgFontSize(nvg, 30);
		nvgFontFace(nvg, fontName);
		nvgTextAlign(nvg, NVG_ALIGN_CENTER | NVG_ALIGN_MIDDLE);
		nvgFillColor(nvg, NVGColors.YELLOW);
		nvgText(nvg, game.getWidth() / 2, game.getHeight() / 2, "You’ll never be too cool to dance~");

	}

}
