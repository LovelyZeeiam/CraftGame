package xueli.craftgame.inventory;

import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.nanovg.NanoVGGL3.*;

import java.util.ArrayList;
import java.util.HashMap;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.nanovg.NVGLUFramebuffer;
import org.lwjgl.opengl.GL11;

import xueli.game.renderer.NVGRenderer;
import xueli.game.utils.NVGColors;
import xueli.game.utils.math.MathUtils;
import xueli.game.vector.Vector2i;
import xueli.utils.Codepoint;
import xueli.utils.Table;
import xueli.utils.eval.EvalableFloat;
import xueli.utils.io.Files;

public class InventoryRenderer extends NVGRenderer {

	// TODO: This type of value should be defined in external files, annotated to
	// make it easier to be found and modified by the game
	private static final int[] INV_VIEW_WIDTHS = { 6, 7, 6 };
	private static final int[][] INV_CHOSEN_KEYBOARD_MAPPING = { { 'Y', 'U', 'I', 'O', 'P', '[' },
			{ 'G', 'H', 'J', 'K', 'L', ';', '\'' }, { 'B', 'N', 'M', ',', '.', '/' }, };
	private static final EvalableFloat INV_VIEW_ITEM_SIZE = new EvalableFloat("60.0 * scale");
	private static final EvalableFloat INV_VIEW_PADDING = new EvalableFloat("4.5 * scale");
	private static final EvalableFloat INV_VIEW_MARGIN = new EvalableFloat("5.0 * scale");
	private static final EvalableFloat INV_VIEW_LETTER_HINT_MARGIN = new EvalableFloat("1.0 * scale");
	private static final EvalableFloat INV_VIEW_LETTER_HINT_SIZE = new EvalableFloat("18 * scale");
	private static final EvalableFloat INV_VIEW_LETTER_HINT_BACKGROUND_SIZE_OFFSET = new EvalableFloat("-3.0 * scale");
	private static final EvalableFloat INV_VIEW_ARROW_SIZE = new EvalableFloat("20.0 * scale");
	
	private static final EvalableFloat INV_HUD_WIDTH = new EvalableFloat("364 * scale");
	private static final EvalableFloat INV_HUD_HEIGHT = new EvalableFloat("44 * scale");
	private static final EvalableFloat INV_HUD_SLOT_OFFSET_Y = new EvalableFloat("6 * scale");
	private static final EvalableFloat INV_HUD_FIRST_SLOT_OFFSET_X = new EvalableFloat("5 * scale");
	private static final EvalableFloat INV_HUD_SLOT_SIZE = new EvalableFloat("34 * scale");
	private static final EvalableFloat INV_HUD_POINTER_OFFSET = new EvalableFloat("40 * scale");
	private static final EvalableFloat INV_HUD_CHOOSEN_SIZE_OFFSET = new EvalableFloat("-3.0 * scale");

	private static final String FONT_NAME = "Inventory";

	private Inventory inventory;
	private ArrayList<NVGLUFramebuffer> pages = new ArrayList<>();
	private ArrayList<Table<InventoryItem>> itemPages = new ArrayList<>();
	private float page_width, page_height;

	private HashMap<Integer, Vector2i> keyboardMapping = new HashMap<>();
	private HashMap<Vector2i, String> mappedKeyboard = new HashMap<>();
	
	private boolean choosenViewVisible = false;
	private int chosenViewPage = 0;

	private int tex_item_frame;
	private int tex_inventory_tab, tex_inventory_tab_chosen;
	private int tex_arrow_up, tex_arrow_down;

	public InventoryRenderer(Inventory inventory) {
		super();
		this.inventory = inventory;

		this.inventory.getItems().forEach(b -> b.renderInit(nvg));
		this.tex_item_frame = nvgCreateImage(nvg,
				Files.getResourcePackedInJar("textures/hud/inventory/item_frame.png").getPath(), NVG_IMAGE_NEAREST);
		this.tex_inventory_tab = nvgCreateImage(nvg,
				Files.getResourcePackedInJar("textures/hud/inventory/inventory_tab.png").getPath(), NVG_IMAGE_NEAREST);
		this.tex_inventory_tab_chosen = nvgCreateImage(nvg,
				Files.getResourcePackedInJar("textures/hud/inventory/inventory_tab_chosen.png").getPath(),
				NVG_IMAGE_NEAREST);
		this.tex_arrow_up = nvgCreateImage(nvg,
				Files.getResourcePackedInJar("textures/hud/inventory/arrow_up.png").getPath(), NVG_IMAGE_NEAREST);
		this.tex_arrow_down = nvgCreateImage(nvg,
				Files.getResourcePackedInJar("textures/hud/inventory/arrow_down.png").getPath(), NVG_IMAGE_NEAREST);
		
		nvgCreateFont(nvg, FONT_NAME, "res/fonts/minecraft-ten.ttf");

		// SEPARATE PAGES
		parseKeyboardMapping();
		preparePages();

	}

	private void parseKeyboardMapping() {
		for (int i = 0; i < INV_CHOSEN_KEYBOARD_MAPPING.length; i++) {
			int[] columes = INV_CHOSEN_KEYBOARD_MAPPING[i];
			for (int j = 0; j < columes.length; j++) {
				int ch = columes[j];
				keyboardMapping.put(ch, new Vector2i(i, j));
				mappedKeyboard.put(new Vector2i(i, j), Codepoint.codepointToString(ch));
			}
		}

	}

	private void preparePages() {
		pages.clear();

		int columeCount = INV_VIEW_WIDTHS.length;
		int allCount = inventory.getItems().size();

		int itemInOnePage = 0;
		for (int i = 0; i < INV_VIEW_WIDTHS.length; i++) {
			itemInOnePage += INV_VIEW_WIDTHS[i];
		}

		int maxWidthCount = MathUtils.max(INV_VIEW_WIDTHS);
		page_width = maxWidthCount * INV_VIEW_ITEM_SIZE.getValue() + (maxWidthCount - 1) * INV_VIEW_PADDING.getValue();
		page_height = columeCount * INV_VIEW_ITEM_SIZE.getValue() + (columeCount - 1) * INV_VIEW_PADDING.getValue();

		ArrayList<ArrayList<InventoryItem>> pageItems = new ArrayList<>();
		{
			int pageCount = (int) Math.ceil((double) allCount / itemInOnePage);
			for (int i = 0; i < pageCount; i++) {
				pageItems.add(new ArrayList<>());
				itemPages.add(new Table<>());
			}
			for (int i = 0; i < allCount; i++) {
				pageItems.get(i / itemInOnePage).add(inventory.getItems().get(i));
			}
		}

		for (int page = 0; page < pageItems.size(); page++) {
			ArrayList<InventoryItem> j = pageItems.get(page);

			NVGLUFramebuffer buf = nvgluCreateFramebuffer(nvg, (int) page_width, (int) page_height, 0);
			nvgluBindFramebuffer(nvg, buf);
			GL11.glViewport(0, 0, (int) page_width, (int) page_height);
			nvgBeginFrame(nvg, page_width, page_height, page_width / page_height);

			int colume = 0, pointer = 0;

			for (InventoryItem inventoryItem : j) {
				int maxItemLine = INV_VIEW_WIDTHS[colume];

				float xInBuf = (maxWidthCount - maxItemLine) * INV_VIEW_ITEM_SIZE.getValue() / 2.0f
						+ pointer * (INV_VIEW_ITEM_SIZE.getValue() + INV_VIEW_PADDING.getValue());
				float yInBuf = colume * (INV_VIEW_ITEM_SIZE.getValue() + INV_VIEW_PADDING.getValue());

				nvgImagePattern(nvg, xInBuf, yInBuf, INV_VIEW_ITEM_SIZE.getValue(), INV_VIEW_ITEM_SIZE.getValue(), 0,
						tex_item_frame, 1, paint);
				nvgBeginPath(nvg);
				nvgRoundedRect(nvg, xInBuf, yInBuf, INV_VIEW_ITEM_SIZE.getValue(), INV_VIEW_ITEM_SIZE.getValue(), 0);
				nvgFillPaint(nvg, paint);
				nvgFill(nvg);

				inventoryItem.renderSlot(xInBuf + INV_VIEW_MARGIN.getValue(), yInBuf + INV_VIEW_MARGIN.getValue(),
						INV_VIEW_ITEM_SIZE.getValue() - INV_VIEW_MARGIN.getValue() * 2,
						INV_VIEW_ITEM_SIZE.getValue() - INV_VIEW_MARGIN.getValue() * 2, nvg);

				nvgBeginPath(nvg);
				nvgRect(nvg,
						xInBuf + INV_VIEW_LETTER_HINT_MARGIN.getValue()
								- INV_VIEW_LETTER_HINT_BACKGROUND_SIZE_OFFSET.getValue() / 2.0f,
						yInBuf + INV_VIEW_LETTER_HINT_MARGIN.getValue()
								- INV_VIEW_LETTER_HINT_BACKGROUND_SIZE_OFFSET.getValue() / 2.0f,
						INV_VIEW_LETTER_HINT_SIZE.getValue() + INV_VIEW_LETTER_HINT_BACKGROUND_SIZE_OFFSET.getValue(),
						INV_VIEW_LETTER_HINT_SIZE.getValue() + INV_VIEW_LETTER_HINT_BACKGROUND_SIZE_OFFSET.getValue());
				nvgFillColor(nvg, NVGColors.WHITE);
				nvgFill(nvg);

				nvgFontSize(nvg, INV_VIEW_LETTER_HINT_SIZE.getValue());
				nvgFontFace(nvg, FONT_NAME);
				nvgFillColor(nvg, NVGColors.BLACK);
				nvgTextAlign(nvg, NVG_ALIGN_CENTER | NVG_ALIGN_MIDDLE);
				nvgText(nvg, xInBuf + INV_VIEW_LETTER_HINT_MARGIN.getValue() + INV_VIEW_LETTER_HINT_SIZE.getValue() / 2,
						yInBuf + INV_VIEW_LETTER_HINT_MARGIN.getValue() + INV_VIEW_LETTER_HINT_SIZE.getValue() / 2,
						mappedKeyboard.get(new Vector2i(colume, pointer)));

				itemPages.get(page).put(pointer, colume, inventoryItem);
				pointer++;
				if (pointer >= maxItemLine) {
					colume++;
					pointer = 0;
				}

			}

			nvgEndFrame(nvg);
			nvgluBindFramebuffer(nvg, null);

			pages.add(buf);

		}

	}

	@Override
	public void update() {
		if (game.getDisplay().isMouseGrabbed()) {
			if (game.getDisplay().isKeyDownOnce(GLFW.GLFW_KEY_E)) {
				choosenViewVisible = !choosenViewVisible;
			}

			if (choosenViewVisible) {
				if (game.getDisplay().isKeyDownOnce(GLFW.GLFW_KEY_DOWN))
					chosenViewPage++;
				if (game.getDisplay().isKeyDownOnce(GLFW.GLFW_KEY_UP))
					chosenViewPage--;
				chosenViewPage = Math.floorMod(chosenViewPage, pages.size());
				
				keyboardMapping.forEach((key, v) -> {
					if(game.getDisplay().isKeyDownOnce(key)) {
						InventoryItem item = itemPages.get(chosenViewPage).get(v.y, v.x);
						item.onHovered(inventory.getPlayer());
						inventory.getSlots()[inventory.getChosenSlotId()] = item;
						return;
					}
				});
				
			}

		}

	}

	@Override
	public void stroke() {
		/**
		 * A fairly new idea about this~ We don't provide a UI specially for the
		 * inventory, but make it controlled by keyboard so that player can control
		 * items when moving
		 */
		if (choosenViewVisible) {
			nvgImagePattern(nvg, game.getWidth() - INV_VIEW_PADDING.getValue() - page_width,
					INV_VIEW_PADDING.getValue(), page_width, page_height, 0, pages.get(chosenViewPage).image(), 1,
					paint);
			nvgBeginPath(nvg);
			nvgRoundedRect(nvg, game.getWidth() - INV_VIEW_PADDING.getValue() - page_width, INV_VIEW_PADDING.getValue(),
					page_width, page_height, 0);
			nvgFillPaint(nvg, paint);
			nvgFill(nvg);
			
			nvgImagePattern(nvg, game.getWidth() - INV_VIEW_PADDING.getValue() - INV_VIEW_ARROW_SIZE.getValue(),
					INV_VIEW_PADDING.getValue() + page_height - INV_VIEW_ARROW_SIZE.getValue(), INV_VIEW_ARROW_SIZE.getValue(), INV_VIEW_ARROW_SIZE.getValue(), 0, tex_arrow_down, 1,
					paint);
			nvgBeginPath(nvg);
			nvgRoundedRect(nvg, game.getWidth() - INV_VIEW_PADDING.getValue() - INV_VIEW_ARROW_SIZE.getValue(),
					INV_VIEW_PADDING.getValue() + page_height - INV_VIEW_ARROW_SIZE.getValue(), INV_VIEW_ARROW_SIZE.getValue(), INV_VIEW_ARROW_SIZE.getValue(), 0);
			nvgFillPaint(nvg, paint);
			nvgFill(nvg);
			
			nvgImagePattern(nvg, game.getWidth() - INV_VIEW_PADDING.getValue() - INV_VIEW_ARROW_SIZE.getValue(),
					INV_VIEW_PADDING.getValue(), INV_VIEW_ARROW_SIZE.getValue(), INV_VIEW_ARROW_SIZE.getValue(), 0, tex_arrow_up, 1,
					paint);
			nvgBeginPath(nvg);
			nvgRoundedRect(nvg, game.getWidth() - INV_VIEW_PADDING.getValue() - INV_VIEW_ARROW_SIZE.getValue(),
					INV_VIEW_PADDING.getValue(), INV_VIEW_ARROW_SIZE.getValue(), INV_VIEW_ARROW_SIZE.getValue(), 0);
			nvgFillPaint(nvg, paint);
			nvgFill(nvg);

		}

		float inv_hud_x = (game.getWidth() - INV_HUD_WIDTH.getValue()) / 2.0f;
		float inv_hud_y = game.getHeight() - INV_HUD_HEIGHT.getValue() + INV_HUD_CHOOSEN_SIZE_OFFSET.getValue();

		nvgImagePattern(nvg, inv_hud_x, inv_hud_y, INV_HUD_WIDTH.getValue(), INV_HUD_HEIGHT.getValue(), 0,
				tex_inventory_tab, 1, paint);
		nvgBeginPath(nvg);
		nvgRoundedRect(nvg, inv_hud_x, inv_hud_y, INV_HUD_WIDTH.getValue(), INV_HUD_HEIGHT.getValue(), 0);
		nvgFillPaint(nvg, paint);
		nvgFill(nvg);

		float inv_chosen_x = inv_hud_x + INV_HUD_CHOOSEN_SIZE_OFFSET.getValue()
				+ inventory.getChosenSlotId() * INV_HUD_POINTER_OFFSET.getValue();
		float inv_chosen_y = inv_hud_y + INV_HUD_CHOOSEN_SIZE_OFFSET.getValue();
		float inv_chosen_width = INV_HUD_HEIGHT.getValue() - INV_HUD_CHOOSEN_SIZE_OFFSET.getValue() * 2;
		float inv_chosen_height = INV_HUD_HEIGHT.getValue() - INV_HUD_CHOOSEN_SIZE_OFFSET.getValue() * 2;

		nvgImagePattern(nvg, inv_chosen_x, inv_chosen_y, inv_chosen_width, inv_chosen_height, 0,
				tex_inventory_tab_chosen, 1, paint);
		nvgBeginPath(nvg);
		nvgRoundedRect(nvg, inv_chosen_x, inv_chosen_y, inv_chosen_width, inv_chosen_height, 0);
		nvgFillPaint(nvg, paint);
		nvgFill(nvg);
		
		for(int i = 0; i < Inventory.SLOT_NUM; i++) {
			InventoryItem item = inventory.getSlots()[i];
			if(item != null) {
				item.renderSlot(inv_hud_x + INV_HUD_FIRST_SLOT_OFFSET_X.getValue() + i * INV_HUD_POINTER_OFFSET.getValue(), inv_hud_y + INV_HUD_SLOT_OFFSET_Y.getValue(), INV_HUD_SLOT_SIZE.getValue(), INV_HUD_SLOT_SIZE.getValue(), nvg);
			}
		}

	}

	@Override
	public void release() {
		this.inventory.getItems().forEach(b -> b.renderRelease(nvg));
		super.release();

	}

	@Override
	public void size() {
		super.size();

		INV_VIEW_ITEM_SIZE.needEvalAgain();
		INV_VIEW_PADDING.needEvalAgain();
		INV_VIEW_MARGIN.needEvalAgain();
		INV_VIEW_LETTER_HINT_SIZE.needEvalAgain();
		INV_VIEW_LETTER_HINT_MARGIN.needEvalAgain();
		INV_VIEW_LETTER_HINT_BACKGROUND_SIZE_OFFSET.needEvalAgain();
		INV_HUD_WIDTH.needEvalAgain();
		INV_HUD_HEIGHT.needEvalAgain();
		INV_HUD_SLOT_OFFSET_Y.needEvalAgain();
		INV_HUD_FIRST_SLOT_OFFSET_X.needEvalAgain();
		INV_HUD_SLOT_SIZE.needEvalAgain();
		INV_HUD_POINTER_OFFSET.needEvalAgain();
		INV_HUD_CHOOSEN_SIZE_OFFSET.needEvalAgain();
		INV_VIEW_ARROW_SIZE.needEvalAgain();

		preparePages();

	}

}
