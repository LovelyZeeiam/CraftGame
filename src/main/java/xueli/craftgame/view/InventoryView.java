package xueli.craftgame.view;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NanoVG;
import xueli.craftgame.block.BlockData;
import xueli.craftgame.block.BlockResource;
import xueli.craftgame.block.BlockReviewGenerator;
import xueli.craftgame.entity.Player;
import xueli.craftgame.world.WorldLogic;
import xueli.gamengine.view.GuiColor;

import java.util.Map;

import static org.lwjgl.nanovg.NanoVG.*;

public class InventoryView extends InGameView {

	private static NVGColor backgroundColor = NVGColor.create();

	private static final float width = 610, height = 400;
	private static final float border = 10;
	private static final float sizePerBlock = 60;
	private static final float blockMargin = 8;
	private static final int infoBoxOffset = 10;

	static {
		NanoVG.nvgRGBf(0.6f, 0.6f, 0.6f, backgroundColor);

	}

	private Player player;
	private NVGPaint paint = NVGPaint.create();

	public InventoryView(WorldLogic logic, Player player) {
		super(logic);
		this.player = player;

	}

	private BlockData lastTimeMouseHoveredBlockData = null;

	private float scrollOffset = 0.0f;

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
		for (Map.Entry<String, BlockData> entry : BlockResource.blockDatas.entrySet()) {
			String namespace = entry.getKey();

			// 是否有鼠标指着这个方块
			boolean mousePointed = mouseX - pointerX < sizePerBlock & mouseY - pointerY - scrollOffset < sizePerBlock
					& mouseX > pointerX & mouseY > pointerY + scrollOffset;
			drawBox(pointerX, pointerY + scrollOffset, sizePerBlock, sizePerBlock,
					mousePointed ? GuiColor.YELLOW : GuiColor.BLACK, backgroundColor, blockMargin / 4, nvg);

			if (mousePointed) {
				lastTimeMouseHoveredBlockData = entry.getValue();

			}

			// 绘制每个小方格代表的方块
			int texture = BlockReviewGenerator.getTexture(namespace);
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
			float boxY = y + height * game.getDisplay().getScale() + infoBoxOffset;
			float boxWidth = width * game.getDisplay().getScale();
			float boxHeight = 30;
			super.drawBox(x, boxY, boxWidth, boxHeight, GuiColor.YELLOW, backgroundColor, 2.0f, nvg);

			nvgFontSize(nvg, boxHeight * 0.8f);
			nvgFontFace(nvg, "game");
			nvgTextAlign(nvg, NVG_ALIGN_CENTER | NVG_ALIGN_MIDDLE);
			nvgFillColor(nvg, GuiColor.BLACK);
			nvgText(nvg, x + boxWidth / 2, boxY + boxHeight / 2, lastTimeMouseHoveredBlockData.getBlockName());

		}

	}

	@Override
	public void onClick(float x, float y, int button) {
		if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT & lastTimeMouseHoveredBlockData != null) {
			player.handBlockData = lastTimeMouseHoveredBlockData;
			logic.toggleGuiExit();

		}

	}

	@Override
	public void onScroll(float x, float y, float scroll) {
		scrollOffset += scroll * 20;

	}

}
