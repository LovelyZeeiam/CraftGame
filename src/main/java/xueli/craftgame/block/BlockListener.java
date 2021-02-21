package xueli.craftgame.block;

import java.io.Serializable;

import xueli.craftgame.block.data.LeftClick;
import xueli.craftgame.block.data.RightClick;
import xueli.craftgame.world.World;

public class BlockListener implements Serializable {

	public void onCreate(int x, int y, int z, World world) {
	}

	public void onLookAt(int x, int y, int z, World world) {
	}

	public void onDestroy(int x, int y, int z, World world) {
	}

	public LeftClick onLeftClick(int x, int y, int z, World world) {
		return LeftClick.DESTROY_BLOCK_WHEN_LEFT_CLICK;
	}

	public RightClick onRightClick(int x, int y, int z, World world) {
		return RightClick.PLACE_BLOCK_WHEN_RIGHT_CLICK;
	}

}
