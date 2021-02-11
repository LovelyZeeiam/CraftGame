package xueli.craftgame.client.inventory;

import xueli.craftgame.block.Tile;
import xueli.craftgame.block.data.LeftClick;
import xueli.craftgame.entity.Player;
import xueli.craftgame.item.Item;
import xueli.craftgame.world.World;

public class ItemInventoryItem extends InventoryItem {

	private Item item;

	public ItemInventoryItem(Item item) {
		this.item = item;

	}

	@Override
	public void onRightClick(World world, Player player) {
		item.getData().onRightClick(world, player);

	}

	@Override
	public void onLeftClick(World world, Player player) {
		if (item.getData().onLeftClick(world, player) == LeftClick.DESTROY_BLOCK_WHEN_LEFT_CLICK) {
			Tile tile = world.getBlock(player.getBlock_select().getX(), player.getBlock_select().getY(),
					player.getBlock_select().getZ());
			tile.getListener().onLeftClick(player.getBlock_select().getX(), player.getBlock_select().getY(),
					player.getBlock_select().getZ(), world);

		}

	}

	@Override
	public String getName() {
		return item.getData().getItemName();
	}

	@Override
	public String getNamespace() {
		return item.getData().getNamespace();
	}

	@Override
	public int getReviewTexture() {
		return item.getNvgTexture();
	}

}
