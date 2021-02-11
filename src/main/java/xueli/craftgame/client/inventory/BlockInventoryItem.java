package xueli.craftgame.client.inventory;

import xueli.craftgame.block.BlockData;
import xueli.craftgame.block.BlockParameters;
import xueli.craftgame.block.BlockReviewGenerator;
import xueli.craftgame.block.Tile;
import xueli.craftgame.block.data.RightClick;
import xueli.craftgame.block.data.SlabAndStairData;
import xueli.craftgame.entity.Player;
import xueli.craftgame.world.World;

public class BlockInventoryItem extends InventoryItem {

	private final BlockData blockData;

	public BlockInventoryItem(BlockData block) {
		this.blockData = block;

	}

	@Override
	public void onRightClick(World world, Player player) {
		Tile tile = world.getBlock(player.getBlock_select().getX(), player.getBlock_select().getY(),
				player.getBlock_select().getZ());
		if (tile.getListener().onRightClick(player.getBlock_select().getX(), player.getBlock_select().getY(),
				player.getBlock_select().getZ(), world) == RightClick.PLACE_BLOCK_WHEN_RIGHT_CLICK) {
			BlockParameters parameters = new BlockParameters();
			parameters.slabOrStairData = player.getLast_time_ray_end().getY()
					- player.getLast_block_select().getY() > 0.5f ? SlabAndStairData.UP : SlabAndStairData.DOWN;
			parameters.faceTo = player.getPlace_block_face_to();

			world.setBlock(player.getLast_block_select(), new Tile(blockData, parameters, world.getWorldLogic()));
		}

	}

	@Override
	public void onLeftClick(World world, Player player) {
		Tile tile = world.getBlock(player.getBlock_select().getX(), player.getBlock_select().getY(),
				player.getBlock_select().getZ());
		tile.getListener().onLeftClick(player.getBlock_select().getX(), player.getBlock_select().getY(),
				player.getBlock_select().getZ(), world);

	}

	@Override
	public String getName() {
		return blockData.getBlockName();
	}

	@Override
	public String getNamespace() {
		return blockData.getNamespace();
	}

	@Override
	public int getReviewTexture() {
		return BlockReviewGenerator.getTexture(blockData.getNamespace());
	}

}
