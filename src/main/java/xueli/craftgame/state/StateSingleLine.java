package xueli.craftgame.state;

import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_BOTTOM;
import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_CENTER;
import static org.lwjgl.nanovg.NanoVG.NVG_IMAGE_NEAREST;
import static org.lwjgl.nanovg.NanoVG.NVG_IMAGE_REPEATX;
import static org.lwjgl.nanovg.NanoVG.NVG_IMAGE_REPEATY;
import static org.lwjgl.nanovg.NanoVG.nvgBeginPath;
import static org.lwjgl.nanovg.NanoVG.nvgCreateFont;
import static org.lwjgl.nanovg.NanoVG.nvgCreateImage;
import static org.lwjgl.nanovg.NanoVG.nvgFill;
import static org.lwjgl.nanovg.NanoVG.nvgFillColor;
import static org.lwjgl.nanovg.NanoVG.nvgFillPaint;
import static org.lwjgl.nanovg.NanoVG.nvgFontFace;
import static org.lwjgl.nanovg.NanoVG.nvgFontSize;
import static org.lwjgl.nanovg.NanoVG.nvgImagePattern;
import static org.lwjgl.nanovg.NanoVG.nvgRect;
import static org.lwjgl.nanovg.NanoVG.nvgText;
import static org.lwjgl.nanovg.NanoVG.nvgTextAlign;

import xueli.craftgame.CraftGame;
import xueli.game.utils.NVGColors;
import xueli.game.utils.renderer.NVGRenderer;
import xueli.utils.eval.EvalableFloat;

public class StateSingleLine extends NVGRenderer {

	private static final String fontName = "GAME";

	private static final EvalableFloat text_size = new EvalableFloat("20.0 * scale");

	private int tex_back;
	private String langKey;

	public StateSingleLine(String langKey) {
		super();

		this.langKey = langKey;

		nvgCreateFont(nvg, fontName, CraftGame.DEFAULT_RES_DIRECTORY_STRING + "/fonts/Minecraft-Ascii.ttf");
		this.tex_back = nvgCreateImage(nvg, CraftGame.DEFAULT_RES_DIRECTORY_STRING + "/gui/options_background.png",
				NVG_IMAGE_REPEATX | NVG_IMAGE_REPEATY | NVG_IMAGE_NEAREST);

	}

	@Override
	public void stroke() {
		// background
		{
			nvgImagePattern(nvg, 0, 0, 2400 * game.getDisplayScale(), 1280 * game.getDisplayScale(), 0, tex_back, 1,
					paint);
			nvgBeginPath(nvg);
			nvgRect(nvg, 0, 0, game.getWidth(), game.getHeight());
			nvgFillPaint(nvg, paint);
			nvgFill(nvg);

		}

		// text
		{
			nvgFontSize(nvg, text_size.getValue());
			nvgFontFace(nvg, fontName);
			nvgTextAlign(nvg, NVG_ALIGN_BOTTOM | NVG_ALIGN_CENTER);
			nvgFillColor(nvg, NVGColors.WHITE);
			nvgText(nvg, game.getWidth() / 2, game.getHeight() / 2, lang.getStringFromLangMap(langKey));

		}

	}

	@Override
	public void size(int w, int h) {
		super.size(w, h);

		text_size.needEvalAgain();

	}

}
