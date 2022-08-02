package xueli.craftgame.entitytest.item;

import xueli.craftgame.client.LocalTicker;
import xueli.craftgame.player.LocalPlayer;

// TODO: ADD KEY BIND SUPPORT
public interface ItemListener {

	public default void onEquipped(ItemStack stack, LocalTicker ticker, LocalPlayer entity) {
	}

	/**
	 * @return Whether to break the block
	 */
	public default boolean onLeftClick(ItemStack stack, LocalTicker ticker, LocalPlayer entity) {
		return true;
	}

	public default void onRightClick(ItemStack stack, LocalTicker ticker, LocalPlayer entity) {
	}

	public default void onDrop(ItemStack stack, LocalTicker ticker, LocalPlayer entity) {
	}

	public static final ItemListener EMPTY = new ItemListener() {
	};

}
