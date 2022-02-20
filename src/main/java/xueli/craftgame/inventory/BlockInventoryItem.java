package xueli.craftgame.inventory;

import com.flowpowered.nbt.ByteTag;
import com.flowpowered.nbt.CompoundMap;
import com.flowpowered.nbt.CompoundTag;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NanoVGGL3;
import xueli.craftgame.block.BlockBase;
import xueli.craftgame.block.BlockIconGenerator;
import xueli.craftgame.block.BlockTags;
import xueli.craftgame.entity.Player;
import xueli.craftgame.entity.PlayerPicker;
import xueli.craftgame.state.StateWorld;
import xueli.game.renderer.FrameBuffer;

import static org.lwjgl.nanovg.NanoVG.*;

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
			CompoundMap tag = new CompoundMap();
			if (base.getTags().contains(BlockTags.HAS_DIFFERENT_DIRECTION))
				tag.put(new ByteTag(BlockTags.TAG_NAME_FACE_TO, picker.getFaceTo()));
			if (base.getTags().contains(BlockTags.HAS_PART_UP_AND_PART_DOWN))
				tag.put(new ByteTag(BlockTags.TAG_NAME_PART, picker.getBlockPart()));

			player.getDimension().setBlock(picker.getLastSelectedBlock().getX(), picker.getLastSelectedBlock().getY(),
					picker.getLastSelectedBlock().getZ(), base, tag);

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
