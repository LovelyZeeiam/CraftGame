package xueli.craftgame.player;

import xueli.craftgame.CraftGameContext;
import xueli.craftgame.entitytest.item.ItemStack;

public class Inventory {

	public static final int SLOT_COUNT = 36;
	public static final int FRONT_INVENTORY = 9;

	private CraftGameContext ctx;
	private LocalPlayer player;

	private ItemStack[] stacks = new ItemStack[SLOT_COUNT];
	private int frontInventoryChosen = 0;

	public Inventory(LocalPlayer player) {
		this.player = player;
		this.ctx = player.getContext();

	}

	public boolean leftClick() {
		ItemStack current = stacks[frontInventoryChosen];
		if (current == null) {
			return true;
		}

		return current.getType().getListener().onLeftClick(current, ctx.getTicker(), player);
	}

	public void rightClick() {
		ItemStack current = stacks[frontInventoryChosen];
		if (current == null) {
			return;
		}

		current.getType().getListener().onRightClick(current, ctx.getTicker(), player);
	}

	public void drop() {
		ItemStack current = stacks[frontInventoryChosen];
		if (current == null) {
			return;
		}

		int count = current.getCount();

		if (count == 1) {
			stacks[frontInventoryChosen] = null;
		} else {
			count--;
			current.setCount(count);
		}

	}

	public void set(int slot, ItemStack stack) {
		if (slot < 0 || slot >= stacks.length)
			return;

		stacks[slot] = stack;

	}

	public ItemStack getStack(int slot) {
		if (slot < 0 || slot >= stacks.length)
			return null;

		return stacks[slot];
	}

	public int getFrontInventoryChosen() {
		return frontInventoryChosen;
	}

	public Inventory setFrontInventoryChosen(int frontInventoryChosen) {
		this.frontInventoryChosen = frontInventoryChosen;
		return this;
	}

	public LocalPlayer getPlayer() {
		return player;
	}

}
