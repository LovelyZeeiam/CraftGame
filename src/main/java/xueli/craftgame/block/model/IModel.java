package xueli.craftgame.block.model;

import java.util.ArrayList;

import com.google.gson.JsonObject;

import xueli.craftgame.block.BlockData;
import xueli.gamengine.physics.AABB;
import xueli.gamengine.resource.TextureAtlas;
import xueli.gamengine.utils.FloatList;

public abstract class IModel {

	public IModel(JsonObject renderArgs) {

	}

	public abstract int getRenderData(FloatList buffer, BlockData data, int x, int y, int z, byte face,
			TextureAtlas blockTextureAtlas);

	public abstract ArrayList<AABB> getAabbs();

}
