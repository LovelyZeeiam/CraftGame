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

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.nanovg.NVGColor;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import xueli.craftgame.CraftGame;
import xueli.craftgame.state.serverselect.ListEntryServer;
import xueli.game.renderer.NVGRenderer;
import xueli.game.renderer.Toasts.Type;
import xueli.game.renderer.widgets.ButtonGroup;
import xueli.game.renderer.widgets.IListEntry;
import xueli.game.renderer.widgets.ListVertical;
import xueli.game.utils.NVGColors;
import xueli.utils.eval.EvalableFloat;

public class StateServerSelect extends NVGRenderer {
	
	public static StateServerSelect INSTANCE;

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
	
	private static EvalableFloat list_border = new EvalableFloat("4.0 * scale");
	private static EvalableFloat list_border_width = new EvalableFloat("win_width * 0.5");
	private static EvalableFloat list_width = new EvalableFloat(list_border_width.getExpression() + " - 2 * (" + list_border.getExpression() + ")");
	private static EvalableFloat list_border_x = new EvalableFloat("win_width * 0.25");
	private static EvalableFloat list_x = new EvalableFloat(list_border_x.getExpression() + " + (" + list_border.getExpression() + ")");
	private static EvalableFloat list_height = new EvalableFloat(border_height.getExpression() + " - 2 * (" + list_border.getExpression() + ")");
	private static EvalableFloat list_y = new EvalableFloat(border_y.getExpression()+ " + " + list_border.getExpression());
	private static EvalableFloat list_entry_height = new EvalableFloat("60.0 * scale");

	private static EvalableFloat button_margin = new EvalableFloat("10.0 * scale");

	private static NVGColor border_background = NVGColor.create();
	private static NVGColor list_background = NVGColor.create();

	static {
		nvgRGBAf(0.0f, 0.0f, 0.0f, 0.6f, border_background);
		nvgRGBAf(0.05f, 0.05f, 0.05f, 0.5f, list_background);

	}

	private int tex_back;

	private ButtonGroup buttonGroup;
	private ListVertical serverList;

	public StateServerSelect() {
		super();
		INSTANCE = this;

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
		
		serverList = new ListVertical(list_x, list_y, list_width, list_entry_height, list_height, new ArrayList<>());
		readServerList();
		
		
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
			nvgFillColor(nvg, border_background);
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
		
		// button group
		{
			buttonGroup.getButton(0, 1).setEnable(true);
			buttonGroup.getButton(0, 2).setEnable(true);
			buttonGroup.getButton(1, 2).setEnable(true);
			buttonGroup.getButton(1, 3).setEnable(true);
			
			if(serverList.getChoosenEntryID() != -1) {
				buttonGroup.getButton(0, 0).setEnable(true);
				buttonGroup.getButton(0, 1).setEnable(true);
				buttonGroup.getButton(1, 0).setEnable(true);
				buttonGroup.getButton(1, 1).setEnable(true);
				
			} else {
				buttonGroup.getButton(0, 0).setEnable(false);
				buttonGroup.getButton(0, 1).setEnable(false);
				buttonGroup.getButton(1, 0).setEnable(false);
				buttonGroup.getButton(1, 1).setEnable(false);
				
			}
			
			buttonGroup.stroke(nvg, fontName);
			
		}
		
		// server list background
		{
			nvgBeginPath(nvg);
			nvgRect(nvg, list_border_x.getValue(), border_y.getValue(), list_border_width.getValue(), border_height.getValue());
			//nvgRect(nvg, list_x.getValue(), list_y.getValue(), list_width.getValue(), list_height.getValue());
			nvgFillColor(nvg, list_background);
			nvgFill(nvg);

		}
		
		serverList.stroke(nvg, fontName);

		if (game.getDisplay().isMouseDownOnce(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
			// cancel button
			if(buttonGroup.getButton(1, 3).canBePressed()) {
				game.getRendererManager().setCurrentRenderer(new StateMainMenu());
				
			}
			
			// edit button
			
			
			
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
		
		list_border.needEvalAgain();
		list_border_width.needEvalAgain();
		list_width.needEvalAgain();
		list_border_x.needEvalAgain();
		list_x.needEvalAgain();
		list_height.needEvalAgain();
		list_y.needEvalAgain();
		list_entry_height.needEvalAgain();
		
		buttonGroup.size(w, h);
		serverList.size(w, h);
		

	}
	
	@Override
	public void release() {
		super.release();
		
		saveServerList();
		
	}
	
	private void readServerList() {
		Gson gson = new Gson();
		try {
			JsonObject object = gson.fromJson(new FileReader(new File(CraftGame.DEFAULT_WORKING_DIRECTORY_STRING + "/serverlist.json")), JsonObject.class);
			object.entrySet().forEach(e -> {
				String name = e.getKey();
				JsonObject addressObj = e.getValue().getAsJsonObject();
				
				String addr = addressObj.get("addr").getAsString();
				int port = addressObj.get("port").getAsInt();
				
				ListEntryServer entry = new ListEntryServer(name, addr, port);
				serverList.addElement(entry);
				
			});
			
		} catch (Exception e) {
			e.printStackTrace();
			game.getRendererManager().message("Read Server List Failed!", "Please check console: " + e.getClass().getName(), Type.ERROR);
			return;
		}
		
	}
	
	private void saveServerList() {
		Gson gson = new Gson();
		try {
			ArrayList<IListEntry> entries = serverList.getEntries();
			for(IListEntry entry : entries) {
				ListEntryServer server = (ListEntryServer) entry;
				
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			game.getRendererManager().message("Save Server List Failed!", "Please check console: " + e.getClass().getName(), Type.ERROR);
			return;
		}
		
	}

}
