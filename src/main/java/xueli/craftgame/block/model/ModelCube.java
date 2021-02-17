package xueli.craftgame.block.model;

import xueli.craftgame.block.BlockData;
import xueli.craftgame.world.World;
import xueli.gamengine.resource.TextureAtlas;
import xueli.gamengine.utils.store.FloatList;
import xueli.gamengine.view.GuiColor;

public class ModelCube extends IModel {

	public ModelCube() {
		super();

	}

	@Override
	public int getRenderData(FloatList buffer, int x, int y, int z, byte face, long details, BlockData data,
			TextureAtlas blockTextureAtlas, World world) {
		return super.getRenderCubeData(buffer, data, x, y, z, face, blockTextureAtlas, world, GuiColor.WHITE);
	}

}
