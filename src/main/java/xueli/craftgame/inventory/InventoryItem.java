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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getClass().getName() == null) ? 0 : getClass().getName().hashCode());
		result = prime * result + ((getNamespace() == null) ? 0 : getNamespace().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InventoryItem other = (InventoryItem) obj;
		if (getClass().getName() == null) {
			if (other.getClass().getName() != null)
				return false;
		} else if (!getClass().getName().equals(other.getClass().getName()))
			return false;
		if (getNamespace() == null) {
			if (other.getNamespace() != null)
				return false;
		} else if (!getNamespace().equals(other.getNamespace()))
			return false;
		return true;
	}
	
	
}
