package xueli.craftgame.block;

import xueli.craftgame.client.LocalTicker;
import xueli.craftgame.entitytest.Entity;

// Should call local ticker and submit events
public interface BlockListener {

	public default void onLookAt(int x, int y, int z, LocalTicker ticker, Entity entity) {
	}

	public default void onPlaced(int x, int y, int z, LocalTicker ticker, Entity entity) {
	}

	public default void onDestroyed(int x, int y, int z, LocalTicker ticker, Entity entity) {
	}

	public default void onLeftClick(int x, int y, int z, LocalTicker ticker, Entity entity) {
	}

	public default void onRightClick(int x, int y, int z, LocalTicker ticker, Entity entity) {
	}

	public static final BlockListener EMPTY = new BlockListener() {
	};

	public static enum Type {
		LOOK_AT, PLACED, LEFT_CLICK, RIGHT_CLICK
	}

}
