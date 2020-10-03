package xueLi.craftgame.block;

import xueLi.craftgame.BlockResource;
import xueLi.craftgame.world.BlockPos;
import xueLi.gamengine.resource.TextureAtlas;
import xueLi.gamengine.utils.FloatList;

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

	public int getDrawData(int x, int y, int z, byte face, TextureAtlas blockTextureAtlas, FloatList buffer) {
		return listener.getDrawData(buffer, this, x, y, z, face, blockTextureAtlas);
	}

}
