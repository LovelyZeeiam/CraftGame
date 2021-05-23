package xueli.craftgame.inventory;

import xueli.craftgame.entity.Player;

public abstract class InventoryItem {

	public abstract void renderInit(long nvg);

	public abstract void renderSlot(float x, float y, float width, float height, long nvg);

	public abstract void renderRelease(long nvg);

	public abstract String getName();

	public abstract String getNamespace();

	public abstract void onLeftClick(Player player);

	public abstract void onRightClick(Player player);

	public abstract void onHovered(Player player);

}
