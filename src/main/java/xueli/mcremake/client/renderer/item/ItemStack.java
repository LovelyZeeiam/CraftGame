package xueli.mcremake.client.renderer.item;

import com.flowpowered.nbt.CompoundMap;

import xueli.mcremake.core.item.ItemType;

public class ItemStack {
	
	private final ItemType type;
	private int count;
	private final CompoundMap tag = new CompoundMap();
	
	public ItemStack(ItemType type, int count) {
		this.type = type;
		this.count = count;
	}
	
	public ItemType getType() {
		return type;
	}
	
	public CompoundMap getTag() {
		return tag;
	}
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "ItemStack [type=" + type + ", count=" + count + ", tag=" + tag + "]";
	}
	
}
