package xueli.craftgame.state;

import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_BOTTOM;
import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_CENTER;
import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_LEFT;
import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_MIDDLE;
import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_RIGHT;
import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_TOP;
import static org.lwjgl.nanovg.NanoVG.NVG_IMAGE_GENERATE_MIPMAPS;
import static org.lwjgl.nanovg.NanoVG.NVG_IMAGE_NEAREST;
import static org.lwjgl.nanovg.NanoVG.NVG_IMAGE_REPEATX;
import static org.lwjgl.nanovg.NanoVG.NVG_IMAGE_REPEATY;
import static org.lwjgl.nanovg.NanoVG.nvgBeginPath;
import static org.lwjgl.nanovg.NanoVG.nvgCircle;
import static org.lwjgl.nanovg.NanoVG.nvgCreateFont;
import static org.lwjgl.nanovg.NanoVG.nvgCreateImage;
import static org.lwjgl.nanovg.NanoVG.nvgFill;
import static org.lwjgl.nanovg.NanoVG.nvgFillColor;
import static org.lwjgl.nanovg.NanoVG.nvgFillPaint;
import static org.lwjgl.nanovg.NanoVG.nvgFontFace;
import static org.lwjgl.nanovg.NanoVG.nvgFontSize;
import static org.lwjgl.nanovg.NanoVG.nvgImagePattern;
import static org.lwjgl.nanovg.NanoVG.nvgRGBA;
import static org.lwjgl.nanovg.NanoVG.nvgRGBAf;
import static org.lwjgl.nanovg.NanoVG.nvgRect;
import static org.lwjgl.nanovg.NanoVG.nvgText;
import static org.lwjgl.nanovg.NanoVG.nvgTextAlign;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.nanovg.NVGColor;

import xueli.craftgame.CraftGame;
import xueli.game.display.DisplayUtils;
import xueli.game.renderer.NVGRenderer;
import xueli.game.utils.NVGColors;
import xueli.utils.eval.EvalableFloat;

public class StateMainMenu extends NVGRenderer {

	private static final String fontName = "GAME";

	private static final EvalableFloat logo_x = new EvalableFloat("win_width * 0.5 - 0.7 * win_width / 2");
	private static final EvalableFloat logo_y = new EvalableFloat("win_height * 0.2 - 0.1 * win_width / 2");
	private static final EvalableFloat logo_width = new EvalableFloat("win_width * 0.7");
	private static final EvalableFloat logo_height = new EvalableFloat("win_width * 0.1");

	private static final EvalableFloat version_x = new EvalableFloat("win_width-5.0");
	private static final EvalableFloat version_y = new EvalableFloat("win_height - 5.0");
	private static final EvalableFloat version_text_size = new EvalableFloat("15.0 * scale");

	private static final EvalableFloat copyright_x = new EvalableFloat("5.0");
	private static final EvalableFloat copyright_y = new EvalableFloat("win_height-5.0");

	private static final EvalableFloat button_width = new EvalableFloat("150.0 * scale");
	private static final EvalableFloat button_height = new EvalableFloat("192.0 * scale");
	private static final EvalableFloat button_border = new EvalableFloat("2.0 * scale");
	private static final EvalableFloat button_text_size = new EvalableFloat("17.0 * scale");
	private static final NVGColor button_text_color = NVGColor.create();

	private static final EvalableFloat single_button_x = new EvalableFloat("win_width * 0.5 - 295 * scale");
	private static final EvalableFloat single_button_y = new EvalableFloat("win_height * 0.5 - 86 * scale");

	private static final EvalableFloat single_text_x = new EvalableFloat("win_width * 0.5 - 220 * scale");
	private static final EvalableFloat single_text_y = new EvalableFloat("win_height * 0.5 + 127 * scale");

	private static final EvalableFloat multi_button_x = new EvalableFloat("win_width * 0.5 - 75 * scale");
	private static final EvalableFloat multi_button_y = new EvalableFloat("win_height * 0.5 - 86 * scale");

	private static final EvalableFloat multi_text_x = new EvalableFloat("win_width * 0.5");
	private static final EvalableFloat multi_text_y = new EvalableFloat("win_height * 0.5 + 127 * scale");

	private static final EvalableFloat setting_button_x = new EvalableFloat("win_width * 0.5 + 145 * scale");
	private static final EvalableFloat setting_button_y = new EvalableFloat("win_height * 0.5 - 86 * scale");

	private static final EvalableFloat setting_text_x = new EvalableFloat("win_width * 0.5 + 220 * scale");
	private static final EvalableFloat setting_text_y = new EvalableFloat("win_height * 0.5 + 127 * scale");

	private static final EvalableFloat name_view_x = new EvalableFloat("win_width - 20 * scale");
	private static final EvalableFloat name_view_y = new EvalableFloat("20.0 * scale");
	private static final EvalableFloat name_view_text_size = new EvalableFloat("13.0 * scale");
	private static final EvalableFloat icon_name_space = new EvalableFloat("8.0 * scale");
	private static final EvalableFloat icon_scale_width = new EvalableFloat("3.0 * scale");
	private static final float icon_scale = 1.5f;
	private static final NVGColor name_view_text_color = NVGColor.create();
	private static final NVGColor name_view_back_color = NVGColor.create();

	static {
		nvgRGBA((byte) 233, (byte) 233, (byte) 233, (byte) 255, button_text_color);

		nvgRGBA((byte) 190, (byte) 190, (byte) 250, (byte) 255, name_view_text_color);
		nvgRGBAf(0.4f, 0.4f, 0.8f, 0.2f, name_view_back_color);

	}

	private int tex_back;
	private int tex_logo;
	private int tex_single, tex_multi, tex_setting;
	private int tex_player_icon;

	private CraftGame game;

	public StateMainMenu() {
		super();
		this.game = (CraftGame) super.game;

		nvgCreateFont(nvg, fontName, CraftGame.DEFAULT_RES_DIRECTORY_STRING + "/fonts/Minecraft-Ascii.ttf");
		this.tex_back = nvgCreateImage(nvg, CraftGame.DEFAULT_RES_DIRECTORY_STRING + "/gui/options_background.png",
				NVG_IMAGE_REPEATX | NVG_IMAGE_REPEATY | NVG_IMAGE_NEAREST);
		this.tex_single = nvgCreateImage(nvg,
				CraftGame.DEFAULT_RES_DIRECTORY_STRING + "/gui/main_menu/single_player.png",
				NVG_IMAGE_GENERATE_MIPMAPS);
		this.tex_multi = nvgCreateImage(nvg, CraftGame.DEFAULT_RES_DIRECTORY_STRING + "/gui/main_menu/multi_player.png",
				NVG_IMAGE_GENERATE_MIPMAPS);
		this.tex_setting = nvgCreateImage(nvg, CraftGame.DEFAULT_RES_DIRECTORY_STRING + "/gui/main_menu/settings.png",
				NVG_IMAGE_GENERATE_MIPMAPS);
		this.tex_logo = nvgCreateImage(nvg, CraftGame.DEFAULT_RES_DIRECTORY_STRING + "/gui/CraftGame.png",
				NVG_IMAGE_GENERATE_MIPMAPS);
		this.tex_player_icon = nvgCreateImage(nvg, game.getPlayer().getIconPath(), NVG_IMAGE_GENERATE_MIPMAPS);

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

		// logo
		{
			nvgImagePattern(nvg, logo_x.getValue(), logo_y.getValue(), logo_width.getValue(), logo_height.getValue(), 0,
					tex_logo, 1, paint);
			nvgBeginPath(nvg);
			nvgRect(nvg, logo_x.getValue(), logo_y.getValue(), logo_width.getValue(), logo_height.getValue());
			nvgFillPaint(nvg, paint);
			nvgFill(nvg);

		}

		// version
		{
			nvgFontSize(nvg, version_text_size.getValue());
			nvgFontFace(nvg, fontName);
			nvgTextAlign(nvg, NVG_ALIGN_BOTTOM | NVG_ALIGN_RIGHT);
			nvgFillColor(nvg, NVGColors.WHITE);
			nvgText(nvg, version_x.getValue(), version_y.getValue(), this.lang.getStringFromLangMap("#game.version"));

		}

		// copyright
		{
			nvgFontSize(nvg, version_text_size.getValue());
			nvgFontFace(nvg, fontName);
			nvgTextAlign(nvg, NVG_ALIGN_BOTTOM | NVG_ALIGN_LEFT);
			nvgFillColor(nvg, NVGColors.WHITE);
			nvgText(nvg, copyright_x.getValue(), copyright_y.getValue(),
					this.lang.getStringFromLangMap("#game.copyright"));

		}

		// single player button
		boolean single_player_button_hover = DisplayUtils.isMouseInBorder(single_button_x.getValue(),
				single_button_y.getValue(), button_width.getValue(), button_height.getValue());
		{
			if (single_player_button_hover) {
				nvgBeginPath(nvg);
				nvgRect(nvg, single_button_x.getValue() - button_border.getValue(),
						single_button_y.getValue() - button_border.getValue(),
						button_width.getValue() + button_border.getValue() * 2,
						button_height.getValue() + button_border.getValue() * 2);
				nvgFillColor(nvg, NVGColors.WHITE);
				nvgFill(nvg);

			}

			nvgImagePattern(nvg, single_button_x.getValue(), single_button_y.getValue(), button_width.getValue(),
					button_height.getValue(), 0, tex_single, 1, paint);
			nvgBeginPath(nvg);
			nvgRect(nvg, single_button_x.getValue(), single_button_y.getValue(), button_width.getValue(),
					button_height.getValue());
			nvgFillPaint(nvg, paint);
			nvgFill(nvg);

			nvgFontSize(nvg, button_text_size.getValue());
			nvgFontFace(nvg, fontName);
			nvgTextAlign(nvg, NVG_ALIGN_CENTER | NVG_ALIGN_MIDDLE);
			if (single_player_button_hover)
				nvgFillColor(nvg, NVGColors.YELLOW);
			else
				nvgFillColor(nvg, button_text_color);
			nvgText(nvg, single_text_x.getValue(), single_text_y.getValue(),
					this.lang.getStringFromLangMap("#main_menu.single_player"));

		}

		// multi player button
		boolean multi_player_button_hover = DisplayUtils.isMouseInBorder(multi_button_x.getValue(),
				multi_button_y.getValue(), button_width.getValue(), button_height.getValue());
		{
			if (multi_player_button_hover) {
				nvgBeginPath(nvg);
				nvgRect(nvg, multi_button_x.getValue() - button_border.getValue(),
						multi_button_y.getValue() - button_border.getValue(),
						button_width.getValue() + button_border.getValue() * 2,
						button_height.getValue() + button_border.getValue() * 2);
				nvgFillColor(nvg, NVGColors.WHITE);
				nvgFill(nvg);

			}

			nvgImagePattern(nvg, multi_button_x.getValue(), multi_button_y.getValue(), button_width.getValue(),
					button_height.getValue(), 0, tex_multi, 1, paint);
			nvgBeginPath(nvg);
			nvgRect(nvg, multi_button_x.getValue(), multi_button_y.getValue(), button_width.getValue(),
					button_height.getValue());
			nvgFillPaint(nvg, paint);
			nvgFill(nvg);

			nvgFontSize(nvg, button_text_size.getValue());
			nvgFontFace(nvg, fontName);
			nvgTextAlign(nvg, NVG_ALIGN_CENTER | NVG_ALIGN_MIDDLE);
			if (multi_player_button_hover)
				nvgFillColor(nvg, NVGColors.YELLOW);
			else
				nvgFillColor(nvg, button_text_color);
			nvgText(nvg, multi_text_x.getValue(), multi_text_y.getValue(),
					this.lang.getStringFromLangMap("#main_menu.multi_player"));

		}

		// setting player button
		boolean setting_player_button_hover = DisplayUtils.isMouseInBorder(setting_button_x.getValue(),
				setting_button_y.getValue(), button_width.getValue(), button_height.getValue());
		{
			if (setting_player_button_hover) {
				nvgBeginPath(nvg);
				nvgRect(nvg, setting_button_x.getValue() - button_border.getValue(),
						setting_button_y.getValue() - button_border.getValue(),
						button_width.getValue() + button_border.getValue() * 2,
						button_height.getValue() + button_border.getValue() * 2);
				nvgFillColor(nvg, NVGColors.WHITE);
				nvgFill(nvg);

			}

			nvgImagePattern(nvg, setting_button_x.getValue(), setting_button_y.getValue(), button_width.getValue(),
					button_height.getValue(), 0, tex_setting, 1, paint);
			nvgBeginPath(nvg);
			nvgRect(nvg, setting_button_x.getValue(), setting_button_y.getValue(), button_width.getValue(),
					button_height.getValue());
			nvgFillPaint(nvg, paint);
			nvgFill(nvg);

			nvgFontSize(nvg, button_text_size.getValue());
			nvgFontFace(nvg, fontName);
			nvgTextAlign(nvg, NVG_ALIGN_CENTER | NVG_ALIGN_MIDDLE);
			if (setting_player_button_hover)
				nvgFillColor(nvg, NVGColors.YELLOW);
			else
				nvgFillColor(nvg, button_text_color);
			nvgText(nvg, setting_text_x.getValue(), setting_text_y.getValue(),
					this.lang.getStringFromLangMap("#main_menu.setting"));

		}

		// nickname
		{
			// measure text width
			nvgFontSize(nvg, name_view_text_size.getValue());
			nvgFontFace(nvg, fontName);
			nvgTextAlign(nvg, NVG_ALIGN_LEFT | NVG_ALIGN_TOP);
			float width = nvgText(nvg, 0, -23333, game.getPlayer().getName());

			nvgBeginPath(nvg);
			nvgRect(nvg,
					name_view_x.getValue()
							- width - name_view_text_size.getValue() / 2 * icon_scale - icon_name_space.getValue(),
					name_view_y.getValue() - icon_scale_width.getValue(),
					width + name_view_text_size.getValue() / 2 * icon_scale + icon_name_space.getValue()
							+ icon_scale_width.getValue(),
					name_view_text_size.getValue() + icon_scale_width.getValue() * 2);
			nvgFillColor(nvg, name_view_back_color);
			nvgFill(nvg);

			nvgFontSize(nvg, name_view_text_size.getValue());
			nvgFontFace(nvg, fontName);
			nvgTextAlign(nvg, NVG_ALIGN_RIGHT | NVG_ALIGN_TOP);
			nvgFillColor(nvg, name_view_text_color);
			nvgText(nvg, name_view_x.getValue(), name_view_y.getValue(), game.getPlayer().getName());

			nvgImagePattern(nvg,
					name_view_x.getValue() - width - name_view_text_size.getValue() * icon_scale
							- icon_name_space.getValue(),
					name_view_y.getValue() - (icon_scale - 1.0f) * name_view_text_size.getValue() / 2,
					name_view_text_size.getValue() * icon_scale, name_view_text_size.getValue() * icon_scale, 0,
					tex_player_icon, 1, paint);
			nvgBeginPath(nvg);
			nvgCircle(nvg,
					name_view_x.getValue() - width - name_view_text_size.getValue() / 2 * icon_scale
							- icon_name_space.getValue(),
					name_view_y.getValue() + name_view_text_size.getValue() / 2,
					name_view_text_size.getValue() / 2 * icon_scale);
			nvgFillPaint(nvg, paint);
			nvgFill(nvg);

		}

	}

	@Override
	public void update() {
		boolean single_player_button_hover = DisplayUtils.isMouseInBorder(single_button_x.getValue(),
				single_button_y.getValue(), button_width.getValue(), button_height.getValue());
		boolean multi_player_button_hover = DisplayUtils.isMouseInBorder(multi_button_x.getValue(),
				multi_button_y.getValue(), button_width.getValue(), button_height.getValue());

		if (game.getDisplay().isMouseDownOnce(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
			if (single_player_button_hover) {
				// game.getRendererManager().setCurrentRenderer(new StateWorldSelect());

			} else if (multi_player_button_hover) {
				game.getRendererManager().setCurrentRenderer(new StateServerSelect());

			}

		}

	}

	@Override
	public void size() {
		super.size();

		logo_x.needEvalAgain();
		logo_y.needEvalAgain();
		logo_width.needEvalAgain();
		logo_height.needEvalAgain();

		version_x.needEvalAgain();
		version_y.needEvalAgain();
		version_text_size.needEvalAgain();

		copyright_x.needEvalAgain();
		copyright_y.needEvalAgain();

		button_width.needEvalAgain();
		button_height.needEvalAgain();
		button_border.needEvalAgain();
		button_text_size.needEvalAgain();

		single_button_x.needEvalAgain();
		single_button_y.needEvalAgain();
		single_text_x.needEvalAgain();
		single_text_y.needEvalAgain();

		multi_button_x.needEvalAgain();
		multi_button_y.needEvalAgain();
		multi_text_x.needEvalAgain();
		multi_text_y.needEvalAgain();

		setting_button_x.needEvalAgain();
		setting_button_y.needEvalAgain();
		setting_text_x.needEvalAgain();
		setting_text_y.needEvalAgain();

		name_view_x.needEvalAgain();
		name_view_y.needEvalAgain();
		name_view_text_size.needEvalAgain();
		icon_name_space.needEvalAgain();
		icon_scale_width.needEvalAgain();

	}

}
