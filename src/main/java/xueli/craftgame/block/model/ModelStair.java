package xueli.craftgame.block.model;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector3i;

import com.google.gson.JsonObject;

import xueli.craftgame.block.BlockData;
import xueli.craftgame.block.BlockParameters;
import xueli.craftgame.block.Tile;
import xueli.craftgame.block.data.BlockFace;
import xueli.craftgame.block.data.SlabAndStairData;
import xueli.craftgame.world.Chunk;
import xueli.craftgame.world.World;
import xueli.gamengine.physics.AABB;
import xueli.gamengine.resource.TextureAtlas;
import xueli.gamengine.utils.FloatList;
import xueli.gamengine.utils.vector.Vector2s;

public class ModelStair extends IModel {

	public ModelStair(JsonObject renderArgs) {
		super(renderArgs);
	}

	@Override
	public int getRenderCubeData(FloatList buffer, BlockData data, int x, int y, int z, byte face,
			TextureAtlas blockTextureAtlas, BlockParameters params, Chunk chunk, World world) {
		Vector2s textureVector2s = data.getTextures()[face];

		float u1 = (float) (textureVector2s.x) / blockTextureAtlas.width;
		float v1 = (float) (textureVector2s.y) / blockTextureAtlas.height;
		float u2 = (float) (textureVector2s.x + 0.5f) / blockTextureAtlas.width;
		float v2 = (float) (textureVector2s.y + 0.5f) / blockTextureAtlas.height;
		float u3 = (float) (textureVector2s.x + 1) / blockTextureAtlas.width;
		float v3 = (float) (textureVector2s.y + 1) / blockTextureAtlas.height;

		if (params == null) {
			switch (face) {
			case BlockFace.FRONT:
				buffer.put(u1).put(v2);
				buffer.put(0.7f).put(0.7f).put(0.7f);
				buffer.put(x).put(y).put(z);
				buffer.put(u1).put(v1);
				buffer.put(0.7f).put(0.7f).put(0.7f);
				buffer.put(x).put(y + 0.5f).put(z);
				buffer.put(u3).put(v2);
				buffer.put(0.7f).put(0.7f).put(0.7f);
				buffer.put(x + 1).put(y).put(z);
				buffer.put(u1).put(v1);
				buffer.put(0.7f).put(0.7f).put(0.7f);
				buffer.put(x).put(y + 0.5f).put(z);
				buffer.put(u3).put(v1);
				buffer.put(0.7f).put(0.7f).put(0.7f);
				buffer.put(x + 1).put(y + 0.5f).put(z);
				buffer.put(u3).put(v2);
				buffer.put(0.7f).put(0.7f).put(0.7f);
				buffer.put(x + 1).put(y).put(z);

				buffer.put(u1).put(v2);
				buffer.put(0.7f).put(0.7f).put(0.7f);
				buffer.put(x).put(y + 0.5f).put(z + 0.5f);
				buffer.put(u1).put(v1);
				buffer.put(0.7f).put(0.7f).put(0.7f);
				buffer.put(x).put(y + 1).put(z + 0.5f);
				buffer.put(u3).put(v2);
				buffer.put(0.7f).put(0.7f).put(0.7f);
				buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);
				buffer.put(u1).put(v1);
				buffer.put(0.7f).put(0.7f).put(0.7f);
				buffer.put(x).put(y + 1).put(z + 0.5f);
				buffer.put(u3).put(v1);
				buffer.put(0.7f).put(0.7f).put(0.7f);
				buffer.put(x + 1).put(y + 1).put(z + 0.5f);
				buffer.put(u3).put(v2);
				buffer.put(0.7f).put(0.7f).put(0.7f);
				buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);

				return 12;
			case BlockFace.BACK:
				buffer.put(u1).put(v3);
				buffer.put(0.7f).put(0.7f).put(0.7f);
				buffer.put(x).put(y).put(z + 1);
				buffer.put(u2).put(v3);
				buffer.put(0.7f).put(0.7f).put(0.7f);
				buffer.put(x + 1).put(y).put(z + 1);
				buffer.put(u1).put(v1);
				buffer.put(0.7f).put(0.7f).put(0.7f);
				buffer.put(x).put(y + 1).put(z + 1);
				buffer.put(u1).put(v1);
				buffer.put(0.7f).put(0.7f).put(0.7f);
				buffer.put(x).put(y + 1).put(z + 1);
				buffer.put(u2).put(v3);
				buffer.put(0.7f).put(0.7f).put(0.7f);
				buffer.put(x + 1).put(y).put(z + 1);
				buffer.put(u2).put(v1);
				buffer.put(0.7f).put(0.7f).put(0.7f);
				buffer.put(x + 1).put(y + 1).put(z + 1);
				return 6;
			case BlockFace.LEFT:
				buffer.put(u1).put(v2);
				buffer.put(0.7f).put(0.7f).put(0.7f);
				buffer.put(x).put(y).put(z);
				buffer.put(u3).put(v2);
				buffer.put(0.7f).put(0.7f).put(0.7f);
				buffer.put(x).put(y).put(z + 1);
				buffer.put(u1).put(v1);
				buffer.put(0.7f).put(0.7f).put(0.7f);
				buffer.put(x).put(y + 0.5f).put(z);
				buffer.put(u1).put(v1);
				buffer.put(0.7f).put(0.7f).put(0.7f);
				buffer.put(x).put(y + 0.5f).put(z);
				buffer.put(u3).put(v2);
				buffer.put(0.7f).put(0.7f).put(0.7f);
				buffer.put(x).put(y).put(z + 1);
				buffer.put(u3).put(v1);
				buffer.put(0.7f).put(0.7f).put(0.7f);
				buffer.put(x).put(y + 0.5f).put(z + 1);

				buffer.put(u1).put(v2);
				buffer.put(0.7f).put(0.7f).put(0.7f);
				buffer.put(x).put(y + 0.5f).put(z + 0.5f);
				buffer.put(u2).put(v2);
				buffer.put(0.7f).put(0.7f).put(0.7f);
				buffer.put(x).put(y + 0.5f).put(z + 1);
				buffer.put(u1).put(v1);
				buffer.put(0.7f).put(0.7f).put(0.7f);
				buffer.put(x).put(y + 1).put(z + 0.5f);
				buffer.put(u1).put(v1);
				buffer.put(0.7f).put(0.7f).put(0.7f);
				buffer.put(x).put(y + 1).put(z + 0.5f);
				buffer.put(u2).put(v2);
				buffer.put(0.7f).put(0.7f).put(0.7f);
				buffer.put(x).put(y + 0.5f).put(z + 1);
				buffer.put(u2).put(v1);
				buffer.put(0.7f).put(0.7f).put(0.7f);
				buffer.put(x).put(y + 1).put(z + 1);

				return 12;
			case BlockFace.RIGHT:
				buffer.put(u1).put(v2);
				buffer.put(0.7f).put(0.7f).put(0.7f);
				buffer.put(x + 1).put(y).put(z);
				buffer.put(u1).put(v1);
				buffer.put(0.7f).put(0.7f).put(0.7f);
				buffer.put(x + 1).put(y + 0.5f).put(z);
				buffer.put(u3).put(v2);
				buffer.put(0.7f).put(0.7f).put(0.7f);
				buffer.put(x + 1).put(y).put(z + 1);
				buffer.put(u1).put(v1);
				buffer.put(0.7f).put(0.7f).put(0.7f);
				buffer.put(x + 1).put(y + 0.5f).put(z);
				buffer.put(u3).put(v1);
				buffer.put(0.7f).put(0.7f).put(0.7f);
				buffer.put(x + 1).put(y + 0.5f).put(z + 1);
				buffer.put(u3).put(v2);
				buffer.put(0.7f).put(0.7f).put(0.7f);
				buffer.put(x + 1).put(y).put(z + 1);

				buffer.put(u1).put(v2);
				buffer.put(0.7f).put(0.7f).put(0.7f);
				buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);
				buffer.put(u1).put(v1);
				buffer.put(0.7f).put(0.7f).put(0.7f);
				buffer.put(x + 1).put(y + 1).put(z + 0.5f);
				buffer.put(u2).put(v2);
				buffer.put(0.7f).put(0.7f).put(0.7f);
				buffer.put(x + 1).put(y + 0.5f).put(z + 1);
				buffer.put(u1).put(v1);
				buffer.put(0.7f).put(0.7f).put(0.7f);
				buffer.put(x + 1).put(y + 1).put(z + 0.5f);
				buffer.put(u2).put(v1);
				buffer.put(0.7f).put(0.7f).put(0.7f);
				buffer.put(x + 1).put(y + 1).put(z + 1);
				buffer.put(u2).put(v2);
				buffer.put(0.7f).put(0.7f).put(0.7f);
				buffer.put(x + 1).put(y + 0.5f).put(z + 1);

				return 12;
			case BlockFace.TOP:
				buffer.put(u2).put(v3);
				buffer.put(1.0f).put(1.0f).put(1.0f);
				buffer.put(x).put(y + 1).put(z + 0.5f);
				buffer.put(u3).put(v3);
				buffer.put(1.0f).put(1.0f).put(1.0f);
				buffer.put(x).put(y + 1).put(z + 1);
				buffer.put(u2).put(v1);
				buffer.put(1.0f).put(1.0f).put(1.0f);
				buffer.put(x + 1).put(y + 1).put(z + 0.5f);
				buffer.put(u2).put(v1);
				buffer.put(1.0f).put(1.0f).put(1.0f);
				buffer.put(x + 1).put(y + 1).put(z + 0.5f);
				buffer.put(u3).put(v3);
				buffer.put(1.0f).put(1.0f).put(1.0f);
				buffer.put(x).put(y + 1).put(z + 1);
				buffer.put(u3).put(v1);
				buffer.put(1.0f).put(1.0f).put(1.0f);
				buffer.put(x + 1).put(y + 1).put(z + 1);

				buffer.put(u2).put(v3);
				buffer.put(1.0f).put(1.0f).put(1.0f);
				buffer.put(x).put(y + 0.5f).put(z);
				buffer.put(u3).put(v3);
				buffer.put(1.0f).put(1.0f).put(1.0f);
				buffer.put(x).put(y + 0.5f).put(z + 0.5f);
				buffer.put(u2).put(v1);
				buffer.put(1.0f).put(1.0f).put(1.0f);
				buffer.put(x + 1).put(y + 0.5f).put(z);
				buffer.put(u2).put(v1);
				buffer.put(1.0f).put(1.0f).put(1.0f);
				buffer.put(x + 1).put(y + 0.5f).put(z);
				buffer.put(u3).put(v3);
				buffer.put(1.0f).put(1.0f).put(1.0f);
				buffer.put(x).put(y + 0.5f).put(z + 0.5f);
				buffer.put(u3).put(v1);
				buffer.put(1.0f).put(1.0f).put(1.0f);
				buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);

				return 12;
			case BlockFace.BOTTOM:
				buffer.put(u1).put(v3);
				buffer.put(0.5f).put(0.5f).put(0.5f);
				buffer.put(x).put(y).put(z);
				buffer.put(u1).put(v1);
				buffer.put(0.5f).put(0.5f).put(0.5f);
				buffer.put(x + 1).put(y).put(z);
				buffer.put(u3).put(v3);
				buffer.put(0.5f).put(0.5f).put(0.5f);
				buffer.put(x).put(y).put(z + 1);
				buffer.put(u1).put(v1);
				buffer.put(0.5f).put(0.5f).put(0.5f);
				buffer.put(x + 1).put(y).put(z);
				buffer.put(u3).put(v1);
				buffer.put(0.5f).put(0.5f).put(0.5f);
				buffer.put(x + 1).put(y).put(z + 1);
				buffer.put(u3).put(v3);
				buffer.put(0.5f).put(0.5f).put(0.5f);
				buffer.put(x).put(y).put(z + 1);

				return 6;
			}

		} else {
			RotateStat stat = getRotateStat(params.faceTo, params.slabOrStairData, world, x, y, z);

			if (params.slabOrStairData == SlabAndStairData.DOWN) {
				switch (params.faceTo) {
				case BlockFace.FRONT:
					switch (face) {
					case BlockFace.FRONT:
						buffer.put(u1).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y).put(z);
						buffer.put(u1).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 0.5f).put(z);
						buffer.put(u3).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y).put(z);
						buffer.put(u1).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 0.5f).put(z);
						buffer.put(u3).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y + 0.5f).put(z);
						buffer.put(u3).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y).put(z);

						if (stat != null && stat.getRotateSection() == BlockFace.RIGHT
								&& stat.getRotateTo() == BlockFace.LEFT) {
							buffer.put(u1).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);
							buffer.put(u2).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);
							buffer.put(u2).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 1).put(z + 0.5f);
							buffer.put(u2).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);
						} else if (stat != null && stat.getRotateSection() == BlockFace.LEFT
								&& stat.getRotateTo() == BlockFace.RIGHT) {
							buffer.put(u1).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 1).put(z + 0.5f);
							buffer.put(u2).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 1).put(z + 0.5f);
							buffer.put(u2).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);
							buffer.put(u2).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);
						} else if (stat != null && stat.getRotateSection() == BlockFace.RIGHT
								&& stat.getRotateTo() == BlockFace.RIGHT) {
							buffer.put(u1).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);
							buffer.put(u2).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);
							buffer.put(u2).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 1).put(z + 0.5f);
							buffer.put(u2).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);
						} else if (stat != null && stat.getRotateSection() == BlockFace.LEFT
								&& stat.rotateTo == BlockFace.LEFT) {
							buffer.put(u1).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 1).put(z + 0.5f);
							buffer.put(u2).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 1).put(z + 0.5f);
							buffer.put(u2).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);
							buffer.put(u2).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);
						} else {
							buffer.put(u1).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 1).put(z + 0.5f);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 1).put(z + 0.5f);
							buffer.put(u3).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 1).put(z + 0.5f);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);
						}

						return 12;
					case BlockFace.BACK:
						if (stat != null && stat.getRotateSection() == BlockFace.RIGHT
								&& stat.getRotateTo() == BlockFace.RIGHT) {
							buffer.put(u1).put(v3);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y).put(z + 1);
							buffer.put(u3).put(v3);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 1).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 1).put(z + 1);
							buffer.put(u3).put(v3);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z + 1);
							buffer.put(u3).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 1).put(z + 1);
							return 6;
						} else if (stat != null && stat.getRotateSection() == BlockFace.LEFT
								&& stat.rotateTo == BlockFace.LEFT) {
							buffer.put(u1).put(v3);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y).put(z + 1);
							buffer.put(u3).put(v3);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 1).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 1).put(z + 1);
							buffer.put(u3).put(v3);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z + 1);
							buffer.put(u3).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 1).put(z + 1);
							return 6;
						} else if (stat != null) {
							return 0;
						} else {
							buffer.put(u1).put(v3);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y).put(z + 1);
							buffer.put(u3).put(v3);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 1).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 1).put(z + 1);
							buffer.put(u3).put(v3);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z + 1);
							buffer.put(u3).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 1).put(z + 1);
						}
						return 6;
					case BlockFace.LEFT:
						if (stat != null && stat.getRotateSection() == BlockFace.RIGHT
								&& stat.getRotateTo() == BlockFace.RIGHT) {
							buffer.put(u1).put(v3);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y).put(z);
							buffer.put(u3).put(v3);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 1).put(z);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 1).put(z);
							buffer.put(u3).put(v3);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y).put(z + 1);
							buffer.put(u3).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 1).put(z + 1);

							return 6;
						} else {
							buffer.put(u1).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y).put(z);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y).put(z + 1);
							buffer.put(u3).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z + 1);

							if (stat != null && stat.getRotateSection() == BlockFace.RIGHT
									&& stat.getRotateTo() == BlockFace.LEFT) {
								buffer.put(u1).put(v2);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);
								buffer.put(u2).put(v2);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 0.5f).put(y + 0.5f).put(z + 1);
								buffer.put(u1).put(v1);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);
								buffer.put(u1).put(v1);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);
								buffer.put(u2).put(v2);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 0.5f).put(y + 0.5f).put(z + 1);
								buffer.put(u2).put(v1);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 0.5f).put(y + 1).put(z + 1);
							} else if (stat != null && stat.getRotateSection() == BlockFace.LEFT
									&& stat.rotateTo == BlockFace.LEFT) {
								buffer.put(u1).put(v2);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x).put(y + 0.5f).put(z + 0.5f);
								buffer.put(u2).put(v2);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x).put(y + 0.5f).put(z + 1);
								buffer.put(u1).put(v1);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x).put(y + 1).put(z + 0.5f);
								buffer.put(u1).put(v1);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x).put(y + 1).put(z + 0.5f);
								buffer.put(u2).put(v2);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x).put(y + 0.5f).put(z + 1);
								buffer.put(u2).put(v1);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x).put(y + 1).put(z + 1);

								buffer.put(u1).put(v2);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 0.5f).put(y + 0.5f).put(z);
								buffer.put(u2).put(v2);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);
								buffer.put(u1).put(v1);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 0.5f).put(y + 1).put(z);
								buffer.put(u1).put(v1);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 0.5f).put(y + 1).put(z);
								buffer.put(u2).put(v2);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);
								buffer.put(u2).put(v1);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);

								return 18;
							} else {
								buffer.put(u1).put(v2);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x).put(y + 0.5f).put(z + 0.5f);
								buffer.put(u2).put(v2);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x).put(y + 0.5f).put(z + 1);
								buffer.put(u1).put(v1);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x).put(y + 1).put(z + 0.5f);
								buffer.put(u1).put(v1);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x).put(y + 1).put(z + 0.5f);
								buffer.put(u2).put(v2);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x).put(y + 0.5f).put(z + 1);
								buffer.put(u2).put(v1);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x).put(y + 1).put(z + 1);
							}

						}
						return 12;
					case BlockFace.RIGHT:
						if (stat != null && stat.getRotateSection() == BlockFace.LEFT
								&& stat.rotateTo == BlockFace.LEFT) {
							buffer.put(u1).put(v3);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 1).put(z);
							buffer.put(u3).put(v3);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 1).put(z);
							buffer.put(u3).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 1).put(z + 1);
							buffer.put(u3).put(v3);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z + 1);

							return 6;
						} else {
							buffer.put(u1).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z);
							buffer.put(u3).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 1);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z + 1);

							if (stat != null && stat.getRotateSection() == BlockFace.LEFT
									&& stat.getRotateTo() == BlockFace.RIGHT) {
								buffer.put(u1).put(v2);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);
								buffer.put(u1).put(v1);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);
								buffer.put(u2).put(v2);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 0.5f).put(y + 0.5f).put(z + 1);
								buffer.put(u1).put(v1);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);
								buffer.put(u2).put(v1);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 0.5f).put(y + 1).put(z + 1);
								buffer.put(u2).put(v2);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 0.5f).put(y + 0.5f).put(z + 1);
							} else if (stat != null && stat.getRotateSection() == BlockFace.RIGHT
									&& stat.getRotateTo() == BlockFace.RIGHT) {
								buffer.put(u1).put(v2);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 0.5f).put(y + 0.5f).put(z);
								buffer.put(u1).put(v1);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 0.5f).put(y + 1).put(z);
								buffer.put(u2).put(v2);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);
								buffer.put(u1).put(v1);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 0.5f).put(y + 1).put(z);
								buffer.put(u2).put(v1);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);
								buffer.put(u2).put(v2);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);

								buffer.put(u1).put(v2);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);
								buffer.put(u1).put(v1);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 1).put(y + 1).put(z + 0.5f);
								buffer.put(u2).put(v2);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 1).put(y + 0.5f).put(z + 1);
								buffer.put(u1).put(v1);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 1).put(y + 1).put(z + 0.5f);
								buffer.put(u2).put(v1);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 1).put(y + 1).put(z + 1);
								buffer.put(u2).put(v2);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 1).put(y + 0.5f).put(z + 1);

								return 18;
							} else {
								buffer.put(u1).put(v2);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);
								buffer.put(u1).put(v1);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 1).put(y + 1).put(z + 0.5f);
								buffer.put(u2).put(v2);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 1).put(y + 0.5f).put(z + 1);
								buffer.put(u1).put(v1);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 1).put(y + 1).put(z + 0.5f);
								buffer.put(u2).put(v1);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 1).put(y + 1).put(z + 1);
								buffer.put(u2).put(v2);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 1).put(y + 0.5f).put(z + 1);
							}

						}
						return 12;
					case BlockFace.TOP:
						if (stat != null && stat.getRotateSection() == BlockFace.RIGHT
								&& stat.getRotateTo() == BlockFace.RIGHT) {
							buffer.put(u2).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z);
							buffer.put(u3).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u2).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 0.5f).put(z);
							buffer.put(u2).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 0.5f).put(z);
							buffer.put(u3).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u3).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);

							buffer.put(u2).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 1).put(z);
							buffer.put(u3).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 1).put(z + 0.5f);
							buffer.put(u2).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 1).put(z);
							buffer.put(u2).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 1).put(z);
							buffer.put(u3).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 1).put(z + 0.5f);
							buffer.put(u3).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);

							buffer.put(v3).put(v3);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 1).put(z + 0.5f);
							buffer.put(u3).put(v3);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 1).put(z + 1);
							buffer.put(u2).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 1).put(z + 0.5f);
							buffer.put(u2).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 1).put(z + 0.5f);
							buffer.put(u3).put(v3);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 1).put(z + 1);
							buffer.put(u3).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 1).put(z + 1);

							return 18;
						} else if (stat != null && stat.getRotateSection() == BlockFace.LEFT
								&& stat.rotateTo == BlockFace.LEFT) {
							buffer.put(u2).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 1).put(z);
							buffer.put(u3).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);
							buffer.put(u2).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 1).put(z);
							buffer.put(u2).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 1).put(z);
							buffer.put(u3).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);
							buffer.put(u3).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 1).put(z + 0.5f);

							buffer.put(u2).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 0.5f).put(z);
							buffer.put(u3).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u2).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z);
							buffer.put(u2).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z);
							buffer.put(u3).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u3).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);

							buffer.put(v3).put(v3);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 1).put(z + 0.5f);
							buffer.put(u3).put(v3);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 1).put(z + 1);
							buffer.put(u2).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 1).put(z + 0.5f);
							buffer.put(u2).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 1).put(z + 0.5f);
							buffer.put(u3).put(v3);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 1).put(z + 1);
							buffer.put(u3).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 1).put(z + 1);

							return 18;
						} else {
							buffer.put(u2).put(v3);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 0.5f).put(z);
							buffer.put(u3).put(v3);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u2).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 0.5f).put(z);
							buffer.put(u2).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 0.5f).put(z);
							buffer.put(u3).put(v3);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u3).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);

							if (stat != null && stat.getRotateSection() == BlockFace.RIGHT
									&& stat.getRotateTo() == BlockFace.LEFT) {
								buffer.put(v3).put(v2);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);
								buffer.put(u3).put(v2);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x + 0.5f).put(y + 1).put(z + 1);
								buffer.put(u2).put(v1);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x + 1).put(y + 1).put(z + 0.5f);
								buffer.put(u2).put(v1);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x + 1).put(y + 1).put(z + 0.5f);
								buffer.put(u3).put(v2);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x + 0.5f).put(y + 1).put(z + 1);
								buffer.put(u3).put(v1);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x + 1).put(y + 1).put(z + 1);

								buffer.put(v3).put(v2);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x).put(y + 0.5f).put(z + 0.5f);
								buffer.put(u3).put(v2);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x).put(y + 0.5f).put(z + 1);
								buffer.put(u2).put(v1);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);
								buffer.put(u2).put(v1);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);
								buffer.put(u3).put(v2);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x).put(y + 0.5f).put(z + 1);
								buffer.put(u3).put(v1);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x + 0.5f).put(y + 0.5f).put(z + 1);

								return 18;
							} else if (stat != null && stat.getRotateSection() == BlockFace.LEFT
									&& stat.getRotateTo() == BlockFace.RIGHT) {
								buffer.put(v3).put(v2);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x).put(y + 1).put(z + 0.5f);
								buffer.put(u3).put(v2);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x).put(y + 1).put(z + 1);
								buffer.put(u2).put(v1);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);
								buffer.put(u2).put(v1);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);
								buffer.put(u3).put(v2);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x).put(y + 1).put(z + 1);
								buffer.put(u3).put(v1);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x + 0.5f).put(y + 1).put(z + 1);

								buffer.put(v3).put(v2);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);
								buffer.put(u3).put(v2);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x + 0.5f).put(y + 0.5f).put(z + 1);
								buffer.put(u2).put(v1);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);
								buffer.put(u2).put(v1);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);
								buffer.put(u3).put(v2);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x + 0.5f).put(y + 0.5f).put(z + 1);
								buffer.put(u3).put(v1);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x + 1).put(y + 0.5f).put(z + 1);

								return 18;
							} else {
								buffer.put(v3).put(v3);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x).put(y + 1).put(z + 0.5f);
								buffer.put(u3).put(v3);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x).put(y + 1).put(z + 1);
								buffer.put(u2).put(v1);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x + 1).put(y + 1).put(z + 0.5f);
								buffer.put(u2).put(v1);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x + 1).put(y + 1).put(z + 0.5f);
								buffer.put(u3).put(v3);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x).put(y + 1).put(z + 1);
								buffer.put(u3).put(v1);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x + 1).put(y + 1).put(z + 1);
							}

							return 12;
						}
					case BlockFace.BOTTOM:
						buffer.put(u1).put(v3);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x).put(y).put(z);
						buffer.put(u1).put(v1);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x + 1).put(y).put(z);
						buffer.put(u3).put(v3);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x).put(y).put(z + 1);
						buffer.put(u1).put(v1);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x + 1).put(y).put(z);
						buffer.put(u3).put(v1);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x + 1).put(y).put(z + 1);
						buffer.put(u3).put(v3);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x).put(y).put(z + 1);

						return 6;
					}
					break;
				case BlockFace.BACK:
					switch (face) {
					case BlockFace.FRONT:
						if (stat != null && stat.getRotateSection() == BlockFace.RIGHT
								&& stat.getRotateTo() == BlockFace.LEFT) {
							buffer.put(u1).put(v3);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y).put(z);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 1).put(z);
							buffer.put(u3).put(v3);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 1).put(z);
							buffer.put(u3).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 1).put(z);
							buffer.put(u3).put(v3);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z);

						} else if (stat != null && stat.getRotateSection() == BlockFace.LEFT
								&& stat.getRotateTo() == BlockFace.RIGHT) {
							buffer.put(u1).put(v3);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y).put(z);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 1).put(z);
							buffer.put(u3).put(v3);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 1).put(z);
							buffer.put(u3).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 1).put(z);
							buffer.put(u3).put(v3);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z);

						} else if (stat != null) {
							return 0;
						} else {
							buffer.put(u1).put(v3);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y).put(z);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 1).put(z);
							buffer.put(u3).put(v3);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 1).put(z);
							buffer.put(u3).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 1).put(z);
							buffer.put(u3).put(v3);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z);

						}
						return 6;
					case BlockFace.BACK:
						if (stat != null && stat.getRotateSection() == BlockFace.LEFT
								&& stat.getRotateTo() == BlockFace.RIGHT) {
							buffer.put(u1).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u2).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);
							buffer.put(u2).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u2).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 1).put(z + 0.5f);

							return 6;
						}

						buffer.put(u1).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y).put(z + 1);
						buffer.put(u3).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y).put(z + 1);
						buffer.put(u1).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 0.5f).put(z + 1);
						buffer.put(u1).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 0.5f).put(z + 1);
						buffer.put(u3).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y).put(z + 1);
						buffer.put(u3).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y + 0.5f).put(z + 1);

						if (stat != null && stat.rotateSection == BlockFace.RIGHT && stat.rotateTo == BlockFace.RIGHT) {
							buffer.put(u1).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u2).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 1).put(z + 0.5f);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 1).put(z + 0.5f);
							buffer.put(u2).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u2).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);

						} else if (stat != null && stat.rotateSection == BlockFace.LEFT
								&& stat.rotateTo == BlockFace.LEFT) {
							buffer.put(u1).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u2).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);
							buffer.put(u2).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u2).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 1).put(z + 0.5f);

						} else if (stat != null && stat.getRotateSection() == BlockFace.RIGHT
								&& stat.getRotateTo() == BlockFace.LEFT) {
							buffer.put(u1).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u2).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 1).put(z + 0.5f);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 1).put(z + 0.5f);
							buffer.put(u2).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u2).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);

						} else {
							buffer.put(u1).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 1).put(z + 0.5f);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 1).put(z + 0.5f);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u3).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 1).put(z + 0.5f);

						}

						return 12;
					case BlockFace.LEFT:
						if (stat != null && stat.getRotateSection() == BlockFace.LEFT
								&& stat.getRotateTo() == BlockFace.RIGHT) {
							buffer.put(u1).put(v3);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y).put(z);
							buffer.put(u3).put(v3);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 1).put(z);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 1).put(z);
							buffer.put(u3).put(v3);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y).put(z + 1);
							buffer.put(u3).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 1).put(z + 1);
							return 6;
						} else {
							buffer.put(u1).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y).put(z);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y).put(z + 1);
							buffer.put(u3).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z + 1);

							if (stat != null && stat.getRotateSection() == BlockFace.LEFT
									&& stat.getRotateTo() == BlockFace.LEFT) {
								buffer.put(u1).put(v2);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 0.5f).put(y + 0.5f).put(z);
								buffer.put(u2).put(v2);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);
								buffer.put(u1).put(v1);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 0.5f).put(y + 1).put(z);
								buffer.put(u1).put(v1);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 0.5f).put(y + 1).put(z);
								buffer.put(u2).put(v2);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);
								buffer.put(u2).put(v1);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);

							} else if (stat != null && stat.getRotateSection() == BlockFace.RIGHT
									&& stat.getRotateTo() == BlockFace.LEFT) {
								buffer.put(u1).put(v2);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x).put(y + 0.5f).put(z);
								buffer.put(u2).put(v2);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x).put(y + 0.5f).put(z + 0.5f);
								buffer.put(u1).put(v1);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x).put(y + 1).put(z);
								buffer.put(u1).put(v1);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x).put(y + 1).put(z);
								buffer.put(u2).put(v2);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x).put(y + 0.5f).put(z + 0.5f);
								buffer.put(u2).put(v1);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x).put(y + 1).put(z + 0.5f);

								buffer.put(u1).put(v2);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);
								buffer.put(u2).put(v2);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 0.5f).put(y + 0.5f).put(z + 1);
								buffer.put(u1).put(v1);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);
								buffer.put(u1).put(v1);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);
								buffer.put(u2).put(v2);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 0.5f).put(y + 0.5f).put(z + 1);
								buffer.put(u2).put(v1);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 0.5f).put(y + 1).put(z + 1);

								return 18;
							} else {
								buffer.put(u1).put(v2);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x).put(y + 0.5f).put(z);
								buffer.put(u2).put(v2);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x).put(y + 0.5f).put(z + 0.5f);
								buffer.put(u1).put(v1);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x).put(y + 1).put(z);
								buffer.put(u1).put(v1);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x).put(y + 1).put(z);
								buffer.put(u2).put(v2);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x).put(y + 0.5f).put(z + 0.5f);
								buffer.put(u2).put(v1);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x).put(y + 1).put(z + 0.5f);

							}

						}
						return 12;
					case BlockFace.RIGHT:
						if (stat != null && stat.getRotateSection() == BlockFace.RIGHT
								&& stat.getRotateTo() == BlockFace.LEFT) {
							buffer.put(u1).put(v3);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 1).put(z);
							buffer.put(u3).put(v3);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 1).put(z);
							buffer.put(u3).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 1).put(z + 1);
							buffer.put(u3).put(v3);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z + 1);

							return 6;
						} else {
							buffer.put(u1).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z);
							buffer.put(u3).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 1);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z + 1);

							if (stat != null && stat.rotateSection == BlockFace.RIGHT
									&& stat.rotateTo == BlockFace.RIGHT) {
								buffer.put(u1).put(v2);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 0.5f).put(y + 0.5f).put(z);
								buffer.put(u1).put(v1);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 0.5f).put(y + 1).put(z);
								buffer.put(u2).put(v2);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);
								buffer.put(u1).put(v1);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 0.5f).put(y + 1).put(z);
								buffer.put(u2).put(v1);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);
								buffer.put(u2).put(v2);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);

							} else {
								buffer.put(u1).put(v2);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 1).put(y + 0.5f).put(z);
								buffer.put(u1).put(v1);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 1).put(y + 1).put(z);
								buffer.put(u2).put(v2);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);
								buffer.put(u1).put(v1);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 1).put(y + 1).put(z);
								buffer.put(u2).put(v1);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 1).put(y + 1).put(z + 0.5f);
								buffer.put(u2).put(v2);
								buffer.put(0.7f).put(0.7f).put(0.7f);
								buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);

								if (stat != null && stat.getRotateSection() == BlockFace.LEFT
										&& stat.getRotateTo() == BlockFace.RIGHT) {
									buffer.put(u1).put(v2);
									buffer.put(0.7f).put(0.7f).put(0.7f);
									buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);
									buffer.put(u1).put(v1);
									buffer.put(0.7f).put(0.7f).put(0.7f);
									buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);
									buffer.put(u2).put(v2);
									buffer.put(0.7f).put(0.7f).put(0.7f);
									buffer.put(x + 0.5f).put(y + 0.5f).put(z + 1);
									buffer.put(u1).put(v1);
									buffer.put(0.7f).put(0.7f).put(0.7f);
									buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);
									buffer.put(u2).put(v1);
									buffer.put(0.7f).put(0.7f).put(0.7f);
									buffer.put(x + 0.5f).put(y + 1).put(z + 1);
									buffer.put(u2).put(v2);
									buffer.put(0.7f).put(0.7f).put(0.7f);
									buffer.put(x + 0.5f).put(y + 0.5f).put(z + 1);

									return 18;
								}

							}

						}

						return 12;
					case BlockFace.TOP:
						if (stat != null && stat.getRotateSection() == BlockFace.RIGHT
								&& stat.getRotateTo() == BlockFace.LEFT) {
							buffer.put(u2).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u3).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 0.5f).put(z + 1);
							buffer.put(u2).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u2).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u3).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 0.5f).put(z + 1);
							buffer.put(u3).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 1);

							buffer.put(u2).put(v3);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 1).put(z);
							buffer.put(u3).put(v3);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 1).put(z + 0.5f);
							buffer.put(u2).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 1).put(z);
							buffer.put(u2).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 1).put(z);
							buffer.put(u3).put(v3);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 1).put(z + 0.5f);
							buffer.put(u3).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 1).put(z + 0.5f);

							buffer.put(u2).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);
							buffer.put(u3).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 1);
							buffer.put(u2).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 1).put(z + 0.5f);
							buffer.put(u2).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 1).put(z + 0.5f);
							buffer.put(u3).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 1);
							buffer.put(u3).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 1).put(z + 1);

							return 18;
						} else if (stat != null && stat.getRotateSection() == BlockFace.LEFT
								&& stat.getRotateTo() == BlockFace.LEFT) {
							buffer.put(u1).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 0.5f).put(z);
							buffer.put(u3).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 0.5f).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z);
							buffer.put(u1).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z);
							buffer.put(u3).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 0.5f).put(z + 1);
							buffer.put(u3).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 1);

							buffer.put(u2).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 1).put(z);
							buffer.put(u3).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);
							buffer.put(u2).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 1).put(z);
							buffer.put(u2).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 1).put(z);
							buffer.put(u3).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);
							buffer.put(u3).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 1).put(z + 0.5f);

							buffer.put(u2).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u3).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 1);
							buffer.put(u2).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u2).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u3).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 1);
							buffer.put(u3).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 1);

							return 18;
						} else if (stat != null && stat.getRotateSection() == BlockFace.LEFT
								&& stat.getRotateTo() == BlockFace.RIGHT) {
							buffer.put(u2).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 1).put(z);
							buffer.put(u3).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);
							buffer.put(u2).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 1).put(z);
							buffer.put(u2).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 1).put(z);
							buffer.put(u3).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);
							buffer.put(u3).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 1).put(z + 0.5f);

							buffer.put(u1).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 1).put(z);
							buffer.put(u3).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 1).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 1).put(z);
							buffer.put(u1).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 1).put(z);
							buffer.put(u3).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 1).put(z + 1);
							buffer.put(u3).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 1);

							buffer.put(u2).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u3).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 1);
							buffer.put(u2).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u2).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u3).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 1);
							buffer.put(u3).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 1);

							return 18;
						} else {
							buffer.put(u2).put(v3);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u3).put(v3);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 0.5f).put(z + 1);
							buffer.put(u2).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u2).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u3).put(v3);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 0.5f).put(z + 1);
							buffer.put(u3).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 1);

							if (stat != null && stat.rotateSection == BlockFace.RIGHT
									&& stat.rotateTo == BlockFace.RIGHT) {
								buffer.put(u2).put(v2);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x).put(y + 1).put(z);
								buffer.put(u3).put(v2);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x).put(y + 1).put(z + 0.5f);
								buffer.put(u2).put(v1);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x + 0.5f).put(y + 1).put(z);
								buffer.put(u2).put(v1);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x + 0.5f).put(y + 1).put(z);
								buffer.put(u3).put(v2);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x).put(y + 1).put(z + 0.5f);
								buffer.put(u3).put(v1);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);

								buffer.put(u2).put(v2);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x + 0.5f).put(y + 0.5f).put(z);
								buffer.put(u3).put(v2);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);
								buffer.put(u2).put(v1);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x + 1).put(y + 0.5f).put(z);
								buffer.put(u2).put(v1);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x + 1).put(y + 0.5f).put(z);
								buffer.put(u3).put(v2);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);
								buffer.put(u3).put(v1);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);

								return 18;
							} else if (stat != null && stat.rotateSection == BlockFace.LEFT
									&& stat.rotateTo == BlockFace.LEFT) {
								buffer.put(u2).put(v2);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x).put(y + 0.5f).put(z);
								buffer.put(u3).put(v2);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x).put(y + 0.5f).put(z + 0.5f);
								buffer.put(u2).put(v1);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x + 0.5f).put(y + 0.5f).put(z);
								buffer.put(u2).put(v1);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x + 0.5f).put(y + 0.5f).put(z);
								buffer.put(u3).put(v2);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x).put(y + 0.5f).put(z + 0.5f);
								buffer.put(u3).put(v1);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);

								buffer.put(u2).put(v2);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x + 0.5f).put(y + 1).put(z);
								buffer.put(u3).put(v2);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);
								buffer.put(u2).put(v1);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x + 1).put(y + 1).put(z);
								buffer.put(u2).put(v1);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x + 1).put(y + 1).put(z);
								buffer.put(u3).put(v2);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);
								buffer.put(u3).put(v1);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x + 1).put(y + 1).put(z + 0.5f);

								return 18;
							} else {
								buffer.put(u2).put(v3);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x).put(y + 1).put(z);
								buffer.put(u3).put(v3);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x).put(y + 1).put(z + 0.5f);
								buffer.put(u2).put(v1);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x + 1).put(y + 1).put(z);
								buffer.put(u2).put(v1);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x + 1).put(y + 1).put(z);
								buffer.put(u3).put(v3);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x).put(y + 1).put(z + 0.5f);
								buffer.put(u3).put(v1);
								buffer.put(1.0f).put(1.0f).put(1.0f);
								buffer.put(x + 1).put(y + 1).put(z + 0.5f);

							}

						}
						return 12;
					case BlockFace.BOTTOM:
						buffer.put(u1).put(v3);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x).put(y).put(z);
						buffer.put(u1).put(v1);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x + 1).put(y).put(z);
						buffer.put(u3).put(v3);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x).put(y).put(z + 1);
						buffer.put(u1).put(v1);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x + 1).put(y).put(z);
						buffer.put(u3).put(v1);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x + 1).put(y).put(z + 1);
						buffer.put(u3).put(v3);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x).put(y).put(z + 1);

						return 6;
					}
					break;
				case BlockFace.LEFT:
					if (stat != null && stat.getRotateSection() == BlockFace.LEFT
							&& stat.getRotateTo() == BlockFace.FRONT) {
						switch (face) {
						case BlockFace.FRONT:
							buffer.put(u1).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y).put(z);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z);
							buffer.put(u3).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z);

							buffer.put(u1).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);
							buffer.put(u2).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);
							buffer.put(u2).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 1).put(z + 0.5f);
							buffer.put(u2).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);

							return 12;
						case BlockFace.BACK:
							buffer.put(u1).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y).put(z + 1);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z + 1);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z + 1);
							buffer.put(u3).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 1);

							buffer.put(u2).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 1);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 1);
							buffer.put(u2).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 1);
							buffer.put(u2).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 1);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 1);
							buffer.put(u3).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 1).put(z + 1);

							return 12;
						case BlockFace.LEFT:
							buffer.put(u1).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y).put(z);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y).put(z + 1);
							buffer.put(u3).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z + 1);

							buffer.put(u1).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u2).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);
							buffer.put(u2).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 1);
							buffer.put(u2).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 1);

							return 12;
						case BlockFace.RIGHT:
							buffer.put(u1).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z);
							buffer.put(u3).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 1);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z + 1);

							buffer.put(u1).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 1).put(z + 0.5f);
							buffer.put(u2).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 1).put(z + 0.5f);
							buffer.put(u2).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 1).put(z + 1);
							buffer.put(u2).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 1);

							return 12;
						case BlockFace.TOP:
							buffer.put(u2).put(v3);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 0.5f).put(z);
							buffer.put(u3).put(v3);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u2).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 0.5f).put(z);
							buffer.put(u2).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 0.5f).put(z);
							buffer.put(u3).put(v3);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u3).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);

							buffer.put(v3).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);
							buffer.put(u3).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 1);
							buffer.put(u2).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 1).put(z + 0.5f);
							buffer.put(u2).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 1).put(z + 0.5f);
							buffer.put(u3).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 1);
							buffer.put(u3).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 1).put(z + 1);

							buffer.put(v3).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u3).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 0.5f).put(z + 1);
							buffer.put(u2).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u2).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u3).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 0.5f).put(z + 1);
							buffer.put(u3).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 1);

							return 18;
						case BlockFace.BOTTOM:
							buffer.put(u1).put(v3);
							buffer.put(0.5f).put(0.5f).put(0.5f);
							buffer.put(x).put(y).put(z);
							buffer.put(u1).put(v1);
							buffer.put(0.5f).put(0.5f).put(0.5f);
							buffer.put(x + 1).put(y).put(z);
							buffer.put(u3).put(v3);
							buffer.put(0.5f).put(0.5f).put(0.5f);
							buffer.put(x).put(y).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(0.5f).put(0.5f).put(0.5f);
							buffer.put(x + 1).put(y).put(z);
							buffer.put(u3).put(v1);
							buffer.put(0.5f).put(0.5f).put(0.5f);
							buffer.put(x + 1).put(y).put(z + 1);
							buffer.put(u3).put(v3);
							buffer.put(0.5f).put(0.5f).put(0.5f);
							buffer.put(x).put(y).put(z + 1);

							return 6;
						}
					} else if (stat != null && stat.getRotateSection() == BlockFace.RIGHT
							&& stat.getRotateTo() == BlockFace.BACK) {
						switch (face) {
						case BlockFace.FRONT:
							buffer.put(u1).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y).put(z);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z);
							buffer.put(u3).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z);

							buffer.put(u2).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z);
							buffer.put(u2).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 1).put(z);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z);
							buffer.put(u2).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 1).put(z);
							buffer.put(u3).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 1).put(z);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z);

							return 12;
						case BlockFace.BACK:
							buffer.put(u1).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y).put(z + 1);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z + 1);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z + 1);
							buffer.put(u3).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 1);

							buffer.put(u1).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u2).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);
							buffer.put(u2).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u2).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 1).put(z + 0.5f);

							return 12;
						case BlockFace.LEFT:
							buffer.put(u1).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y).put(z);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y).put(z + 1);
							buffer.put(u3).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z + 1);

							buffer.put(u1).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z);
							buffer.put(u2).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 1).put(z);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 1).put(z);
							buffer.put(u2).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u2).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);

							return 12;
						case BlockFace.RIGHT:
							buffer.put(u1).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z);
							buffer.put(u3).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 1);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z + 1);

							buffer.put(u1).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 1).put(z);
							buffer.put(u2).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 1).put(z);
							buffer.put(u2).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 1).put(z + 0.5f);
							buffer.put(u2).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);

							return 12;
						case BlockFace.TOP:
							buffer.put(u1).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 0.5f).put(z);
							buffer.put(u3).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 0.5f).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z);
							buffer.put(u1).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z);
							buffer.put(u3).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 0.5f).put(z + 1);
							buffer.put(u3).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 1);

							buffer.put(u2).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 1).put(z);
							buffer.put(u3).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);
							buffer.put(u2).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 1).put(z);
							buffer.put(u2).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 1).put(z);
							buffer.put(u3).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);
							buffer.put(u3).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 1).put(z + 0.5f);

							buffer.put(u2).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u3).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 1);
							buffer.put(u2).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u2).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u3).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 1);
							buffer.put(u3).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 1);

							return 18;
						case BlockFace.BOTTOM:
							buffer.put(u1).put(v3);
							buffer.put(0.5f).put(0.5f).put(0.5f);
							buffer.put(x).put(y).put(z);
							buffer.put(u1).put(v1);
							buffer.put(0.5f).put(0.5f).put(0.5f);
							buffer.put(x + 1).put(y).put(z);
							buffer.put(u3).put(v3);
							buffer.put(0.5f).put(0.5f).put(0.5f);
							buffer.put(x).put(y).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(0.5f).put(0.5f).put(0.5f);
							buffer.put(x + 1).put(y).put(z);
							buffer.put(u3).put(v1);
							buffer.put(0.5f).put(0.5f).put(0.5f);
							buffer.put(x + 1).put(y).put(z + 1);
							buffer.put(u3).put(v3);
							buffer.put(0.5f).put(0.5f).put(0.5f);
							buffer.put(x).put(y).put(z + 1);

							return 6;
						default:
							return 0;
						}
					} else if (stat != null && stat.getRotateSection() == BlockFace.LEFT
							&& stat.getRotateTo() == BlockFace.BACK) {
						switch (face) {
						case BlockFace.FRONT:
							buffer.put(u1).put(v3);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y).put(z);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 1).put(z);
							buffer.put(u3).put(v3);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 1).put(z);
							buffer.put(u3).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 1).put(z);
							buffer.put(u3).put(v3);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z);
							return 6;
						case BlockFace.BACK:
							buffer.put(u1).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y).put(z + 1);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z + 1);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z + 1);
							buffer.put(u3).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 1);

							buffer.put(u1).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u2).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 1).put(z + 0.5f);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 1).put(z + 0.5f);
							buffer.put(u2).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u2).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);

							buffer.put(u1).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 1);
							buffer.put(u2).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 1);
							buffer.put(u2).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 1);
							buffer.put(u2).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 1).put(z + 1);

							return 18;
						case BlockFace.LEFT:
							buffer.put(u1).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y).put(z);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y).put(z + 1);
							buffer.put(u3).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z + 1);

							buffer.put(u1).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z);
							buffer.put(u2).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 1).put(z);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 1).put(z);
							buffer.put(u2).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u2).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 1).put(z + 0.5f);

							buffer.put(u1).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u2).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);
							buffer.put(u2).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 1);
							buffer.put(u2).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 1);

							return 18;
						case BlockFace.RIGHT:
							buffer.put(u1).put(v3);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 1).put(z);
							buffer.put(u3).put(v3);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 1).put(z);
							buffer.put(u3).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 1).put(z + 1);
							buffer.put(u3).put(v3);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z + 1);

							return 6;
						case BlockFace.TOP:
							buffer.put(u2).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u3).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 0.5f).put(z + 1);
							buffer.put(u2).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u2).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u3).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 0.5f).put(z + 1);
							buffer.put(u3).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 1);

							buffer.put(u2).put(v3);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 1).put(z);
							buffer.put(u3).put(v3);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 1).put(z + 0.5f);
							buffer.put(u2).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 1).put(z);
							buffer.put(u2).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 1).put(z);
							buffer.put(u3).put(v3);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 1).put(z + 0.5f);
							buffer.put(u3).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 1).put(z + 0.5f);

							buffer.put(u2).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);
							buffer.put(u3).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 1);
							buffer.put(u2).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 1).put(z + 0.5f);
							buffer.put(u2).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 1).put(z + 0.5f);
							buffer.put(u3).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 1);
							buffer.put(u3).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 1).put(z + 1);

							return 18;
						case BlockFace.BOTTOM:
							buffer.put(u1).put(v3);
							buffer.put(0.5f).put(0.5f).put(0.5f);
							buffer.put(x).put(y).put(z);
							buffer.put(u1).put(v1);
							buffer.put(0.5f).put(0.5f).put(0.5f);
							buffer.put(x + 1).put(y).put(z);
							buffer.put(u3).put(v3);
							buffer.put(0.5f).put(0.5f).put(0.5f);
							buffer.put(x).put(y).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(0.5f).put(0.5f).put(0.5f);
							buffer.put(x + 1).put(y).put(z);
							buffer.put(u3).put(v1);
							buffer.put(0.5f).put(0.5f).put(0.5f);
							buffer.put(x + 1).put(y).put(z + 1);
							buffer.put(u3).put(v3);
							buffer.put(0.5f).put(0.5f).put(0.5f);
							buffer.put(x).put(y).put(z + 1);

							return 6;
						default:
							return 0;
						}
					} else if (stat != null && stat.getRotateSection() == BlockFace.RIGHT
							&& stat.getRotateTo() == BlockFace.FRONT) {
						switch (face) {
						case BlockFace.FRONT:
							buffer.put(u1).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y).put(z);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z);
							buffer.put(u3).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z);

							buffer.put(u2).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z);
							buffer.put(u2).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 1).put(z);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z);
							buffer.put(u2).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 1).put(z);
							buffer.put(u3).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 1).put(z);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z);

							buffer.put(u2).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u2).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 1).put(z + 0.5f);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u2).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 1).put(z + 0.5f);
							buffer.put(u3).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);

							return 12;
						case BlockFace.BACK:
							buffer.put(u1).put(v3);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y).put(z + 1);
							buffer.put(u3).put(v3);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 1).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 1).put(z + 1);
							buffer.put(u3).put(v3);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z + 1);
							buffer.put(u3).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 1).put(z + 1);
							return 6;
						case BlockFace.LEFT:
							buffer.put(u1).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y).put(z);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y).put(z + 1);
							buffer.put(u3).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z + 1);

							buffer.put(u1).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u2).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 1).put(z + 0.5f);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 1).put(z + 0.5f);
							buffer.put(u2).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z + 1);
							buffer.put(u2).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 1).put(z + 1);

							buffer.put(u1).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z);
							buffer.put(u2).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 1).put(z);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 1).put(z);
							buffer.put(u2).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u2).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);

							return 18;
						case BlockFace.RIGHT:
							buffer.put(u1).put(v3);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 1).put(z);
							buffer.put(u3).put(v3);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 1).put(z);
							buffer.put(u3).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 1).put(z + 1);
							buffer.put(u3).put(v3);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z + 1);

							return 6;
						case BlockFace.TOP:
							buffer.put(u2).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 1).put(z);
							buffer.put(u3).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);
							buffer.put(u2).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 1).put(z);
							buffer.put(u2).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 1).put(z);
							buffer.put(u3).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);
							buffer.put(u3).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 1).put(z + 0.5f);

							buffer.put(u2).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 0.5f).put(z);
							buffer.put(u3).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u2).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z);
							buffer.put(u2).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z);
							buffer.put(u3).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u3).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);

							buffer.put(v3).put(v3);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 1).put(z + 0.5f);
							buffer.put(u3).put(v3);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 1).put(z + 1);
							buffer.put(u2).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 1).put(z + 0.5f);
							buffer.put(u2).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 1).put(z + 0.5f);
							buffer.put(u3).put(v3);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 1).put(z + 1);
							buffer.put(u3).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 1).put(z + 1);

							return 18;
						case BlockFace.BOTTOM:
							buffer.put(u1).put(v3);
							buffer.put(0.5f).put(0.5f).put(0.5f);
							buffer.put(x).put(y).put(z);
							buffer.put(u1).put(v1);
							buffer.put(0.5f).put(0.5f).put(0.5f);
							buffer.put(x + 1).put(y).put(z);
							buffer.put(u3).put(v3);
							buffer.put(0.5f).put(0.5f).put(0.5f);
							buffer.put(x).put(y).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(0.5f).put(0.5f).put(0.5f);
							buffer.put(x + 1).put(y).put(z);
							buffer.put(u3).put(v1);
							buffer.put(0.5f).put(0.5f).put(0.5f);
							buffer.put(x + 1).put(y).put(z + 1);
							buffer.put(u3).put(v3);
							buffer.put(0.5f).put(0.5f).put(0.5f);
							buffer.put(x).put(y).put(z + 1);

							return 6;
						default:
							return 0;
						}
					} else {
						switch (face) {
						case BlockFace.FRONT:
							buffer.put(u1).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y).put(z);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z);
							buffer.put(u3).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z);

							buffer.put(u2).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z);
							buffer.put(u2).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 1).put(z);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z);
							buffer.put(u2).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 1).put(z);
							buffer.put(u3).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 1).put(z);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z);

							return 12;
						case BlockFace.BACK:
							buffer.put(u1).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y).put(z + 1);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z + 1);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z + 1);
							buffer.put(u3).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 1);

							buffer.put(u2).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 1);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 1);
							buffer.put(u2).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 1);
							buffer.put(u2).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 1);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 1);
							buffer.put(u3).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 1).put(z + 1);

							return 12;
						case BlockFace.LEFT:
							buffer.put(u1).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y).put(z);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y).put(z + 1);
							buffer.put(u3).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z + 1);

							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z);
							buffer.put(u1).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 1);
							buffer.put(u3).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 1).put(z);
							buffer.put(u3).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 1).put(z);
							buffer.put(u1).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 1);

							return 12;
						case BlockFace.RIGHT:
							buffer.put(u1).put(v3);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 1).put(z);
							buffer.put(u3).put(v3);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 1).put(z);
							buffer.put(u3).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 1).put(z + 1);
							buffer.put(u3).put(v3);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z + 1);
							return 6;
						case BlockFace.TOP:
							buffer.put(u1).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 0.5f).put(z);
							buffer.put(u3).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 0.5f).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z);
							buffer.put(u1).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z);
							buffer.put(u3).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 0.5f).put(z + 1);
							buffer.put(u3).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 1);

							buffer.put(u1).put(v3);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 1).put(z);
							buffer.put(u3).put(v3);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 1);
							buffer.put(u1).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 1).put(z);
							buffer.put(u1).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 1).put(z);
							buffer.put(u3).put(v3);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 1);
							buffer.put(u3).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 1).put(z + 1);

							return 12;
						case BlockFace.BOTTOM:
							buffer.put(u1).put(v3);
							buffer.put(0.5f).put(0.5f).put(0.5f);
							buffer.put(x).put(y).put(z);
							buffer.put(u1).put(v1);
							buffer.put(0.5f).put(0.5f).put(0.5f);
							buffer.put(x + 1).put(y).put(z);
							buffer.put(u3).put(v3);
							buffer.put(0.5f).put(0.5f).put(0.5f);
							buffer.put(x).put(y).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(0.5f).put(0.5f).put(0.5f);
							buffer.put(x + 1).put(y).put(z);
							buffer.put(u3).put(v1);
							buffer.put(0.5f).put(0.5f).put(0.5f);
							buffer.put(x + 1).put(y).put(z + 1);
							buffer.put(u3).put(v3);
							buffer.put(0.5f).put(0.5f).put(0.5f);
							buffer.put(x).put(y).put(z + 1);

							return 6;
						}
					}
					break;
				case BlockFace.RIGHT:
					// TODO: 我懒了 之后再弄叭~
					if (stat != null && stat.getRotateSection() == BlockFace.LEFT
							&& stat.getRotateTo() == BlockFace.FRONT) {
						switch (face) {
						case BlockFace.FRONT:
							
						case BlockFace.BACK:
							
						case BlockFace.LEFT:
							
						case BlockFace.RIGHT:

						case BlockFace.TOP:
							
						case BlockFace.BOTTOM:
							
						default:
							return 0;
						}
					} else if (stat != null && stat.getRotateSection() == BlockFace.RIGHT
							&& stat.getRotateTo() == BlockFace.BACK) {
						switch (face) {
						case BlockFace.FRONT:
							buffer.put(u1).put(v3);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y).put(z);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 1).put(z);
							buffer.put(u3).put(v3);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 1).put(z);
							buffer.put(u3).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 1).put(z);
							buffer.put(u3).put(v3);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z);
							return 6;
						case BlockFace.BACK:
							buffer.put(u1).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y).put(z + 1);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z + 1);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z + 1);
							buffer.put(u3).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 1);
							
							buffer.put(u1).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u2).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);
							buffer.put(u2).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u2).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 1).put(z + 0.5f);
							
							return 12;
						case BlockFace.LEFT:
							buffer.put(u1).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y).put(z);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y).put(z + 1);
							buffer.put(u3).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z + 1);
							
							buffer.put(u1).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z);
							buffer.put(u2).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 1).put(z);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 1).put(z);
							buffer.put(u2).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u2).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);
							
							return 12;
						case BlockFace.RIGHT:
							buffer.put(u1).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z);
							buffer.put(u3).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 1);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z + 1);
							
							buffer.put(u1).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 1).put(z);
							buffer.put(u2).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 1).put(z);
							buffer.put(u2).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 1).put(z + 0.5f);
							buffer.put(u2).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);
							
							return 12;
						case BlockFace.TOP:
							buffer.put(u1).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 0.5f).put(z);
							buffer.put(u3).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 0.5f).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z);
							buffer.put(u1).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z);
							buffer.put(u3).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 0.5f).put(z + 1);
							buffer.put(u3).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 1);

							buffer.put(u2).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 1).put(z);
							buffer.put(u3).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);
							buffer.put(u2).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 1).put(z);
							buffer.put(u2).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 1).put(z);
							buffer.put(u3).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 0.5f);
							buffer.put(u3).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 1).put(z + 0.5f);

							buffer.put(u2).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u3).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 1);
							buffer.put(u2).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u2).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);
							buffer.put(u3).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 1);
							buffer.put(u3).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 1);

							return 18;
						case BlockFace.BOTTOM:
							buffer.put(u1).put(v3);
							buffer.put(0.5f).put(0.5f).put(0.5f);
							buffer.put(x).put(y).put(z);
							buffer.put(u1).put(v1);
							buffer.put(0.5f).put(0.5f).put(0.5f);
							buffer.put(x + 1).put(y).put(z);
							buffer.put(u3).put(v3);
							buffer.put(0.5f).put(0.5f).put(0.5f);
							buffer.put(x).put(y).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(0.5f).put(0.5f).put(0.5f);
							buffer.put(x + 1).put(y).put(z);
							buffer.put(u3).put(v1);
							buffer.put(0.5f).put(0.5f).put(0.5f);
							buffer.put(x + 1).put(y).put(z + 1);
							buffer.put(u3).put(v3);
							buffer.put(0.5f).put(0.5f).put(0.5f);
							buffer.put(x).put(y).put(z + 1);

							return 6;
						default:
							return 0;
						}
					} else if (stat != null && stat.getRotateSection() == BlockFace.LEFT
							&& stat.getRotateTo() == BlockFace.BACK) {
						switch (face) {
						case BlockFace.FRONT:
							
						case BlockFace.BACK:
							
						case BlockFace.LEFT:
							
						case BlockFace.RIGHT:

						case BlockFace.TOP:
							
						case BlockFace.BOTTOM:
							
						default:
							return 0;
						}
					} else if (stat != null && stat.getRotateSection() == BlockFace.RIGHT
							&& stat.getRotateTo() == BlockFace.FRONT) {
						switch (face) {
						case BlockFace.FRONT:
							
						case BlockFace.BACK:
							
						case BlockFace.LEFT:
							
						case BlockFace.RIGHT:

						case BlockFace.TOP:
							
						case BlockFace.BOTTOM:
							
						default:
							return 0;
						}
					} else {
						switch (face) {
						case BlockFace.FRONT:
							buffer.put(u1).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y).put(z);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z);
							buffer.put(u3).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z);

							buffer.put(u2).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z);
							buffer.put(u2).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 1).put(z);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z);
							buffer.put(u2).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 1).put(z);
							buffer.put(u3).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 1).put(z);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z);

							return 12;
						case BlockFace.BACK:
							buffer.put(u1).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y).put(z + 1);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z + 1);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z + 1);
							buffer.put(u3).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 1);

							buffer.put(u2).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 0.5f).put(z + 1);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 1);
							buffer.put(u2).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 1).put(z + 1);
							buffer.put(u2).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 1).put(z + 1);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 1);
							buffer.put(u3).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 1);

							return 12;
						case BlockFace.LEFT:
							buffer.put(u1).put(v3);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y).put(z);
							buffer.put(u3).put(v3);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 1).put(z);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 1).put(z);
							buffer.put(u3).put(v3);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y).put(z + 1);
							buffer.put(u3).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x).put(y + 1).put(z + 1);
							return 6;
						case BlockFace.RIGHT:
							buffer.put(u1).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z);
							buffer.put(u3).put(v1);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 1);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 1).put(y).put(z + 1);

							buffer.put(u1).put(v3);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z);
							buffer.put(u1).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 1).put(z);
							buffer.put(u3).put(v3);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 1);
							buffer.put(u1).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 1).put(z);
							buffer.put(u3).put(v2);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 1);
							buffer.put(u3).put(v3);
							buffer.put(0.7f).put(0.7f).put(0.7f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 1);

							return 12;
						case BlockFace.TOP:
							buffer.put(u1).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 1).put(z);
							buffer.put(u3).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 1).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 1).put(z);
							buffer.put(u1).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 1).put(z);
							buffer.put(u3).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x).put(y + 1).put(z + 1);
							buffer.put(u3).put(v1);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 1).put(z + 1);

							buffer.put(u1).put(v3);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z);
							buffer.put(u3).put(v3);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 1);
							buffer.put(u1).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 0.5f).put(z);
							buffer.put(u1).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 0.5f).put(z);
							buffer.put(u3).put(v3);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 0.5f).put(y + 0.5f).put(z + 1);
							buffer.put(u3).put(v2);
							buffer.put(1.0f).put(1.0f).put(1.0f);
							buffer.put(x + 1).put(y + 0.5f).put(z + 1);

							return 12;
						case BlockFace.BOTTOM:
							buffer.put(u1).put(v3);
							buffer.put(0.5f).put(0.5f).put(0.5f);
							buffer.put(x).put(y).put(z);
							buffer.put(u1).put(v1);
							buffer.put(0.5f).put(0.5f).put(0.5f);
							buffer.put(x + 1).put(y).put(z);
							buffer.put(u3).put(v3);
							buffer.put(0.5f).put(0.5f).put(0.5f);
							buffer.put(x).put(y).put(z + 1);
							buffer.put(u1).put(v1);
							buffer.put(0.5f).put(0.5f).put(0.5f);
							buffer.put(x + 1).put(y).put(z);
							buffer.put(u3).put(v1);
							buffer.put(0.5f).put(0.5f).put(0.5f);
							buffer.put(x + 1).put(y).put(z + 1);
							buffer.put(u3).put(v3);
							buffer.put(0.5f).put(0.5f).put(0.5f);
							buffer.put(x).put(y).put(z + 1);

							return 6;
						}
						break;

					}
				}
			} else if (params.slabOrStairData == SlabAndStairData.UP) {
				switch (params.faceTo) {
				case BlockFace.FRONT:
					switch (face) {
					case BlockFace.FRONT:
						buffer.put(u1).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y).put(z + 0.5f);
						buffer.put(u1).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 0.5f).put(z + 0.5f);
						buffer.put(u3).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y).put(z + 0.5f);
						buffer.put(u1).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 0.5f).put(z + 0.5f);
						buffer.put(u3).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);
						buffer.put(u3).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y).put(z + 0.5f);

						buffer.put(u1).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 0.5f).put(z);
						buffer.put(u1).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 1).put(z);
						buffer.put(u3).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y + 0.5f).put(z);
						buffer.put(u1).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 1).put(z);
						buffer.put(u3).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y + 1).put(z);
						buffer.put(u3).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y + 0.5f).put(z);

						return 12;
					case BlockFace.BACK:
						buffer.put(u1).put(v3);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y).put(z + 1);
						buffer.put(u3).put(v3);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y).put(z + 1);
						buffer.put(u1).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 1).put(z + 1);
						buffer.put(u1).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 1).put(z + 1);
						buffer.put(u3).put(v3);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y).put(z + 1);
						buffer.put(u3).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y + 1).put(z + 1);
						return 6;
					case BlockFace.LEFT:
						buffer.put(u1).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 0.5f).put(z);
						buffer.put(u3).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 0.5f).put(z + 1);
						buffer.put(u1).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 1).put(z);
						buffer.put(u1).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 1).put(z);
						buffer.put(u3).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 0.5f).put(z + 1);
						buffer.put(u3).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 1).put(z + 1);

						buffer.put(u1).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y).put(z + 0.5f);
						buffer.put(u2).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y).put(z + 1);
						buffer.put(u1).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 0.5f).put(z + 0.5f);
						buffer.put(u1).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 0.5f).put(z + 0.5f);
						buffer.put(u2).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y).put(z + 1);
						buffer.put(u2).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 0.5f).put(z + 1);

						return 12;
					case BlockFace.RIGHT:
						buffer.put(u1).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y + 0.5f).put(z);
						buffer.put(u1).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y + 1).put(z);
						buffer.put(u3).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y + 0.5f).put(z + 1);
						buffer.put(u1).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y + 1).put(z);
						buffer.put(u3).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y + 1).put(z + 1);
						buffer.put(u3).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y + 0.5f).put(z + 1);

						buffer.put(u1).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y).put(z + 0.5f);
						buffer.put(u1).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);
						buffer.put(u2).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y).put(z + 1);
						buffer.put(u1).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);
						buffer.put(u2).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y + 0.5f).put(z + 1);
						buffer.put(u2).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y).put(z + 1);

						return 12;
					case BlockFace.TOP:
						buffer.put(u1).put(v3);
						buffer.put(1.0f).put(1.0f).put(1.0f);
						buffer.put(x).put(y + 1).put(z);
						buffer.put(u3).put(v3);
						buffer.put(1.0f).put(1.0f).put(1.0f);
						buffer.put(x).put(y + 1).put(z + 1);
						buffer.put(u1).put(v1);
						buffer.put(1.0f).put(1.0f).put(1.0f);
						buffer.put(x + 1).put(y + 1).put(z);
						buffer.put(u1).put(v1);
						buffer.put(1.0f).put(1.0f).put(1.0f);
						buffer.put(x + 1).put(y + 1).put(z);
						buffer.put(u3).put(v3);
						buffer.put(1.0f).put(1.0f).put(1.0f);
						buffer.put(x).put(y + 1).put(z + 1);
						buffer.put(u3).put(v1);
						buffer.put(1.0f).put(1.0f).put(1.0f);
						buffer.put(x + 1).put(y + 1).put(z + 1);
						return 6;
					case BlockFace.BOTTOM:
						buffer.put(u1).put(v3);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x).put(y).put(z + 0.5f);
						buffer.put(u1).put(v1);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x + 1).put(y).put(z + 0.5f);
						buffer.put(u2).put(v3);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x).put(y).put(z + 1);
						buffer.put(u1).put(v1);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x + 1).put(y).put(z + 0.5f);
						buffer.put(u2).put(v1);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x + 1).put(y).put(z + 1);
						buffer.put(u2).put(v3);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x).put(y).put(z + 1);

						buffer.put(u2).put(v3);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x).put(y + 0.5f).put(z);
						buffer.put(u2).put(v1);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x + 1).put(y + 0.5f).put(z);
						buffer.put(u3).put(v3);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x).put(y + 0.5f).put(z + 0.5f);
						buffer.put(u2).put(v1);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x + 1).put(y + 0.5f).put(z);
						buffer.put(u3).put(v1);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);
						buffer.put(u3).put(v3);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x).put(y + 0.5f).put(z + 0.5f);

						return 12;
					}
					break;
				case BlockFace.BACK:
					switch (face) {
					case BlockFace.FRONT:
						buffer.put(u1).put(v3);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y).put(z);
						buffer.put(u1).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 1).put(z);
						buffer.put(u3).put(v3);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y).put(z);
						buffer.put(u1).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 1).put(z);
						buffer.put(u3).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y + 1).put(z);
						buffer.put(u3).put(v3);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y).put(z);
						return 6;
					case BlockFace.BACK:
						buffer.put(u1).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y).put(z + 0.5f);
						buffer.put(u3).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y).put(z + 0.5f);
						buffer.put(u1).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 0.5f).put(z + 0.5f);
						buffer.put(u1).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 0.5f).put(z + 0.5f);
						buffer.put(u3).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y).put(z + 0.5f);
						buffer.put(u3).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);

						buffer.put(u1).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 0.5f).put(z + 1);
						buffer.put(u3).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y + 0.5f).put(z + 1);
						buffer.put(u1).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 1).put(z + 1);
						buffer.put(u1).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 1).put(z + 1);
						buffer.put(u3).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y + 0.5f).put(z + 1);
						buffer.put(u3).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y + 1).put(z + 1);

						return 12;
					case BlockFace.LEFT:
						buffer.put(u1).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 0.5f).put(z);
						buffer.put(u3).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 0.5f).put(z + 1);
						buffer.put(u1).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 1).put(z);
						buffer.put(u1).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 1).put(z);
						buffer.put(u3).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 0.5f).put(z + 1);
						buffer.put(u3).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 1).put(z + 1);

						buffer.put(u1).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y).put(z);
						buffer.put(u2).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y).put(z + 0.5f);
						buffer.put(u1).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 0.5f).put(z);
						buffer.put(u1).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 0.5f).put(z);
						buffer.put(u2).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y).put(z + 0.5f);
						buffer.put(u2).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 0.5f).put(z + 0.5f);

						return 12;
					case BlockFace.RIGHT:
						buffer.put(u1).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y + 0.5f).put(z);
						buffer.put(u1).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y + 1).put(z);
						buffer.put(u3).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y + 0.5f).put(z + 1);
						buffer.put(u1).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y + 1).put(z);
						buffer.put(u3).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y + 1).put(z + 1);
						buffer.put(u3).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y + 0.5f).put(z + 1);

						buffer.put(u1).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y).put(z);
						buffer.put(u1).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y + 0.5f).put(z);
						buffer.put(u2).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y).put(z + 0.5f);
						buffer.put(u1).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y + 0.5f).put(z);
						buffer.put(u2).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);
						buffer.put(u2).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y).put(z + 0.5f);

						return 12;
					case BlockFace.TOP:
						buffer.put(u1).put(v3);
						buffer.put(1.0f).put(1.0f).put(1.0f);
						buffer.put(x).put(y + 1).put(z);
						buffer.put(u3).put(v3);
						buffer.put(1.0f).put(1.0f).put(1.0f);
						buffer.put(x).put(y + 1).put(z + 1);
						buffer.put(u1).put(v1);
						buffer.put(1.0f).put(1.0f).put(1.0f);
						buffer.put(x + 1).put(y + 1).put(z);
						buffer.put(u1).put(v1);
						buffer.put(1.0f).put(1.0f).put(1.0f);
						buffer.put(x + 1).put(y + 1).put(z);
						buffer.put(u3).put(v3);
						buffer.put(1.0f).put(1.0f).put(1.0f);
						buffer.put(x).put(y + 1).put(z + 1);
						buffer.put(u3).put(v1);
						buffer.put(1.0f).put(1.0f).put(1.0f);
						buffer.put(x + 1).put(y + 1).put(z + 1);
						return 6;
					case BlockFace.BOTTOM:
						buffer.put(u1).put(v3);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x).put(y + 0.5f).put(z + 0.5f);
						buffer.put(u1).put(v1);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);
						buffer.put(u2).put(v3);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x).put(y + 0.5f).put(z + 1);
						buffer.put(u1).put(v1);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x + 1).put(y + 0.5f).put(z + 0.5f);
						buffer.put(u2).put(v1);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x + 1).put(y + 0.5f).put(z + 1);
						buffer.put(u2).put(v3);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x).put(y + 0.5f).put(z + 1);

						buffer.put(u2).put(v3);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x).put(y).put(z);
						buffer.put(u2).put(v1);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x + 1).put(y).put(z);
						buffer.put(u3).put(v3);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x).put(y).put(z + 0.5f);
						buffer.put(u2).put(v1);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x + 1).put(y).put(z);
						buffer.put(u3).put(v1);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x + 1).put(y).put(z + 0.5f);
						buffer.put(u3).put(v3);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x).put(y).put(z + 0.5f);

						return 12;
					}
					break;
				case BlockFace.LEFT:
					switch (face) {
					case BlockFace.FRONT:
						buffer.put(u1).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 0.5f).put(z);
						buffer.put(u1).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 1).put(z);
						buffer.put(u3).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y + 0.5f).put(z);
						buffer.put(u1).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 1).put(z);
						buffer.put(u3).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y + 1).put(z);
						buffer.put(u3).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y + 0.5f).put(z);

						buffer.put(u2).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 0.5f).put(y).put(z);
						buffer.put(u2).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 0.5f).put(y + 0.5f).put(z);
						buffer.put(u3).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y).put(z);
						buffer.put(u2).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 0.5f).put(y + 0.5f).put(z);
						buffer.put(u3).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y + 0.5f).put(z);
						buffer.put(u3).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y).put(z);

						return 12;
					case BlockFace.BACK:
						buffer.put(u1).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 0.5f).put(z + 1);
						buffer.put(u3).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y + 0.5f).put(z + 1);
						buffer.put(u1).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 1).put(z + 1);
						buffer.put(u1).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 1).put(z + 1);
						buffer.put(u3).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y + 0.5f).put(z + 1);
						buffer.put(u3).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y + 1).put(z + 1);

						buffer.put(u2).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 0.5f).put(y).put(z + 1);
						buffer.put(u3).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y).put(z + 1);
						buffer.put(u2).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 0.5f).put(y + 0.5f).put(z + 1);
						buffer.put(u2).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 0.5f).put(y + 0.5f).put(z + 1);
						buffer.put(u3).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y).put(z + 1);
						buffer.put(u3).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y + 0.5f).put(z + 1);

						return 12;
					case BlockFace.LEFT:
						buffer.put(u1).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 0.5f).put(y).put(z);
						buffer.put(u3).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 0.5f).put(y).put(z + 1);
						buffer.put(u1).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 0.5f).put(y + 0.5f).put(z);
						buffer.put(u1).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 0.5f).put(y + 0.5f).put(z);
						buffer.put(u3).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 0.5f).put(y).put(z + 1);
						buffer.put(u3).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 0.5f).put(y + 0.5f).put(z + 1);

						buffer.put(u3).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 0.5f).put(z);
						buffer.put(u1).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 0.5f).put(z + 1);
						buffer.put(u3).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 1).put(z);
						buffer.put(u3).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 1).put(z);
						buffer.put(u1).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 0.5f).put(z + 1);
						buffer.put(u1).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 1).put(z + 1);

						return 12;
					case BlockFace.RIGHT:
						buffer.put(u1).put(v3);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y).put(z);
						buffer.put(u1).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y + 1).put(z);
						buffer.put(u3).put(v3);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y).put(z + 1);
						buffer.put(u1).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y + 1).put(z);
						buffer.put(u3).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y + 1).put(z + 1);
						buffer.put(u3).put(v3);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y).put(z + 1);
						return 6;
					case BlockFace.TOP:
						buffer.put(u1).put(v3);
						buffer.put(1.0f).put(1.0f).put(1.0f);
						buffer.put(x).put(y + 1).put(z);
						buffer.put(u3).put(v3);
						buffer.put(1.0f).put(1.0f).put(1.0f);
						buffer.put(x).put(y + 1).put(z + 1);
						buffer.put(u1).put(v1);
						buffer.put(1.0f).put(1.0f).put(1.0f);
						buffer.put(x + 1).put(y + 1).put(z);
						buffer.put(u1).put(v1);
						buffer.put(1.0f).put(1.0f).put(1.0f);
						buffer.put(x + 1).put(y + 1).put(z);
						buffer.put(u3).put(v3);
						buffer.put(1.0f).put(1.0f).put(1.0f);
						buffer.put(x).put(y + 1).put(z + 1);
						buffer.put(u3).put(v1);
						buffer.put(1.0f).put(1.0f).put(1.0f);
						buffer.put(x + 1).put(y + 1).put(z + 1);
						return 6;
					case BlockFace.BOTTOM:
						buffer.put(u1).put(v1);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x).put(y + 0.5f).put(z);
						buffer.put(u1).put(v2);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x + 0.5f).put(y + 0.5f).put(z);
						buffer.put(u3).put(v1);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x).put(y + 0.5f).put(z + 1);
						buffer.put(u1).put(v2);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x + 0.5f).put(y + 0.5f).put(z);
						buffer.put(u3).put(v2);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x + 0.5f).put(y + 0.5f).put(z + 1);
						buffer.put(u3).put(v1);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x).put(y + 0.5f).put(z + 1);

						buffer.put(u1).put(v2);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x + 0.5f).put(y).put(z);
						buffer.put(u1).put(v3);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x + 1).put(y).put(z);
						buffer.put(u3).put(v2);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x + 0.5f).put(y).put(z + 1);
						buffer.put(u1).put(v3);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x + 1).put(y).put(z);
						buffer.put(u3).put(v3);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x + 1).put(y).put(z + 1);
						buffer.put(u3).put(v2);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x + 0.5f).put(y).put(z + 1);

						return 12;
					}
					break;
				case BlockFace.RIGHT:
					switch (face) {
					case BlockFace.FRONT:
						buffer.put(u1).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 0.5f).put(z);
						buffer.put(u1).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 1).put(z);
						buffer.put(u3).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y + 0.5f).put(z);
						buffer.put(u1).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 1).put(z);
						buffer.put(u3).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y + 1).put(z);
						buffer.put(u3).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y + 0.5f).put(z);

						buffer.put(u2).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y).put(z);
						buffer.put(u2).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 0.5f).put(z);
						buffer.put(u3).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 0.5f).put(y).put(z);
						buffer.put(u2).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 0.5f).put(z);
						buffer.put(u3).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 0.5f).put(y + 0.5f).put(z);
						buffer.put(u3).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 0.5f).put(y).put(z);

						return 12;
					case BlockFace.BACK:
						buffer.put(u1).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 0.5f).put(z + 1);
						buffer.put(u3).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y + 0.5f).put(z + 1);
						buffer.put(u1).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 1).put(z + 1);
						buffer.put(u1).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 1).put(z + 1);
						buffer.put(u3).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y + 0.5f).put(z + 1);
						buffer.put(u3).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y + 1).put(z + 1);

						buffer.put(u2).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y).put(z + 1);
						buffer.put(u3).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 0.5f).put(y).put(z + 1);
						buffer.put(u2).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 0.5f).put(z + 1);
						buffer.put(u2).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 0.5f).put(z + 1);
						buffer.put(u3).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 0.5f).put(y).put(z + 1);
						buffer.put(u3).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 0.5f).put(y + 0.5f).put(z + 1);

						return 12;
					case BlockFace.LEFT:
						buffer.put(u1).put(v3);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y).put(z);
						buffer.put(u3).put(v3);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y).put(z + 1);
						buffer.put(u1).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 1).put(z);
						buffer.put(u1).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 1).put(z);
						buffer.put(u3).put(v3);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y).put(z + 1);
						buffer.put(u3).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x).put(y + 1).put(z + 1);
						return 6;
					case BlockFace.RIGHT:
						buffer.put(u1).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 0.5f).put(y).put(z);
						buffer.put(u1).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 0.5f).put(y + 0.5f).put(z);
						buffer.put(u3).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 0.5f).put(y).put(z + 1);
						buffer.put(u1).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 0.5f).put(y + 0.5f).put(z);
						buffer.put(u3).put(v1);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 0.5f).put(y + 0.5f).put(z + 1);
						buffer.put(u3).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 0.5f).put(y).put(z + 1);

						buffer.put(u1).put(v3);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y + 0.5f).put(z);
						buffer.put(u1).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y + 1).put(z);
						buffer.put(u3).put(v3);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y + 0.5f).put(z + 1);
						buffer.put(u1).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y + 1).put(z);
						buffer.put(u3).put(v2);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y + 1).put(z + 1);
						buffer.put(u3).put(v3);
						buffer.put(0.7f).put(0.7f).put(0.7f);
						buffer.put(x + 1).put(y + 0.5f).put(z + 1);

						return 12;
					case BlockFace.TOP:
						buffer.put(u1).put(v3);
						buffer.put(1.0f).put(1.0f).put(1.0f);
						buffer.put(x).put(y + 1).put(z);
						buffer.put(u3).put(v3);
						buffer.put(1.0f).put(1.0f).put(1.0f);
						buffer.put(x).put(y + 1).put(z + 1);
						buffer.put(u1).put(v1);
						buffer.put(1.0f).put(1.0f).put(1.0f);
						buffer.put(x + 1).put(y + 1).put(z);
						buffer.put(u1).put(v1);
						buffer.put(1.0f).put(1.0f).put(1.0f);
						buffer.put(x + 1).put(y + 1).put(z);
						buffer.put(u3).put(v3);
						buffer.put(1.0f).put(1.0f).put(1.0f);
						buffer.put(x).put(y + 1).put(z + 1);
						buffer.put(u3).put(v1);
						buffer.put(1.0f).put(1.0f).put(1.0f);
						buffer.put(x + 1).put(y + 1).put(z + 1);
						return 6;
					case BlockFace.BOTTOM:
						buffer.put(u1).put(v2);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x).put(y).put(z);
						buffer.put(u1).put(v1);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x + 0.5f).put(y).put(z);
						buffer.put(u3).put(v2);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x).put(y).put(z + 1);
						buffer.put(u1).put(v1);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x + 0.5f).put(y).put(z);
						buffer.put(u3).put(v1);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x + 0.5f).put(y).put(z + 1);
						buffer.put(u3).put(v2);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x).put(y).put(z + 1);

						buffer.put(u1).put(v3);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x + 0.5f).put(y + 0.5f).put(z);
						buffer.put(u1).put(v2);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x + 1).put(y + 0.5f).put(z);
						buffer.put(u3).put(v3);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x + 0.5f).put(y + 0.5f).put(z + 1);
						buffer.put(u1).put(v2);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x + 1).put(y + 0.5f).put(z);
						buffer.put(u3).put(v2);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x + 1).put(y + 0.5f).put(z + 1);
						buffer.put(u3).put(v3);
						buffer.put(0.5f).put(0.5f).put(0.5f);
						buffer.put(x + 0.5f).put(y + 0.5f).put(z + 1);

						return 12;
					}
					break;

				}

			}
		}

		return 0;
	}

	@Override
	public boolean isAlpha(World world) {
		return false;
	}

	@Override
	public boolean isCompleteBlock(World world) {
		return false;
	}

	@Override
	public ArrayList<AABB> getAabbs(BlockParameters params, World world, int x, int y, int z) {
		ArrayList<AABB> aabbs = new ArrayList<>();

		if (params.slabOrStairData == SlabAndStairData.DOWN) {
			aabbs.add(new AABB(0, 1, 0, 0.5f, 0, 1));
			switch (params.faceTo) {
			case BlockFace.FRONT:
				aabbs.add(new AABB(0, 1, 0.5f, 1, 0.5f, 1));
				break;
			case BlockFace.BACK:
				aabbs.add(new AABB(0, 1, 0.5f, 1, 0, 0.5f));
				break;
			case BlockFace.LEFT:
				aabbs.add(new AABB(0.5f, 1, 0.5f, 1, 0, 1));
				break;
			case BlockFace.RIGHT:
				aabbs.add(new AABB(0, 0.5f, 0.5f, 1, 0, 1));
				break;
			}
		} else if (params.slabOrStairData == SlabAndStairData.UP) {
			aabbs.add(new AABB(0, 1, 0.5f, 1, 0, 1));
			switch (params.faceTo) {
			case BlockFace.FRONT:
				aabbs.add(new AABB(0, 1, 0, 0.5f, 0.5f, 1));
				break;
			case BlockFace.BACK:
				aabbs.add(new AABB(0, 1, 0, 0.5f, 0, 0.5f));
				break;
			case BlockFace.LEFT:
				aabbs.add(new AABB(0.5f, 1, 0, 0.5f, 0, 1));
				break;
			case BlockFace.RIGHT:
				aabbs.add(new AABB(0, 0.5f, 0, 0.5f, 0, 1));
				break;
			}
		}

		return aabbs;
	}

	/**
	 * 获取这个台阶方块的转向
	 * 
	 * @param currentBlockFaceTo    当前台阶方块的朝向，食用BlockFace类里面定义的一个参数
	 * @param currentBlockStairData 当前台阶方块的SlabAndStairData，食用SlabAndStairData类里面定义的一个参数
	 * @param world                 当前世界实例
	 * @param x                     当前方块的x坐标
	 * @param y                     当前方块的y坐标
	 * @param z                     当前方块的z坐标
	 * @return 旋转参数
	 */
	private RotateStat getRotateStat(byte currentBlockFaceTo, byte currentBlockStairData, World world, int x, int y,
			int z) {
		switch (currentBlockFaceTo) {
		case BlockFace.FRONT:
		case BlockFace.BACK:
			a: if (world.hasBlock(new Vector3i(x, y, z - 1))) {
				Tile tile = world.getBlock(x, y, z - 1);
				if (!tile.getData().getNamespace().endsWith(".stair"))
					break a;
				if (tile.getParams().slabOrStairData != currentBlockStairData)
					break a;
				if (tile.getParams().faceTo == BlockFace.LEFT)
					return new RotateStat(BlockFace.LEFT, BlockFace.LEFT);
				else if (tile.getParams().faceTo == BlockFace.RIGHT)
					return new RotateStat(BlockFace.RIGHT, BlockFace.RIGHT);
				else
					break a;
			}
			b: if (world.hasBlock(new Vector3i(x, y, z + 1))) {
				Tile tile = world.getBlock(x, y, z + 1);
				if (!tile.getData().getNamespace().endsWith(".stair"))
					break b;
				if (tile.getParams().slabOrStairData != currentBlockStairData)
					break b;
				if (tile.getParams().faceTo == BlockFace.LEFT)
					return new RotateStat(BlockFace.RIGHT, BlockFace.LEFT);
				else if (tile.getParams().faceTo == BlockFace.RIGHT)
					return new RotateStat(BlockFace.LEFT, BlockFace.RIGHT);
				else
					break b;
			}
			break;
		case BlockFace.LEFT:
		case BlockFace.RIGHT:
			c: if (world.hasBlock(new Vector3i(x + 1, y, z))) {
				Tile tile = world.getBlock(x + 1, y, z);
				if (!tile.getData().getNamespace().endsWith(".stair"))
					break c;
				if (tile.getParams().slabOrStairData != currentBlockStairData)
					break c;
				if (tile.getParams().faceTo == BlockFace.FRONT)
					return new RotateStat(BlockFace.LEFT, BlockFace.FRONT);
				else if (tile.getParams().faceTo == BlockFace.BACK)
					return new RotateStat(BlockFace.RIGHT, BlockFace.BACK);
				else
					break c;
			}
			d: if (world.hasBlock(new Vector3i(x - 1, y, z))) {
				Tile tile = world.getBlock(x - 1, y, z);
				if (!tile.getData().getNamespace().endsWith(".stair"))
					break d;
				if (tile.getParams().slabOrStairData != currentBlockStairData)
					break d;
				if (tile.getParams().faceTo == BlockFace.FRONT)
					return new RotateStat(BlockFace.RIGHT, BlockFace.FRONT);
				else if (tile.getParams().faceTo == BlockFace.BACK)
					return new RotateStat(BlockFace.LEFT, BlockFace.BACK);
				else
					break d;
			}
			break;
		}
		return null;
	}

	/**
	 * 台阶的旋转参数
	 */
	private static class RotateStat {

		// 从楼梯的前面看 偏转部分如果是左边就是BlockFace.LEFT, 如果是右边就是BlockFace.RIGHT
		private byte rotateSection;
		// 楼梯偏转将要转到的面 食用BlockFace里面的参数
		private byte rotateTo;

		public RotateStat(byte rotateSection, byte rotateTo) {
			this.rotateSection = rotateSection;
			this.rotateTo = rotateTo;
		}

		public byte getRotateSection() {
			return rotateSection;
		}

		public byte getRotateTo() {
			return rotateTo;
		}

		@Override
		public String toString() {
			return "RotateStat [rotateSection=" + rotateSection + ", rotateTo=" + rotateTo + "]";
		}

	}

}
