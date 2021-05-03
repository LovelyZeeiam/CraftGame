package xueli.craftgame.block;

import xueli.craftgame.entity.Player;
import xueli.craftgame.world.Dimension;
import xueli.craftgame.world.Tile;

public interface IBlockListener {

	public static IBlockListener EMPTY = new IBlockListener() {
		@Override
		public void onLookAt(int x, int y, int z, Tile tile, Dimension world, Player player) {
		}

		@Override
		public void onPlaced(int x, int y, int z, Tile tile, Dimension world, Player player) {
		}

		@Override
		public void onLeftClick(int x, int y, int z, Tile tile, Dimension world, Player player) {
		}

		@Override
		public void onRightClick(int x, int y, int z, Tile tile, Dimension world, Player player) {
		}
	};

	public void onLookAt(int x, int y, int z, Tile tile, Dimension world, Player player);

	public void onPlaced(int x, int y, int z, Tile tile, Dimension world, Player player);

	public void onLeftClick(int x, int y, int z, Tile tile, Dimension world, Player player);

	public void onRightClick(int x, int y, int z, Tile tile, Dimension world, Player player);

}
