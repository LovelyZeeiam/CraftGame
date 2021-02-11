package xueli.craftgame.block;

import xueli.craftgame.block.data.RightClick;
import xueli.craftgame.view.BlockMessageView;
import xueli.craftgame.world.World;

public class BlockListener {

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
		Tile tile = world.getBlock(x, y, z);
		String message = tile.getParams().message;
		if (message == null || message.isEmpty())
			return RightClick.PLACE_BLOCK_WHEN_RIGHT_CLICK;
		world.getWorldLogic().toggleSetIngameGui(new BlockMessageView(tile, world.getWorldLogic()));
		return RightClick.DONT_PLACE_BLOCK_WHEN_RIGHT_CLICK;
	}

}
