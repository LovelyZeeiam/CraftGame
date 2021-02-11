package xueli.craftgame.client.inventory;

import xueli.craftgame.entity.Player;
import xueli.craftgame.world.World;

public abstract class InventoryItem {

	public InventoryItem() {
		
	}
	
	public abstract void onRightClick(World world, Player player);
	
	public abstract void onLeftClick(World world, Player player);
	
	public abstract String getName();
	
	public abstract String getNamespace();
	
	public abstract int getReviewTexture();

}
