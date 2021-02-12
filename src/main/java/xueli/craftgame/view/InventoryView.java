package xueli.craftgame.view;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NanoVG;
import xueli.craftgame.WorldLogic;
import xueli.craftgame.client.inventory.Inventory;
import xueli.craftgame.client.inventory.InventoryItem;
import xueli.craftgame.entity.Player;
import xueli.gamengine.view.GuiColor;

import static org.lwjgl.nanovg.NanoVG.*;

public class InventoryView extends InGameView {

	private static NVGColor backgroundColor = NVGColor.create();

	public static final float width = 610, height = 400;
	public static final float border = 10;
	public static final float sizePerBlock = 60;
	public static final float blockMargin = 3;
	public static final int infoBoxPadding = 5;
	public static final int infoBoxOffset = 15;
	public static final float sizePerSlot = 50;
	public static final float marginPerSlot = 8;

	static {
		NanoVG.nvgRGBf(0.6f, 0.6f, 0.6f, backgroundColor);

	}

	private Player player;
	private NVGPaint paint = NVGPaint.create();

	public InventoryView(WorldLogic logic, Player player) {
		super(logic);
		this.player = player;

	}

	private InventoryItem lastTimeMouseHoveredBlockData = null;
	private InventoryItem chosenItem = null;

	private float scrollOffset = 0.0f;

	// 沉底物品栏的一些全局变量 会在onclick函数里面用到
	private float realSizePerSlot = 0;
	private float slotsWidth = 0;
	private float slotsHeight = 0;
	private float slotsY = 0;
	private float slotsX = 0;

	@Override
	public void draw(long nvg) {
		super.draw(nvg);

		// 整个界面的左上角坐标
		float x = (game.getDisplay().getWidth() - width * game.getDisplay().getScale()) / 2.0f;
		float y = (game.getDisplay().getHeight() - height * game.getDisplay().getScale()) / 2.0f;

		// 界面内的小框框的左上角坐标
		float pointerX = x + border / 2.0f;
		float pointerY = y + border / 2.0f;

		// 绘制时用到的指针
		float startPointerX = pointerX;
		float pointerXMax = pointerX + width * game.getDisplay().getScale() - border;

		// 背景框框的绘制
		drawBox(x, y, width * game.getDisplay().getScale(), height * game.getDisplay().getScale(), GuiColor.BLACK,
				backgroundColor, 2.0f, nvg);
		drawBox(pointerX, pointerY, width * game.getDisplay().getScale() - border,
				height * game.getDisplay().getScale() - border, GuiColor.BLACK, backgroundColor, 2.0f, nvg);

		// 鼠标位置
		float mouseX = game.getDisplay().getMouseX();
		float mouseY = game.getDisplay().getMouseY();

		// 上次鼠标指到的方块清空
		lastTimeMouseHoveredBlockData = null;

		// 裁剪
		nvgSave(nvg);
		nvgScissor(nvg, pointerX, pointerY, width * game.getDisplay().getScale() - border,
				height * game.getDisplay().getScale() - border);

		// 物品栏元素
		for (InventoryItem inventoryItem : Inventory.inventoryItems) {
			// 是否有鼠标指着这个物品
			boolean mousePointed = mouseX - pointerX < sizePerBlock & mouseY - pointerY - scrollOffset < sizePerBlock
					& mouseX > pointerX & mouseY > pointerY + scrollOffset;
			drawBox(pointerX, pointerY + scrollOffset, sizePerBlock, sizePerBlock,
					mousePointed ? GuiColor.YELLOW : GuiColor.BLACK, backgroundColor, blockMargin / 4, nvg);

			if (mousePointed) {
				lastTimeMouseHoveredBlockData = inventoryItem;

			}

			// 绘制每个小方格代表的物品
			int texture = inventoryItem.getReviewTexture();
			nvgImagePattern(nvg, pointerX, pointerY + scrollOffset, sizePerBlock, sizePerBlock, 0, texture, 1, paint);
			nvgBeginPath(nvg);
			nvgRoundedRect(nvg, pointerX, pointerY + scrollOffset, sizePerBlock, sizePerBlock, 0);
			nvgFillPaint(nvg, paint);
			nvgFill(nvg);

			pointerX += sizePerBlock + blockMargin;
			if (pointerX + sizePerBlock > pointerXMax) {
				pointerY += sizePerBlock + blockMargin;
				pointerX = startPointerX;

			}

		}

		nvgRestore(nvg);

		if (lastTimeMouseHoveredBlockData != null) {
			float boxHeight = 20;

			nvgFontSize(nvg, boxHeight * display.getScale());
			nvgFontFace(nvg, "game");
			nvgTextAlign(nvg, NVG_ALIGN_TOP | NVG_ALIGN_LEFT);
			nvgFillColor(nvg, GuiColor.BLACK);
			float textWidth = nvgText(nvg, mouseX + infoBoxPadding + infoBoxOffset,
					mouseY + infoBoxPadding + infoBoxOffset, lastTimeMouseHoveredBlockData.getName())
					- (mouseX + infoBoxPadding + infoBoxOffset);

			super.drawBox(mouseX + infoBoxOffset, mouseY + infoBoxOffset, textWidth + infoBoxPadding * 2,
					boxHeight * display.getScale() + infoBoxPadding * 2, GuiColor.BLACK, backgroundColor, 1, nvg);

			nvgFontSize(nvg, boxHeight * display.getScale());
			nvgFontFace(nvg, "game");
			nvgTextAlign(nvg, NVG_ALIGN_TOP | NVG_ALIGN_LEFT);
			nvgFillColor(nvg, GuiColor.BLACK);
			nvgText(nvg, mouseX + infoBoxPadding + infoBoxOffset, mouseY + infoBoxPadding + infoBoxOffset,
					lastTimeMouseHoveredBlockData.getName());

		}

		// 沉底的物品栏
		realSizePerSlot = sizePerSlot * display.getScale();
		slotsWidth = Inventory.DISPLAY_SLOT_COUNT * realSizePerSlot;
		slotsHeight = realSizePerSlot;
		slotsY = display.getHeight() - realSizePerSlot - blockMargin;
		slotsX = (display.getWidth() - slotsWidth) / 2.0f;
		float realMarginPerSlot = marginPerSlot * display.getScale();

		super.drawImage(slotsX, slotsY, slotsWidth, slotsHeight, logic.getNvgTextures().get("ingame.gui.inventory"),
				nvg);

		for (int i = 0; i < Inventory.DISPLAY_SLOT_COUNT; i++) {
			// 绘制物品栏里面的东西
			float pX = slotsX + (realSizePerSlot - 0.5f * display.getScale()) * i + realMarginPerSlot
					+ 2.0f * display.getScale();
			float pY = slotsY + realMarginPerSlot;
			float pWidth = realSizePerSlot - realMarginPerSlot * 2;
			float pHeight = realSizePerSlot - realMarginPerSlot * 2;

			InventoryItem inventoryItem = player.getInventory().getSlot(i);
			if (inventoryItem != null)
				super.drawImage(pX, pY, pWidth, pHeight, inventoryItem.getReviewTexture(), nvg);

		}

		if (chosenItem != null) {
			super.drawImage(mouseX, mouseY, realSizePerSlot, realSizePerSlot, chosenItem.getReviewTexture(), nvg);

		}

	}

	@Override
	public void onClick(float x, float y, int button) {
		if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
			if (chosenItem == null) {
				if (y > slotsY && y < slotsY + slotsHeight && x > slotsX && x < slotsX + slotsWidth) {
					// 在物品槽内部
					int clickSlot = (int) ((x - slotsX) / realSizePerSlot);
					chosenItem = player.getInventory().getSlot(clickSlot);
					player.getInventory().setSlot(clickSlot, null);

				} else {
					// 在物品槽外部
					if (lastTimeMouseHoveredBlockData != null) {
						chosenItem = lastTimeMouseHoveredBlockData;

					}

				}

			} else {
				if (y > slotsY && y < slotsY + slotsHeight && x > slotsX && x < slotsX + slotsWidth) {
					// 在物品槽内部
					int clickSlot = (int) ((x - slotsX) / realSizePerSlot);
					player.getInventory().setSlot(clickSlot, chosenItem);

					chosenItem = null;

				} else {
					// 在物品槽外部
					chosenItem = null;

				}

			}

		}

	}

	@Override
	public void onScroll(float x, float y, float scroll) {
		scrollOffset += scroll * 20;

	}

}
