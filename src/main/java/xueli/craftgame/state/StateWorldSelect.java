package xueli.craftgame.state;

import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_BOTTOM;
import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_CENTER;
import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_MIDDLE;
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

public class StateWorldSelect extends NVGRenderer {

	private static final String fontName = "GAME";

	private static EvalableFloat select_text_x = new EvalableFloat("win_width * 0.5");
	private static EvalableFloat select_text_y = new EvalableFloat("50 * scale");
	private static EvalableFloat select_text_size = new EvalableFloat("15.0 * scale");

	private static EvalableFloat button_height = new EvalableFloat("40.0 * scale");
	private static EvalableFloat button_y_line1 = new EvalableFloat(
			"win_height - 30.0 * scale - " + button_height.getExpression() + "* 2");
	private static EvalableFloat button_y_line2 = new EvalableFloat(
			"win_height - 15.0 * scale - " + button_height.getExpression());

	private static EvalableFloat border_x = new EvalableFloat("0.0");
	private static EvalableFloat border_y = new EvalableFloat("60 * scale");
	private static EvalableFloat border_height = new EvalableFloat(
			button_y_line1.getExpression() + " - 15.0 * scale - " + border_y.getExpression());
	private static EvalableFloat border_width = new EvalableFloat("win_width");

	private static EvalableFloat button_width_line1 = new EvalableFloat("305.0 * scale");
	private static EvalableFloat button_width_line2 = new EvalableFloat("200.0 * scale");
	private static EvalableFloat button_margin = new EvalableFloat("10.0 * scale");

	private static EvalableFloat button_border_width = new EvalableFloat("2.0 * scale");
	private static EvalableFloat button_start_x_line1 = new EvalableFloat("(win_width - ("
			+ button_width_line1.getExpression() + ") * 2 - (" + button_margin.getExpression() + ") * 1) / 2");
	private static EvalableFloat button_start_x_line2 = new EvalableFloat("(win_width - ("
			+ button_width_line2.getExpression() + ") * 3 - (" + button_margin.getExpression() + ") * 2) / 2");

	private static NVGColor button_text_color_hover = NVGColor.create();
	private static NVGColor button_text_color = NVGColor.create();

	private static NVGColor button_color = NVGColor.create();
	private static NVGColor button_color_disable = NVGColor.create();

	private static NVGColor gray_transparent = NVGColor.create();
	private static NVGColor gray = NVGColor.create();

	static {
		nvgRGBAf(1, 1, 1, 1, button_text_color_hover);
		nvgRGBAf(0.9f, 0.9f, 0.9f, 1, button_text_color);
		nvgRGBAf(0.4f, 0.4f, 0.4f, 0.8f, button_color);
		nvgRGBAf(0.2f, 0.2f, 0.2f, 0.8f, button_color_disable);
		nvgRGBAf(0.0f, 0.0f, 0.0f, 0.5f, gray_transparent);
		nvgRGBAf(0.7f, 0.7f, 0.7f, 0.7f, gray);

	}

	private int tex_back;

	public StateWorldSelect() {
		super();

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

		// Gui Border
		{
			nvgBeginPath(nvg);
			nvgRect(nvg, border_x.getValue(), border_y.getValue(), border_width.getValue(), border_height.getValue());
			nvgFillColor(nvg, gray_transparent);
			nvgFill(nvg);

		}

		// world select text
		{
			nvgFontSize(nvg, select_text_size.getValue());
			nvgFontFace(nvg, fontName);
			nvgTextAlign(nvg, NVG_ALIGN_BOTTOM | NVG_ALIGN_CENTER);
			nvgFillColor(nvg, NVGColors.WHITE);
			nvgText(nvg, select_text_x.getValue(), select_text_y.getValue(),
					lang.getStringFromLangMap("#world_select_menu.text"));

		}

		boolean play_button_enable = false;
		boolean create_button_enable = true;
		boolean edit_button_enable = false;
		boolean delete_button_enable = false;
		boolean cancel_button_enable = true;

		// Button
		boolean play_button_hover = false;
		{
			play_button_hover = DisplayUtils.isMouseInBorder(
					button_start_x_line1.getValue() + (button_margin.getValue() + button_width_line1.getValue()) * 0,
					button_y_line1.getValue(), button_width_line1.getValue(), button_height.getValue());

			nvgBeginPath(nvg);
			nvgRect(nvg,
					button_start_x_line1.getValue() - button_border_width.getValue()
							+ (button_margin.getValue() + button_width_line1.getValue()) * 0,
					button_y_line1.getValue() - button_border_width.getValue(),
					button_width_line1.getValue() + 2 * button_border_width.getValue(),
					button_height.getValue() + 2 * button_border_width.getValue());
			if (play_button_enable && play_button_hover)
				nvgFillColor(nvg, gray);
			else
				nvgFillColor(nvg, NVGColors.BLACK);
			nvgFill(nvg);

			nvgBeginPath(nvg);
			nvgRect(nvg,
					button_start_x_line1.getValue() + (button_margin.getValue() + button_width_line1.getValue()) * 0,
					button_y_line1.getValue(), button_width_line1.getValue(), button_height.getValue());
			if (play_button_enable)
				nvgFillColor(nvg, button_color);
			else
				nvgFillColor(nvg, button_color_disable);
			nvgFill(nvg);

			nvgFontSize(nvg, select_text_size.getValue());
			nvgFontFace(nvg, fontName);
			nvgTextAlign(nvg, NVG_ALIGN_MIDDLE | NVG_ALIGN_CENTER);
			if (play_button_enable && play_button_hover)
				nvgFillColor(nvg, button_text_color_hover);
			else
				nvgFillColor(nvg, button_text_color);
			nvgText(nvg,
					button_start_x_line1.getValue() + button_width_line1.getValue() / 2
							+ (button_margin.getValue() + button_width_line1.getValue()) * 0,
					button_y_line1.getValue() + button_height.getValue() / 2,
					lang.getStringFromLangMap("#world_select_menu.play"));

		}

		boolean create_button_hover = false;
		{
			create_button_hover = DisplayUtils.isMouseInBorder(
					button_start_x_line1.getValue() + (button_margin.getValue() + button_width_line1.getValue()) * 1,
					button_y_line1.getValue(), button_width_line1.getValue(), button_height.getValue());

			nvgBeginPath(nvg);
			nvgRect(nvg,
					button_start_x_line1.getValue() - button_border_width.getValue()
							+ (button_margin.getValue() + button_width_line1.getValue()) * 1,
					button_y_line1.getValue() - button_border_width.getValue(),
					button_width_line1.getValue() + 2 * button_border_width.getValue(),
					button_height.getValue() + 2 * button_border_width.getValue());
			if (create_button_enable && create_button_hover)
				nvgFillColor(nvg, gray);
			else
				nvgFillColor(nvg, NVGColors.BLACK);
			nvgFill(nvg);

			nvgBeginPath(nvg);
			nvgRect(nvg,
					button_start_x_line1.getValue() + (button_margin.getValue() + button_width_line1.getValue()) * 1,
					button_y_line1.getValue(), button_width_line1.getValue(), button_height.getValue());
			if (create_button_enable)
				nvgFillColor(nvg, button_color);
			else
				nvgFillColor(nvg, button_color_disable);
			nvgFill(nvg);

			nvgFontSize(nvg, select_text_size.getValue());
			nvgFontFace(nvg, fontName);
			nvgTextAlign(nvg, NVG_ALIGN_MIDDLE | NVG_ALIGN_CENTER);
			if (create_button_enable && create_button_hover)
				nvgFillColor(nvg, button_text_color_hover);
			else
				nvgFillColor(nvg, button_text_color);
			nvgText(nvg,
					button_start_x_line1.getValue() + button_width_line1.getValue() / 2
							+ (button_margin.getValue() + button_width_line1.getValue()) * 1,
					button_y_line1.getValue() + button_height.getValue() / 2,
					lang.getStringFromLangMap("#world_select_menu.create"));

		}

		boolean edit_button_hover = false;
		{
			edit_button_hover = DisplayUtils.isMouseInBorder(
					button_start_x_line2.getValue() + (button_margin.getValue() + button_width_line2.getValue()) * 0,
					button_y_line2.getValue(), button_width_line2.getValue(), button_height.getValue());

			nvgBeginPath(nvg);
			nvgRect(nvg,
					button_start_x_line2.getValue() - button_border_width.getValue()
							+ (button_margin.getValue() + button_width_line2.getValue()) * 0,
					button_y_line2.getValue() - button_border_width.getValue(),
					button_width_line2.getValue() + 2 * button_border_width.getValue(),
					button_height.getValue() + 2 * button_border_width.getValue());
			if (edit_button_enable && edit_button_hover)
				nvgFillColor(nvg, gray);
			else
				nvgFillColor(nvg, NVGColors.BLACK);
			nvgFill(nvg);

			nvgBeginPath(nvg);
			nvgRect(nvg,
					button_start_x_line2.getValue() + (button_margin.getValue() + button_width_line2.getValue()) * 0,
					button_y_line2.getValue(), button_width_line2.getValue(), button_height.getValue());
			if (edit_button_enable)
				nvgFillColor(nvg, button_color);
			else
				nvgFillColor(nvg, button_color_disable);
			nvgFill(nvg);

			nvgFontSize(nvg, select_text_size.getValue());
			nvgFontFace(nvg, fontName);
			nvgTextAlign(nvg, NVG_ALIGN_MIDDLE | NVG_ALIGN_CENTER);
			if (edit_button_enable && edit_button_hover)
				nvgFillColor(nvg, button_text_color_hover);
			else
				nvgFillColor(nvg, button_text_color);
			nvgText(nvg,
					button_start_x_line2.getValue() + button_width_line2.getValue() / 2
							+ (button_margin.getValue() + button_width_line2.getValue()) * 0,
					button_y_line2.getValue() + button_height.getValue() / 2,
					lang.getStringFromLangMap("#world_select_menu.edit"));

		}

		boolean delete_button_hover = false;
		{
			delete_button_hover = DisplayUtils.isMouseInBorder(
					button_start_x_line2.getValue() + (button_margin.getValue() + button_width_line2.getValue()) * 1,
					button_y_line2.getValue(), button_width_line2.getValue(), button_height.getValue());

			nvgBeginPath(nvg);
			nvgRect(nvg,
					button_start_x_line2.getValue() - button_border_width.getValue()
							+ (button_margin.getValue() + button_width_line2.getValue()) * 1,
					button_y_line2.getValue() - button_border_width.getValue(),
					button_width_line2.getValue() + 2 * button_border_width.getValue(),
					button_height.getValue() + 2 * button_border_width.getValue());
			if (delete_button_enable && delete_button_hover)
				nvgFillColor(nvg, gray);
			else
				nvgFillColor(nvg, NVGColors.BLACK);
			nvgFill(nvg);

			nvgBeginPath(nvg);
			nvgRect(nvg,
					button_start_x_line2.getValue() + (button_margin.getValue() + button_width_line2.getValue()) * 1,
					button_y_line2.getValue(), button_width_line2.getValue(), button_height.getValue());
			if (delete_button_enable)
				nvgFillColor(nvg, button_color);
			else
				nvgFillColor(nvg, button_color_disable);
			nvgFill(nvg);

			nvgFontSize(nvg, select_text_size.getValue());
			nvgFontFace(nvg, fontName);
			nvgTextAlign(nvg, NVG_ALIGN_MIDDLE | NVG_ALIGN_CENTER);
			if (delete_button_enable && delete_button_hover)
				nvgFillColor(nvg, button_text_color_hover);
			else
				nvgFillColor(nvg, button_text_color);
			nvgText(nvg,
					button_start_x_line2.getValue() + button_width_line2.getValue() / 2
							+ (button_margin.getValue() + button_width_line2.getValue()) * 1,
					button_y_line2.getValue() + button_height.getValue() / 2,
					lang.getStringFromLangMap("#world_select_menu.delete"));

		}

		boolean cancel_button_hover = false;
		{
			cancel_button_hover = DisplayUtils.isMouseInBorder(
					button_start_x_line2.getValue() + (button_margin.getValue() + button_width_line2.getValue()) * 2,
					button_y_line2.getValue(), button_width_line2.getValue(), button_height.getValue());

			nvgBeginPath(nvg);
			nvgRect(nvg,
					button_start_x_line2.getValue() - button_border_width.getValue()
							+ (button_margin.getValue() + button_width_line2.getValue()) * 2,
					button_y_line2.getValue() - button_border_width.getValue(),
					button_width_line2.getValue() + 2 * button_border_width.getValue(),
					button_height.getValue() + 2 * button_border_width.getValue());
			if (cancel_button_enable && cancel_button_hover)
				nvgFillColor(nvg, gray);
			else
				nvgFillColor(nvg, NVGColors.BLACK);
			nvgFill(nvg);

			nvgBeginPath(nvg);
			nvgRect(nvg,
					button_start_x_line2.getValue() + (button_margin.getValue() + button_width_line2.getValue()) * 2,
					button_y_line2.getValue(), button_width_line2.getValue(), button_height.getValue());
			if (cancel_button_enable)
				nvgFillColor(nvg, button_color);
			else
				nvgFillColor(nvg, button_color_disable);
			nvgFill(nvg);

			nvgFontSize(nvg, select_text_size.getValue());
			nvgFontFace(nvg, fontName);
			nvgTextAlign(nvg, NVG_ALIGN_MIDDLE | NVG_ALIGN_CENTER);
			if (cancel_button_enable && cancel_button_hover)
				nvgFillColor(nvg, button_text_color_hover);
			else
				nvgFillColor(nvg, button_text_color);
			nvgText(nvg,
					button_start_x_line2.getValue() + button_width_line2.getValue() / 2
							+ (button_margin.getValue() + button_width_line2.getValue()) * 2,
					button_y_line2.getValue() + button_height.getValue() / 2,
					lang.getStringFromLangMap("#world_select_menu.cancel"));

		}

		if (game.getDisplay().isMouseDownOnce(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
			if (cancel_button_enable && cancel_button_hover) {
				game.getRendererManager().setCurrentRenderer(new StateMainMenu());

			} else if (create_button_enable && create_button_hover) {
				// game.getRendererManager().setCurrentRenderer(new StateWorldLoading("test",
				// Game.DEFAULT_WORKING_DIRECTORY_STRING + DataFolder.SAVES));

			}

		}

	}

	@Override
	public void size(int w, int h) {
		super.size(w, h);

		select_text_x.needEvalAgain();
		select_text_y.needEvalAgain();
		select_text_size.needEvalAgain();

		button_height.needEvalAgain();
		button_y_line1.needEvalAgain();
		button_y_line2.needEvalAgain();

		border_x.needEvalAgain();
		border_y.needEvalAgain();
		border_height.needEvalAgain();
		border_width.needEvalAgain();

		button_width_line1.needEvalAgain();
		button_width_line2.needEvalAgain();
		button_margin.needEvalAgain();

		button_border_width.needEvalAgain();
		button_start_x_line1.needEvalAgain();
		button_start_x_line2.needEvalAgain();

	}

}
