package xueli.craftgame.state;

import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_CENTER;
import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_MIDDLE;
import static org.lwjgl.nanovg.NanoVG.NVG_IMAGE_GENERATE_MIPMAPS;
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
import static org.lwjgl.nanovg.NanoVG.nvgRGBA;
import static org.lwjgl.nanovg.NanoVG.nvgRect;
import static org.lwjgl.nanovg.NanoVG.nvgRoundedRect;
import static org.lwjgl.nanovg.NanoVG.nvgText;
import static org.lwjgl.nanovg.NanoVG.nvgTextAlign;

import org.lwjgl.nanovg.NVGColor;

import xueli.craftgame.CraftGame;
import xueli.game.renderer.NVGRenderer;
import xueli.game.utils.NVGColors;
import xueli.utils.eval.EvalableFloat;

public class StateSplash extends NVGRenderer {

	private static final String fontName = "GAME";

	private static final NVGColor background_color = NVGColor.create();

	private static final EvalableFloat splash_x = new EvalableFloat("win_width * 0.5 - 100.0 * scale");
	private static final EvalableFloat splash_y = new EvalableFloat("win_height * 0.4 - 100.0 * scale");
	private static final EvalableFloat splash_size = new EvalableFloat("200.0 * scale");
	private static final EvalableFloat splash_border_size = new EvalableFloat("3.0 * scale");
	private static final NVGColor splash_border_color = NVGColor.create();

	private static final EvalableFloat loading_message_x = new EvalableFloat("win_width / 2.0");
	private static final EvalableFloat loading_message_y = new EvalableFloat("win_height * 0.6 + 30.0 * scale");
	private static final EvalableFloat loading_message_text_size = new EvalableFloat("20.0 * scale");

	static {
		nvgRGBA((byte) 240, (byte) 248, (byte) 255, (byte) 255, background_color);
		nvgRGBA((byte) 0, (byte) 0, (byte) 139, (byte) 255, splash_border_color);

	}

	private int tex_splash;
	private int tex_back;

	public StateSplash() {
		super();

		nvgCreateFont(nvg, fontName, CraftGame.DEFAULT_RES_DIRECTORY_STRING + "/fonts/Minecraft-Ascii.ttf");
		this.tex_splash = nvgCreateImage(nvg, CraftGame.DEFAULT_RES_DIRECTORY_STRING + "/gui/splash.jpg",
				NVG_IMAGE_GENERATE_MIPMAPS);
		this.tex_back = nvgCreateImage(nvg, CraftGame.DEFAULT_RES_DIRECTORY_STRING + "/gui/options_background.png",
				NVG_IMAGE_REPEATX | NVG_IMAGE_REPEATY | NVG_IMAGE_NEAREST);

	}

	@Override
	public void stroke() {
		// background
		nvgImagePattern(nvg, 0, 0, 2400 * game.getDisplayScale(), 1280 * game.getDisplayScale(), 0, tex_back, 1, paint);
		nvgBeginPath(nvg);
		nvgRect(nvg, 0, 0, game.getWidth(), game.getHeight());
		nvgFillPaint(nvg, paint);
		nvgFill(nvg);

		// splash border
		nvgBeginPath(nvg);
		nvgRect(nvg, splash_x.getValue() - splash_border_size.getValue(),
				splash_y.getValue() - splash_border_size.getValue(),
				splash_size.getValue() + splash_border_size.getValue() * 2,
				splash_size.getValue() + splash_border_size.getValue() * 2);
		nvgFillColor(nvg, splash_border_color);
		nvgFill(nvg);

		// splash
		nvgImagePattern(nvg, splash_x.getValue(), splash_y.getValue(), splash_size.getValue(), splash_size.getValue(),
				0, tex_splash, 1, paint);
		nvgBeginPath(nvg);
		nvgRoundedRect(nvg, splash_x.getValue(), splash_y.getValue(), splash_size.getValue(), splash_size.getValue(),
				0);
		nvgFillPaint(nvg, paint);
		nvgFill(nvg);

		// loading message
		nvgFontSize(nvg, loading_message_text_size.getValue());
		nvgFontFace(nvg, fontName);
		nvgTextAlign(nvg, NVG_ALIGN_CENTER | NVG_ALIGN_MIDDLE);
		nvgFillColor(nvg, NVGColors.BLACK);
		nvgText(nvg, loading_message_x.getValue(), loading_message_y.getValue(), "Loading~");

		if (((CraftGame) game).initComplete)
			game.getRendererManager().setCurrentRenderer(new StateMainMenu());

	}

	@Override
	public void size(int w, int h) {
		super.size(w, h);

		splash_x.needEvalAgain();
		splash_y.needEvalAgain();
		splash_size.needEvalAgain();
		splash_border_size.needEvalAgain();

		loading_message_x.needEvalAgain();
		loading_message_y.needEvalAgain();
		loading_message_text_size.needEvalAgain();

	}

}
