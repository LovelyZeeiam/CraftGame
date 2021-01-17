package xueli.craftgame.block.model;

import java.util.ArrayList;

import com.google.gson.JsonObject;

import xueli.craftgame.block.BlockData;
import xueli.craftgame.world.Chunk;
import xueli.craftgame.world.World;
import xueli.gamengine.physics.AABB;
import xueli.gamengine.resource.TextureAtlas;
import xueli.gamengine.utils.FloatList;
import xueli.gamengine.view.GuiColor;

public class ModelCube extends IModel {

	private static ArrayList<AABB> aabbs = new ArrayList<>();

	static {
		aabbs.add(new AABB(0, 1, 0, 1, 0, 1));

	}

	public ModelCube(JsonObject renderArgs) {
		super(renderArgs);

	}

	@Override
	public int getRenderCubeData(FloatList buffer, BlockData data, int x, int y, int z, byte face,
			TextureAtlas blockTextureAtlas, Chunk chunk, World world) {
		return getRenderCubeData(buffer, data, x, y, z, face, blockTextureAtlas, chunk, world, GuiColor.WHITE);
	}

	@Override
	public ArrayList<AABB> getAabbs() {
		return aabbs;
	}
}
