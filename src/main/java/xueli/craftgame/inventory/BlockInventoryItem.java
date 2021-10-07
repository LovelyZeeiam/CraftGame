package xueli.craftgame.inventory;

import static org.lwjgl.nanovg.NanoVG.NVG_IMAGE_FLIPY;
import static org.lwjgl.nanovg.NanoVG.nvgBeginPath;
import static org.lwjgl.nanovg.NanoVG.nvgDeleteImage;
import static org.lwjgl.nanovg.NanoVG.nvgFill;
import static org.lwjgl.nanovg.NanoVG.nvgFillPaint;
import static org.lwjgl.nanovg.NanoVG.nvgImagePattern;
import static org.lwjgl.nanovg.NanoVG.nvgRoundedRect;

import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NanoVGGL3;

import com.flowpowered.nbt.ByteTag;

import xueli.craftgame.block.BlockBase;
import xueli.craftgame.block.BlockIconGenerator;
import xueli.craftgame.block.BlockTags;
import xueli.craftgame.entity.Player;
import xueli.craftgame.entity.PlayerPicker;
import xueli.craftgame.state.StateWorld;
import xueli.craftgame.world.Tile;
import xueli.game.renderer.FrameBuffer;

public class BlockInventoryItem extends InventoryItem {

	private BlockBase base;

	private FrameBuffer displayIcon;
	private int nvgTexture;
	private static NVGPaint paint = NVGPaint.create();

	public BlockInventoryItem(BlockBase base) {
		this.base = base;

	}

	@Override
	public void renderInit(long nvg) {
		this.displayIcon = BlockIconGenerator.generate(base, StateWorld.getInstance().getBlocksTextureAtlas());
		this.nvgTexture = NanoVGGL3.nvglCreateImageFromHandle(nvg, displayIcon.getTbo_image(), displayIcon.getWidth(),
				displayIcon.getHeight(), NVG_IMAGE_FLIPY);

	}

	@Override
	public void renderSlot(float x, float y, float width, float height, long nvg) {
		float size = Math.min(width, height);
		float realX = x + (width - size) / 2.0f;
		float realY = y + (height - size) / 2.0f;

		nvgImagePattern(nvg, realX, realY, size, size, 0, nvgTexture, 1, paint);
		nvgBeginPath(nvg);
		nvgRoundedRect(nvg, realX, realY, size, size, 0);
		nvgFillPaint(nvg, paint);
		nvgFill(nvg);

	}

	@Override
	public void renderRelease(long nvg) {
		this.displayIcon.delete();
		nvgDeleteImage(nvg, nvgTexture);

	}

	@Override
	public String getName() {
		return base.getNameInternational();
	}

	@Override
	public String getNamespace() {
		return base.getNamespace();
	}

	public BlockBase get() {
		return base;
	}

	@Override
	public void onLeftClick(Player player) {
		PlayerPicker picker = player.getPicker();
		if (picker.getSelectedBlock() != null
				&& base.getListener().onLeftClickUse(player) == InventoryOperation.OPERATE) {
			player.getDimension().setBlock(picker.getSelectedBlock().getX(), picker.getSelectedBlock().getY(),
					picker.getSelectedBlock().getZ(), null);

		}

	}

	@Override
	public void onRightClick(Player player) {
		PlayerPicker picker = player.getPicker();
		if (picker.getLastSelectedBlock() != null) {
			Tile tile = new Tile(base);
			if (base.getTags().contains(BlockTags.HAS_DIFFERENT_DIRECTION))
				tile.getTags().put(new ByteTag(BlockTags.TAG_NAME_FACE_TO, picker.getFaceTo()));
			if (base.getTags().contains(BlockTags.HAS_PART_UP_AND_PART_DOWN))
				tile.getTags().put(new ByteTag(BlockTags.TAG_NAME_PART, picker.getBlockPart()));

			player.getDimension().setBlock(picker.getLastSelectedBlock().getX(), picker.getLastSelectedBlock().getY(),
					picker.getLastSelectedBlock().getZ(), tile);
		}

	}

	@Override
	public void onHovered(Player player) {
		base.getListener().onHandOn(player);

	}

	public int getNvgTexture() {
		return nvgTexture;
	}

	@Override
	public String toString() {
		return "[BlockInventoryItem] " + getNamespace();
	}

}