package xueli.craftgame.item;

public class ItemStack {

	private ItemType type;
	private int count;

	public ItemStack(ItemType type, int count) {
		this.type = type;
		this.count = count;
	}

	public ItemType getType() {
		return type;
	}

	public ItemStack setType(ItemType type) {
		this.type = type;
		return this;
	}

	public int getCount() {
		return count;
	}

	public ItemStack setCount(int count) {
		this.count = count;
		return this;
	}
}
