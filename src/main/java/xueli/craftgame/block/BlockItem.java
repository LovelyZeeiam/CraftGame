package xueli.craftgame.block;

import org.lwjgl.utils.vector.Vector3i;

import xueli.craftgame.client.LocalTicker;
import xueli.craftgame.event.EventSetBlock;
import xueli.craftgame.item.ItemListener;
import xueli.craftgame.item.ItemRenderable;
import xueli.craftgame.item.ItemStack;
import xueli.craftgame.item.ItemType;
import xueli.craftgame.player.LocalPlayer;
import xueli.game.renderer.FrameBuffer;
import xueli.game.renderer.ScreenQuadRenderer;

public class BlockItem extends ItemType implements ItemListener, ItemRenderable {

	private BlockType block;

	public BlockItem(BlockType type) {
		super(type.getNamespace(), type.getName());
		this.block = type;

		setListener(this);
		setRenderable(this);

	}

	@Override
	public void onRightClick(ItemStack stack, LocalTicker ticker, LocalPlayer entity) {
		Vector3i lastSelectedBlock = entity.getLastSelectedBlock();
		if (lastSelectedBlock != null)
			ticker.submitEvent(
					new EventSetBlock(lastSelectedBlock.x, lastSelectedBlock.y, lastSelectedBlock.z, this.block, null));
	}

	private static ScreenQuadRenderer renderer = new ScreenQuadRenderer();

	@Override
	public void render(ItemStack stack, LocalPlayer player) {
		FrameBuffer blockReview = Blocks.blockReviews.get(block);
		renderer.render(blockReview.getTbo_image());

	}

}
