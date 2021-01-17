package xueli.craftgame.block.model;

import java.util.ArrayList;

import com.google.gson.JsonObject;

import xueli.craftgame.block.BlockData;
import xueli.craftgame.block.BlockFace;
import xueli.gamengine.physics.AABB;
import xueli.gamengine.resource.TextureAtlas;
import xueli.gamengine.utils.FloatList;
import xueli.gamengine.utils.vector.Vector2s;

public class BlockModel extends IModel {

	private static ArrayList<AABB> aabbs = new ArrayList<>();

	static {
		aabbs.add(new AABB(0, 1, 0, 1, 0, 1));

	}

	public BlockModel(JsonObject renderArgs) {
		super(renderArgs);

	}

	@Override
	public int getRenderData(FloatList buffer, BlockData data, int x, int y, int z, byte face,
			TextureAtlas blockTextureAtlas) {
		Vector2s textureVector2s = data.getTextures()[face];

		float u1 = (float) textureVector2s.x / blockTextureAtlas.width;
		float v1 = (float) textureVector2s.y / blockTextureAtlas.height;
		float u2 = (float) (textureVector2s.x + 1) / blockTextureAtlas.width;
		float v2 = (float) (textureVector2s.y + 1) / blockTextureAtlas.height;

		switch (face) {
		case BlockFace.FRONT:
			buffer.put(u1).put(v2);
			buffer.put(0.7f).put(0.7f).put(0.7f);
			buffer.put(x).put(y).put(z);
			buffer.put(u1).put(v1);
			buffer.put(0.7f).put(0.7f).put(0.7f);
			buffer.put(x).put(y + 1).put(z);
			buffer.put(u2).put(v2);
			buffer.put(0.7f).put(0.7f).put(0.7f);
			buffer.put(x + 1).put(y).put(z);
			buffer.put(u1).put(v1);
			buffer.put(0.7f).put(0.7f).put(0.7f);
			buffer.put(x).put(y + 1).put(z);
			buffer.put(u2).put(v1);
			buffer.put(0.7f).put(0.7f).put(0.7f);
			buffer.put(x + 1).put(y + 1).put(z);
			buffer.put(u2).put(v2);
			buffer.put(0.7f).put(0.7f).put(0.7f);
			buffer.put(x + 1).put(y).put(z);
			break;
		case BlockFace.RIGHT:
			buffer.put(u1).put(v2);
			buffer.put(0.7f).put(0.7f).put(0.7f);
			buffer.put(x + 1).put(y).put(z);
			buffer.put(u1).put(v1);
			buffer.put(0.7f).put(0.7f).put(0.7f);
			buffer.put(x + 1).put(y + 1).put(z);
			buffer.put(u2).put(v2);
			buffer.put(0.7f).put(0.7f).put(0.7f);
			buffer.put(x + 1).put(y).put(z + 1);
			buffer.put(u1).put(v1);
			buffer.put(0.7f).put(0.7f).put(0.7f);
			buffer.put(x + 1).put(y + 1).put(z);
			buffer.put(u2).put(v1);
			buffer.put(0.7f).put(0.7f).put(0.7f);
			buffer.put(x + 1).put(y + 1).put(z + 1);
			buffer.put(u2).put(v2);
			buffer.put(0.7f).put(0.7f).put(0.7f);
			buffer.put(x + 1).put(y).put(z + 1);
			break;
		case BlockFace.BACK:
			buffer.put(u1).put(v2);
			buffer.put(0.7f).put(0.7f).put(0.7f);
			buffer.put(x).put(y).put(z + 1);
			buffer.put(u2).put(v2);
			buffer.put(0.7f).put(0.7f).put(0.7f);
			buffer.put(x + 1).put(y).put(z + 1);
			buffer.put(u1).put(v1);
			buffer.put(0.7f).put(0.7f).put(0.7f);
			buffer.put(x).put(y + 1).put(z + 1);
			buffer.put(u1).put(v1);
			buffer.put(0.7f).put(0.7f).put(0.7f);
			buffer.put(x).put(y + 1).put(z + 1);
			buffer.put(u2).put(v2);
			buffer.put(0.7f).put(0.7f).put(0.7f);
			buffer.put(x + 1).put(y).put(z + 1);
			buffer.put(u2).put(v1);
			buffer.put(0.7f).put(0.7f).put(0.7f);
			buffer.put(x + 1).put(y + 1).put(z + 1);
			break;
		case BlockFace.LEFT:
			buffer.put(u1).put(v2);
			buffer.put(0.7f).put(0.7f).put(0.7f);
			buffer.put(x).put(y).put(z);
			buffer.put(u2).put(v2);
			buffer.put(0.7f).put(0.7f).put(0.7f);
			buffer.put(x).put(y).put(z + 1);
			buffer.put(u1).put(v1);
			buffer.put(0.7f).put(0.7f).put(0.7f);
			buffer.put(x).put(y + 1).put(z);
			buffer.put(u1).put(v1);
			buffer.put(0.7f).put(0.7f).put(0.7f);
			buffer.put(x).put(y + 1).put(z);
			buffer.put(u2).put(v2);
			buffer.put(0.7f).put(0.7f).put(0.7f);
			buffer.put(x).put(y).put(z + 1);
			buffer.put(u2).put(v1);
			buffer.put(0.7f).put(0.7f).put(0.7f);
			buffer.put(x).put(y + 1).put(z + 1);
			break;
		case BlockFace.TOP:
			buffer.put(u1).put(v2);
			buffer.put(1.0f).put(1.0f).put(1.0f);
			buffer.put(x).put(y + 1).put(z);
			buffer.put(u2).put(v2);
			buffer.put(1.0f).put(1.0f).put(1.0f);
			buffer.put(x).put(y + 1).put(z + 1);
			buffer.put(u1).put(v1);
			buffer.put(1.0f).put(1.0f).put(1.0f);
			buffer.put(x + 1).put(y + 1).put(z);
			buffer.put(u1).put(v1);
			buffer.put(1.0f).put(1.0f).put(1.0f);
			buffer.put(x + 1).put(y + 1).put(z);
			buffer.put(u2).put(v2);
			buffer.put(1.0f).put(1.0f).put(1.0f);
			buffer.put(x).put(y + 1).put(z + 1);
			buffer.put(u2).put(v1);
			buffer.put(1.0f).put(1.0f).put(1.0f);
			buffer.put(x + 1).put(y + 1).put(z + 1);
			break;
		case BlockFace.BOTTOM:
			buffer.put(u1).put(v2);
			buffer.put(0.5f).put(0.5f).put(0.5f);
			buffer.put(x).put(y).put(z);
			buffer.put(u1).put(v1);
			buffer.put(0.5f).put(0.5f).put(0.5f);
			buffer.put(x + 1).put(y).put(z);
			buffer.put(u2).put(v2);
			buffer.put(0.5f).put(0.5f).put(0.5f);
			buffer.put(x).put(y).put(z + 1);
			buffer.put(u1).put(v1);
			buffer.put(0.5f).put(0.5f).put(0.5f);
			buffer.put(x + 1).put(y).put(z);
			buffer.put(u2).put(v1);
			buffer.put(0.5f).put(0.5f).put(0.5f);
			buffer.put(x + 1).put(y).put(z + 1);
			buffer.put(u2).put(v2);
			buffer.put(0.5f).put(0.5f).put(0.5f);
			buffer.put(x).put(y).put(z + 1);
			break;
		}

		return 6;
	}

	@Override
	public ArrayList<AABB> getAabbs() {
		return aabbs;
	}
}
