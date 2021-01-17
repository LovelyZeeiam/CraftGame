package xueli.craftgame.block;

import java.util.ArrayList;

import xueli.gamengine.physics.AABB;
import xueli.gamengine.resource.TextureAtlas;
import xueli.gamengine.utils.FloatList;

public class Tile {

	public BlockData data;
	private BlockListener listener;

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

	public int getDrawData(int x, int y, int z, byte face, TextureAtlas blockTextureAtlas, FloatList buffer) {
		return data.getDrawData(buffer, this.data, x, y, z, face, blockTextureAtlas);
	}

	public ArrayList<AABB> getAabbs() {
		return data.getAabbs();
	}



}
