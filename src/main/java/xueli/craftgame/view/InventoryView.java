package xueli.craftgame.view;

import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_CENTER;
import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_MIDDLE;
import static org.lwjgl.nanovg.NanoVG.nvgBeginPath;
import static org.lwjgl.nanovg.NanoVG.nvgFill;
import static org.lwjgl.nanovg.NanoVG.nvgFillColor;
import static org.lwjgl.nanovg.NanoVG.nvgFillPaint;
import static org.lwjgl.nanovg.NanoVG.nvgFontFace;
import static org.lwjgl.nanovg.NanoVG.nvgFontSize;
import static org.lwjgl.nanovg.NanoVG.nvgImagePattern;
import static org.lwjgl.nanovg.NanoVG.nvgRoundedRect;
import static org.lwjgl.nanovg.NanoVG.nvgText;
import static org.lwjgl.nanovg.NanoVG.nvgTextAlign;

import java.util.Map;

import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NanoVG;

import xueli.craftgame.CraftGame;
import xueli.craftgame.WorldLogic;
import xueli.craftgame.block.BlockData;
import xueli.craftgame.block.BlockResource;
import xueli.craftgame.block.BlockReviewGenerator;
import xueli.craftgame.entity.Player;
import xueli.gamengine.utils.vector.Vector2i;
import xueli.gamengine.view.GuiColor;
import xueli.utils.Table;

public class InventoryView extends InGameBackgroundView {

	private static NVGColor backgroundColor = NVGColor.create();

	private static final float width = 600, height = 400;
	private static final float border = 10;
	private static final float sizePerBlock = 64;
	private static final float blockMargin = 10;
	private static final int infoBoxOffset = 10;

	static {
		NanoVG.nvgRGBf(0.6f, 0.6f, 0.6f, backgroundColor);

	}

	private Player player;
	private NVGPaint paint = NVGPaint.create();

	public InventoryView(WorldLogic logic, CraftGame game, Player player) {
		super(logic, game);
		this.player = player;

	}

	private Table<BlockData> drawTable = new Table<BlockData>();

	@Override
	public void draw(long nvg) {
		super.draw(nvg);

		float x = (game.getDisplay().getWidth() - width * game.getDisplay().getScale()) / 2.0f;
		float y = (game.getDisplay().getHeight() - height * game.getDisplay().getScale()) / 2.0f;

		float pointerX = x + border / 2.0f;
		float pointerY = y + border / 2.0f;

		float startPointerX = pointerX;

		float pointerXMax = pointerX + width * game.getDisplay().getScale() - border;

		drawBox(x, y, width * game.getDisplay().getScale(), height * game.getDisplay().getScale(), GuiColor.BLACK,
				backgroundColor, 2.0f, nvg);
		drawBox(pointerX, pointerY, width * game.getDisplay().getScale() - border,
				height * game.getDisplay().getScale() - border, GuiColor.BLACK, backgroundColor, 2.0f, nvg);

		int xoffset = 0, yoffset = 0;

		float mouseX = game.getDisplay().getMouseX();
		float mouseY = game.getDisplay().getMouseY();
		Vector2i indexVector2i = getIndexFromMouse(mouseX, mouseY);

		for (Map.Entry<String, BlockData> entry : BlockResource.blockDatas.entrySet()) {
			String namespace = entry.getKey();

			boolean mousePointed = xoffset == indexVector2i.x & yoffset == indexVector2i.y;
			drawBox(pointerX, pointerY, sizePerBlock, sizePerBlock, mousePointed ? GuiColor.YELLOW : GuiColor.BLACK,
					backgroundColor, mousePointed ? 5.0f : 2.0f, nvg);

			int texture = BlockReviewGenerator.getTexture(namespace);
			nvgImagePattern(nvg, pointerX, pointerY, sizePerBlock, sizePerBlock, 0, texture, 1, paint);
			nvgBeginPath(nvg);
			nvgRoundedRect(nvg, pointerX, pointerY, sizePerBlock, sizePerBlock, 0);
			nvgFillPaint(nvg, paint);
			nvgFill(nvg);

			drawTable.put(xoffset, yoffset, entry.getValue());

			pointerX += sizePerBlock + blockMargin;
			xoffset++;

			if (pointerX + sizePerBlock > pointerXMax) {
				pointerY += sizePerBlock + blockMargin;
				pointerX = startPointerX;
				yoffset++;

			}

		}

		BlockData data = drawTable.get(indexVector2i.x, indexVector2i.y);
		if (data != null) {
			float boxY = y + height * game.getDisplay().getScale() + infoBoxOffset;
			float boxWidth = width * game.getDisplay().getScale();
			float boxHeight = 30;
			super.drawBox(x, boxY, boxWidth, boxHeight, GuiColor.YELLOW, backgroundColor, 2.0f, nvg);

			nvgFontSize(nvg, boxHeight * 0.8f);
			nvgFontFace(nvg, "game");
			nvgTextAlign(nvg, NVG_ALIGN_CENTER | NVG_ALIGN_MIDDLE);
			nvgFillColor(nvg, GuiColor.BLACK);
			nvgText(nvg, x + boxWidth / 2, boxY + boxHeight / 2, data.getBlockName());

		}

	}

	private Vector2i getIndexFromMouse(float x, float y) {
		float realX = x
				- ((game.getDisplay().getWidth() - width * game.getDisplay().getScale()) / 2.0f + border / 2.0f);
		float realY = y
				- ((game.getDisplay().getHeight() - height * game.getDisplay().getScale()) / 2.0f + border / 2.0f);
		return new Vector2i((int) (realX / sizePerBlock), (int) (realY / sizePerBlock));
	}

	@Override
	public void onLeftClick(float x, float y) {
		Vector2i indexVector2s = getIndexFromMouse(x, y);
		BlockData data = drawTable.get(indexVector2s.x, indexVector2s.y);
		if (data != null) {
			player.handBlockData = data;
			logic.toggleGuiExit();

		}

	}

}
