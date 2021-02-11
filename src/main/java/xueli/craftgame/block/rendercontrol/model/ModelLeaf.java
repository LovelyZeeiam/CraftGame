package xueli.craftgame.block.rendercontrol.model;

import com.google.gson.JsonObject;

import xueli.craftgame.block.BlockData;
import xueli.craftgame.block.BlockParameters;
import xueli.craftgame.world.Chunk;
import xueli.craftgame.world.World;
import xueli.gamengine.resource.TextureAtlas;
import xueli.gamengine.utils.store.FloatList;
import xueli.gamengine.view.GuiColor;

public class ModelLeaf extends IModel {

	public ModelLeaf(JsonObject renderArgs) {
		super(renderArgs);

	}

	@Override
	public int getRenderCubeData(FloatList buffer, BlockData data, int x, int y, int z, byte face,
			TextureAtlas blockTextureAtlas, BlockParameters params, Chunk chunk, World world) {
		return getRenderCubeData(buffer, data, x, y, z, face, blockTextureAtlas, chunk, world,
				chunk == null ? GuiColor.GREEN : chunk.getBiome().getLeaves_color_nvg());
	}

	@Override
	public boolean isAlpha(World world) {
		return true;
	}

}
