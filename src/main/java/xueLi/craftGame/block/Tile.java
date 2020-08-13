package xueLi.craftGame.block;

import xueLi.craftGame.BlockResource;
import xueLi.craftGame.world.BlockPos;

public class Tile {

	public BlockData data;
	private BlockListener listener;

	public BlockPos pos;

	public Tile(String namespace) {
		this.data = BlockResource.blockDatas.get(namespace);
		if (this.data == null)
			this.data = BlockResource.blockDatas.get("craftgame:" + namespace);
		this.listener = data.getListener();

	}

	public Tile(BlockData data) {
		this.data = data;
		this.listener = data.getListener();

	}

	public BlockListener getListener() {
		return listener;
	}

	public int getX() {
		return pos.getX();
	}

	public int getY() {
		return pos.getY();
	}

	public int getZ() {
		return pos.getZ();
	}

}
