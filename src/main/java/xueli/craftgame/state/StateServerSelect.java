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
import static org.lwjgl.nanovg.NanoVG.nvgRGBAf;
import static org.lwjgl.nanovg.NanoVG.nvgRect;
import static org.lwjgl.nanovg.NanoVG.nvgText;
import static org.lwjgl.nanovg.NanoVG.nvgTextAlign;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.nanovg.NVGColor;

import xueli.craftgame.CraftGame;
import xueli.game.utils.NVGColors;
import xueli.game.utils.renderer.NVGRenderer;
import xueli.game.utils.renderer.widgets.ButtonGroup;
import xueli.utils.eval.EvalableFloat;

public class StateServerSelect extends NVGRenderer {

	private static final String fontName = "GAME";

	private static EvalableFloat select_text_x = new EvalableFloat("win_width * 0.5");
	private static EvalableFloat select_text_y = new EvalableFloat("50 * scale");
	private static EvalableFloat select_text_size = new EvalableFloat("15.0 * scale");

	private static EvalableFloat button_width = new EvalableFloat("600.0 * scale");
	private static EvalableFloat button_height = new EvalableFloat("80.0 * scale");
	private static EvalableFloat button_x = new EvalableFloat(
			"(win_width - (" + button_width.getExpression() + ")) / 2");
	private static EvalableFloat button_y = new EvalableFloat(
			"win_height - 5.0 * scale - (" + button_height.getExpression() + ")");

	private static EvalableFloat border_x = new EvalableFloat("0.0");
	private static EvalableFloat border_y = new EvalableFloat("60.0 * scale");
	private static EvalableFloat border_height = new EvalableFloat(
			button_y.getExpression() + " - 25.0 * scale - (" + border_y.getExpression() + ")");
	private static EvalableFloat border_width = new EvalableFloat("win_width");

	private static EvalableFloat button_margin = new EvalableFloat("10.0 * scale");

	private static NVGColor gray_transparent = NVGColor.create();

	static {
		nvgRGBAf(0.0f, 0.0f, 0.0f, 0.5f, gray_transparent);

	}

	private int tex_back;

	private ButtonGroup buttonGroup;

	public StateServerSelect() {
		super();

		nvgCreateFont(nvg, fontName, CraftGame.DEFAULT_RES_DIRECTORY_STRING + "/fonts/Minecraft-Ascii.ttf");
		this.tex_back = nvgCreateImage(nvg, CraftGame.DEFAULT_RES_DIRECTORY_STRING + "/gui/options_background.png",
				NVG_IMAGE_REPEATX | NVG_IMAGE_REPEATY | NVG_IMAGE_NEAREST);

		String[][] button_grid = {
				{ 
						lang.getStringFromLangMap("#server_select_menu.join"),
						lang.getStringFromLangMap("#server_select_menu.direct"),
						lang.getStringFromLangMap("#server_select_menu.add") 
				},
				{ 		
						lang.getStringFromLangMap("#server_select_menu.edit"),
						lang.getStringFromLangMap("#server_select_menu.delete"),
						lang.getStringFromLangMap("#server_select_menu.refresh"),
						lang.getStringFromLangMap("#server_select_menu.cancel") 
				} 
		};
		buttonGroup = new ButtonGroup(button_x, button_y, button_width, button_height, button_margin, button_grid,
				fontName, select_text_size);

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
					lang.getStringFromLangMap("#server_select_menu.text"));

		}
		
		buttonGroup.getButton(0, 1).setEnable(true);
		buttonGroup.getButton(0, 2).setEnable(true);
		buttonGroup.getButton(1, 2).setEnable(true);
		buttonGroup.getButton(1, 3).setEnable(true);

		buttonGroup.stroke(nvg);

		if (game.getDisplay().isMouseDownOnce(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
			// cancel button
			if(buttonGroup.getButton(1, 3).isMouseHover()) {
				game.getRendererManager().setCurrentRenderer(new StateMainMenu());
				
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

		border_x.needEvalAgain();
		border_y.needEvalAgain();
		border_height.needEvalAgain();
		border_width.needEvalAgain();

		button_margin.needEvalAgain();
		
		buttonGroup.size(w, h);
		

	}

}
