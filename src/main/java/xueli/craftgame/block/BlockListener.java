package xueli.craftgame.block;

import xueli.craftgame.world.World;

public class BlockListener {

	public static enum RightClick {
		PLACE_BLOCK_WHEN_RIGHT_CLICK, DONT_PLACE_BLOCK_WHEN_RIGHT_CLICK;
	}

	public void onCreate(int x, int y, int z, World world) {
	}

	public void onLookAt(int x, int y, int z, World world) {
	}

	public void onDestroy(int x, int y, int z, World world) {
	}

	public void onLeftClick(int x, int y, int z, World world) {
		world.setBlock(x, y, z, (Tile) null);

	}

	public RightClick onRightClick(int x, int y, int z, World world) {
		return RightClick.PLACE_BLOCK_WHEN_RIGHT_CLICK;
	}

}
