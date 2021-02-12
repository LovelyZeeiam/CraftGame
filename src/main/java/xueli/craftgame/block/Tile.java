package xueli.craftgame.block;

import com.flowpowered.nbt.CompoundMap;
import com.flowpowered.nbt.CompoundTag;
import com.flowpowered.nbt.StringTag;
import com.google.gson.Gson;
import xueli.craftgame.WorldLogic;
import xueli.craftgame.block.rendercontrol.model.IModel;
import xueli.craftgame.interfaces.Saveable;
import xueli.craftgame.world.Chunk;
import xueli.craftgame.world.World;
import xueli.gamengine.physics.AABB;
import xueli.gamengine.resource.TextureAtlas;
import xueli.gamengine.utils.Logger;
import xueli.gamengine.utils.store.FloatList;

import java.util.ArrayList;
import java.util.Objects;

public class Tile implements Saveable {

	private WorldLogic logic;

	private BlockData data;
	private BlockListener listener;
	private BlockParameters params;

	public Tile(String namespace, WorldLogic logic) {
		this.logic = logic;

		this.data = BlockResource.blockDatas.get(namespace);
		if (this.data == null)
			this.data = BlockResource.blockDatas.get("craftgame:" + namespace);
		this.listener = data.getListener();
		this.params = new BlockParameters();

	}

	public Tile(BlockData data, WorldLogic logic) {
		this.logic = logic;

		this.data = data;
		this.listener = data.getListener();
		this.params = new BlockParameters();

	}

	public Tile(BlockData data, BlockParameters params, WorldLogic logic) {
		this.logic = logic;

		this.data = data;
		this.listener = data.getListener();
		this.params = params;

	}

	public Tile(CompoundTag data, WorldLogic logic) {
		this.logic = logic;
		setSaveData(data, logic);

	}

	public BlockData getData() {
		return data;
	}

	public IModel getModel() {
		return data.getModel();
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

	public ArrayList<AABB> getAabbs(BlockParameters params, World world, int x, int y, int z) {
		return data.getAabbs(params, world, x, y, z);
	}

	@Override
	public CompoundTag getSaveData(WorldLogic logic) {
		CompoundMap rootTag = new CompoundMap();
		rootTag.put(new StringTag("namespace", data.getNamespace()));
		rootTag.put(new StringTag("param", new Gson().toJson(params)));
		return new CompoundTag("", rootTag);
	}

	@Override
	public void setSaveData(CompoundTag data, WorldLogic logic) {
		CompoundMap rootData = data.getValue();
		String namespace = (String) rootData.get("namespace").getValue();
		String paramJson = (String) rootData.get("param").getValue();

		this.data = BlockResource.blockDatas.get(namespace);
		if (this.data == null) {
			Logger.warn("Oops! We've found an unknown block: " + namespace);
			this.data = BlockResource.blockDatas.get("unknown_block");
			this.params = new BlockParameters();
			this.params.message = logic.getCg().getLangManager().getStringFromLangMap("#tile.unknown.message");
			this.listener = this.data.getListener();
			return;
		}

		this.params = new Gson().fromJson(paramJson, BlockParameters.class);
		this.listener = this.data.getListener();

	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Tile tile = (Tile) o;
		return Objects.equals(data, tile.data) && Objects.equals(params, tile.params);
	}

	@Override
	public int hashCode() {
		return Objects.hash(data, params);
	}

}
