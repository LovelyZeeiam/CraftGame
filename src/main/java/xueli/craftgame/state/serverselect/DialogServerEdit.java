package xueli.craftgame.state.serverselect;

import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_LEFT;
import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_TOP;
import static org.lwjgl.nanovg.NanoVG.NVG_IMAGE_NEAREST;
import static org.lwjgl.nanovg.NanoVG.nvgBeginPath;
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
import org.lwjgl.nanovg.NVGPaint;

import xueli.craftgame.CraftGame;
import xueli.game.Game;
import xueli.game.display.DisplayUtils;
import xueli.game.lang.LangManager;
import xueli.game.renderer.widgets.Button;
import xueli.game.renderer.widgets.Dialog;
import xueli.game.renderer.widgets.DialogManager;
import xueli.game.renderer.widgets.IListEntry;
import xueli.game.renderer.widgets.TextBox;
import xueli.game.utils.NVGColors;
import xueli.utils.eval.EvalableFloat;

public class DialogServerEdit extends Dialog {

	private static EvalableFloat dialog_x = new EvalableFloat("(win_width - 632 * 1.1 * scale) / 2");
	private static EvalableFloat dialog_y = new EvalableFloat("(win_height - 358 * 1.1 * scale) / 2");
	private static EvalableFloat dialog_width = new EvalableFloat("632 * 1.1 * scale");
	private static EvalableFloat dialog_height = new EvalableFloat("358 * 1.1 * scale");

	private static EvalableFloat close_button_x = new EvalableFloat(
			"(win_width - 632 * 1.1 * scale) / 2 + 1.1 * scale * 590");
	private static EvalableFloat close_button_y = new EvalableFloat(
			"(win_height - 358 * 1.1 * scale) / 2 + 1.1 * scale * 8");
	private static EvalableFloat close_button_size = new EvalableFloat("1.1 * scale * 33");

	private static EvalableFloat title_x = new EvalableFloat("(win_width - 632 * 1.1 * scale) / 2 + 1.1 * scale * 20");
	private static EvalableFloat title_y = new EvalableFloat("(win_height - 358 * 1.1 * scale) / 2 + 1.1 * scale * 20");
	private static EvalableFloat title_font_size = new EvalableFloat("1.1 * scale * 14");

	private static EvalableFloat remove_button_x = new EvalableFloat(
			"(win_width - 632 * 1.1 * scale) / 2 + 1.1 * scale * 15.5");
	private static EvalableFloat save_button_x = new EvalableFloat(
			"(win_width - 632 * 1.1 * scale) / 2 + 1.1 * scale * 319.5");
	private static EvalableFloat button_y = new EvalableFloat(
			"(win_height - 358 * 1.1 * scale) / 2 + 1.1 * scale * 284");
	private static EvalableFloat button_width = new EvalableFloat("297 * 1.1 * scale");
	private static EvalableFloat button_height = new EvalableFloat("60 * 1.1 * scale");
	private static EvalableFloat button_font_size = new EvalableFloat("1.1 * scale * 14");

	private static EvalableFloat content_x = new EvalableFloat("(win_width - 632 * 1.1 * scale) / 2 + 1.1 * scale * 20");
	private static EvalableFloat content_width = new EvalableFloat("1.1 * scale * 589");
	private static EvalableFloat content_font_size = new EvalableFloat("1.1 * scale * 13");
	private static EvalableFloat content_text_box_height = new EvalableFloat("1.1 * scale * 49");
	private static EvalableFloat content_text_1_y = new EvalableFloat("(win_height - 358 * 1.1 * scale) / 2 + 1.1 * scale * 50");
	private static EvalableFloat content_textbox_1_y = new EvalableFloat("(win_height - 358 * 1.1 * scale) / 2 + 1.1 * scale * 68");
	private static EvalableFloat content_text_2_y = new EvalableFloat("(win_height - 358 * 1.1 * scale) / 2 + 1.1 * scale * 126");
	private static EvalableFloat content_textbox_2_y = new EvalableFloat("(win_height - 358 * 1.1 * scale) / 2 + 1.1 * scale * 144");
	private static EvalableFloat content_text_3_y = new EvalableFloat("(win_height - 358 * 1.1 * scale) / 2 + 1.1 * scale * 202");
	private static EvalableFloat content_textbox_3_y = new EvalableFloat("(win_height - 358 * 1.1 * scale) / 2 + 1.1 * scale * 220");
	
	private static NVGPaint paint = NVGPaint.create();
	
	private static NVGColor content_textColor = NVGColor.create();
	
	static {
		nvgRGBAf(0.8f, 0.8f, 0.8f, 1.0f, content_textColor);
		
	}
	
	private ListEntryServer entry;

	private LangManager langManager;
	private int tex_background;
	private int tex_close, tex_chosen_close;

	private Button removeButton, saveButton;
	private TextBox nameTextBox, addrTextBox, portTextBox;

	public DialogServerEdit(IListEntry entry, DialogManager manager, long nvg, LangManager langManager) {
		super(manager);
		this.entry = (ListEntryServer) entry;
		this.langManager = langManager;

		this.tex_background = nvgCreateImage(nvg,
				CraftGame.DEFAULT_RES_DIRECTORY_STRING + "/gui/server_select/server_dialog.png", NVG_IMAGE_NEAREST);
		this.tex_close = nvgCreateImage(nvg, CraftGame.DEFAULT_RES_DIRECTORY_STRING + "/gui/widgets/close.png",
				NVG_IMAGE_NEAREST);
		this.tex_chosen_close = nvgCreateImage(nvg,
				CraftGame.DEFAULT_RES_DIRECTORY_STRING + "/gui/widgets/chosen_close.png", NVG_IMAGE_NEAREST);

		this.removeButton = new Button(remove_button_x, button_y, button_width, button_height,
				langManager.getStringFromLangMap("#server_select_edit_dialog.remove"), button_font_size, true);
		this.saveButton = new Button(save_button_x, button_y, button_width, button_height,
				langManager.getStringFromLangMap("#server_select_edit_dialog.save"), button_font_size, true);
		
		this.nameTextBox = new TextBox(content_x, content_textbox_1_y, content_width, content_text_box_height, langManager.getStringFromLangMap("#server_select_edit_dialog.name.hint"), nvg);
		this.addrTextBox = new TextBox(content_x, content_textbox_2_y, content_width, content_text_box_height, langManager.getStringFromLangMap("#server_select_edit_dialog.addr.hint"), nvg);
		this.portTextBox = new TextBox(content_x, content_textbox_3_y, content_width, content_text_box_height, null, nvg);
		
		
		
	}

	@Override
	public void stroke(long nvg, String fontName) {
		// background
		{
			nvgImagePattern(nvg, dialog_x.getValue(), dialog_y.getValue(), dialog_width.getValue(),
					dialog_height.getValue(), 0, tex_background, 1, paint);
			nvgBeginPath(nvg);
			nvgRect(nvg, dialog_x.getValue(), dialog_y.getValue(), dialog_width.getValue(), dialog_height.getValue());
			nvgFillPaint(nvg, paint);
			nvgFill(nvg);

		}

		// close button
		{
			if (DisplayUtils.isMouseInBorder(close_button_x.getValue(), close_button_y.getValue(),
					close_button_size.getValue(), close_button_size.getValue()))
				nvgImagePattern(nvg, close_button_x.getValue(), close_button_y.getValue(), close_button_size.getValue(),
						close_button_size.getValue(), 0, tex_chosen_close, 1, paint);
			else
				nvgImagePattern(nvg, close_button_x.getValue(), close_button_y.getValue(), close_button_size.getValue(),
						close_button_size.getValue(), 0, tex_close, 1, paint);
			nvgBeginPath(nvg);
			nvgRect(nvg, close_button_x.getValue(), close_button_y.getValue(), close_button_size.getValue(),
					close_button_size.getValue());
			nvgFillPaint(nvg, paint);
			nvgFill(nvg);

		}

		// title
		{
			nvgFontSize(nvg, title_font_size.getValue());
			nvgFontFace(nvg, fontName);
			nvgTextAlign(nvg, NVG_ALIGN_TOP | NVG_ALIGN_LEFT);
			nvgFillColor(nvg, NVGColors.WHITE);
			nvgText(nvg, title_x.getValue(), title_y.getValue(),
					langManager.getStringFromLangMap("#server_select_edit_dialog.title"));
		}

		this.removeButton.stroke(nvg, fontName);
		this.saveButton.stroke(nvg, fontName);
		
		// texts
		{
			nvgFontSize(nvg, content_font_size.getValue());
			nvgFontFace(nvg, fontName);
			nvgTextAlign(nvg, NVG_ALIGN_TOP | NVG_ALIGN_LEFT);
			nvgFillColor(nvg, content_textColor);
			nvgText(nvg, content_x.getValue(), content_text_1_y.getValue(),
					langManager.getStringFromLangMap("#server_select_edit_dialog.name"));
			nvgText(nvg, content_x.getValue(), content_text_2_y.getValue(),
					langManager.getStringFromLangMap("#server_select_edit_dialog.addr"));
			nvgText(nvg, content_x.getValue(), content_text_3_y.getValue(),
					langManager.getStringFromLangMap("#server_select_edit_dialog.port"));
			
		}
		
		this.nameTextBox.stroke(nvg, fontName);
		this.addrTextBox.stroke(nvg, fontName);
		this.portTextBox.stroke(nvg, fontName);
		
		

	}

	@Override
	public void update() {
		this.removeButton.update();
		this.saveButton.update();

		this.nameTextBox.update();
		this.addrTextBox.update();
		this.portTextBox.update();
		
		if (Game.INSTANCE_GAME.getDisplay().isMouseDownOnce(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
			if (DisplayUtils.isMouseInBorder(close_button_x.getValue(), close_button_y.getValue(),
					close_button_size.getValue(), close_button_size.getValue())) {
				manager.handleDialogExit(null);

			}

		}

	}

	@Override
	public String getSignature() {
		return "EDIT_SERVER_LIST_ENTRY";
	}

	@Override
	public void size() {
		dialog_x.needEvalAgain();
		dialog_y.needEvalAgain();
		dialog_width.needEvalAgain();
		dialog_height.needEvalAgain();

		close_button_x.needEvalAgain();
		close_button_y.needEvalAgain();
		close_button_size.needEvalAgain();

		title_x.needEvalAgain();
		title_y.needEvalAgain();
		title_font_size.needEvalAgain();

		this.removeButton.size();
		this.saveButton.size();
		
		content_x.needEvalAgain();
		content_width.needEvalAgain();
		content_font_size.needEvalAgain();
		content_text_box_height.needEvalAgain();
		content_text_1_y.needEvalAgain();
		content_textbox_1_y.needEvalAgain();
		content_text_2_y.needEvalAgain();
		content_textbox_2_y.needEvalAgain();
		content_text_3_y.needEvalAgain();
		content_textbox_3_y.needEvalAgain();
		
		this.nameTextBox.size();
		this.addrTextBox.size();
		this.portTextBox.size();

	}

}
