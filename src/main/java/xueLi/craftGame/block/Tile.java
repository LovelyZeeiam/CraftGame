package xueLi.craftGame.block;

import xueLi.craftGame.BlockResource;

public class Tile {

	public BlockData data;
	private BlockListener listener;

	private int x, y, z;

	public Tile(String namespace) {
		this.data = BlockResource.blockDatas.get(namespace);
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
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

}
