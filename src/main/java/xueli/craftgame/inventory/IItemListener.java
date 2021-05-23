package xueli.craftgame.inventory;

import xueli.craftgame.entity.Player;

public interface IItemListener {

	public void onHandOn(Player player);

	public InventoryOperation onLeftClickUse(Player player);

	public InventoryOperation onRightClickUse(Player player);

}
