package xueli.craftgame.block;

import com.flowpowered.nbt.CompoundMap;
import com.flowpowered.nbt.CompoundTag;
import com.flowpowered.nbt.StringTag;
import com.google.gson.Gson;
import xueli.craftgame.interfaces.Saveable;
import xueli.craftgame.world.Chunk;
import xueli.craftgame.world.World;
import xueli.gamengine.physics.AABB;
import xueli.gamengine.resource.TextureAtlas;
import xueli.gamengine.utils.FloatList;

import java.util.ArrayList;
import java.util.Objects;

public class Tile implements Saveable {

	private BlockData data;
	private BlockListener listener;
	private BlockParameters params;

	public Tile(String namespace) {
		this.data = BlockResource.blockDatas.get(namespace);
		if (this.data == null)
			this.data = BlockResource.blockDatas.get("craftgame:" + namespace);
		this.listener = data.getListener();
		this.params = new BlockParameters();

	}

	public Tile(BlockData data) {
		this.data = data;
		this.listener = data.getListener();
		this.params = new BlockParameters();

	}

	public Tile(BlockData data, BlockParameters params) {
		this.data = data;
		this.listener = data.getListener();
		this.params = params;

	}

	public Tile(CompoundTag data) {
		setSaveData(data);

	}

	public BlockData getData() {
		return data;
	}

	public BlockListener getListener() {
		return listener;
	}

	public BlockParameters getParams() {
		return params;
	}

	public int getDrawData(int x, int y, int z, byte face, TextureAtlas blockTextureAtlas, FloatList buffer,
			BlockParameters params, World world, Chunk chunk) {
		return data.getDrawData(buffer, this.data, x, y, z, face, blockTextureAtlas, params, chunk, world);
	}

	public ArrayList<AABB> getAabbs() {
		return data.getAabbs();
	}

	@Override
	public CompoundTag getSaveData() {
		CompoundMap rootTag = new CompoundMap();
		rootTag.put(new StringTag("namespace", data.getNamespace()));
		rootTag.put(new StringTag("param", new Gson().toJson(params)));
		return new CompoundTag("", rootTag);
	}

	@Override
	public void setSaveData(CompoundTag data) {
		CompoundMap rootData = data.getValue();
		String namespace = (String) rootData.get("namespace").getValue();
		String paramJson = (String) rootData.get("param").getValue();

		this.data = BlockResource.blockDatas.get(namespace);
		this.params = new Gson().fromJson(paramJson, BlockParameters.class);

		this.listener = this.data.getListener();

	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Tile tile = (Tile) o;
		return Objects.equals(data, tile.data);
	}

	@Override
	public int hashCode() {
		return Objects.hash(data);
	}

}
