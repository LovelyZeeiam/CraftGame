package xueli.craftgame.block.rendercontrol.model;

import java.util.ArrayList;

import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.google.gson.JsonObject;

import xueli.craftgame.block.BlockData;
import xueli.craftgame.block.BlockParameters;
import xueli.craftgame.block.data.BlockFace;
import xueli.craftgame.world.Chunk;
import xueli.craftgame.world.World;
import xueli.gamengine.physics.AABB;
import xueli.gamengine.resource.TextureAtlas;
import xueli.gamengine.utils.store.FloatList;
import xueli.gamengine.utils.vector.Vector2s;

public abstract class IModel {

	// TODO: Plain World and stand on 27,6,43 then world rendering will make its
	// problem appear

	private static ArrayList<AABB> aabbs = new ArrayList<>();

	static {
		aabbs.add(new AABB(0, 1, 0, 1, 0, 1));

	}

	public IModel(JsonObject renderArgs) {

	}

	/**
	 * @return 绘制的有多少个面
	 */
	public abstract int getRenderCubeData(FloatList buffer, BlockData data, int x, int y, int z, byte face,
			TextureAtlas blockTextureAtlas, BlockParameters params, Chunk chunk, World world);

	public boolean isAlpha(World world) {
		return false;
	}

	public boolean isCompleteBlock(World world) {
		return true;
	}

	public ArrayList<AABB> getAabbs(BlockParameters params, World world, int x, int y, int z) {
		return aabbs;
	}

	protected int getRenderCubeData(FloatList buffer, BlockData data, int x, int y, int z, byte face,
			TextureAtlas blockTextureAtlas, Chunk chunk, World world, NVGColor multiplyColor) {
		Vector2s textureVector2s = data.getTextures()[face];

		float u1 = (float) textureVector2s.x / blockTextureAtlas.width;
		float v1 = (float) textureVector2s.y / blockTextureAtlas.height;
		float u2 = (float) (textureVector2s.x + 1) / blockTextureAtlas.width;
		float v2 = (float) (textureVector2s.y + 1) / blockTextureAtlas.height;

		float r = multiplyColor.r();
		float g = multiplyColor.g();
		float b = multiplyColor.b();

		switch (face) {
		case BlockFace.FRONT:
			drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y, z), new Vector2f(u1, v2),
					new Vector3f(0.7f * r, 0.7f * g, 0.7f * b), new Vector3f(x + 1, y, z), new Vector2f(u2, v2),
					new Vector3f(0.7f * r, 0.7f * g, 0.7f * b), new Vector3f(x, y + 1, z), new Vector2f(u1, v1),
					new Vector3f(0.7f * r, 0.7f * g, 0.7f * b), new Vector3f(x + 1, y + 1, z), new Vector2f(u2, v1),
					new Vector3f(0.7f * r, 0.7f * g, 0.7f * b));
			break;
		case BlockFace.RIGHT:
			drawQuadFacingBackOrRight(buffer, new Vector3f(x + 1, y, z), new Vector2f(u2, v2),
					new Vector3f(0.6f * r, 0.6f * g, 0.6f * b), new Vector3f(x + 1, y + 1, z), new Vector2f(u2, v1),
					new Vector3f(0.6f * r, 0.6f * g, 0.6f * b), new Vector3f(x + 1, y, z + 1), new Vector2f(u1, v2),
					new Vector3f(0.6f * r, 0.6f * g, 0.6f * b), new Vector3f(x + 1, y + 1, z + 1), new Vector2f(u1, v1),
					new Vector3f(0.6f * r, 0.6f * g, 0.6f * b));
			break;
		case BlockFace.BACK:
			drawQuadFacingBackOrRight(buffer, new Vector3f(x, y, z + 1), new Vector2f(u1, v2),
					new Vector3f(0.5f * r, 0.5f * g, 0.5f * b), new Vector3f(x + 1, y, z + 1), new Vector2f(u2, v2),
					new Vector3f(0.5f * r, 0.5f * g, 0.5f * b), new Vector3f(x, y + 1, z + 1), new Vector2f(u1, v1),
					new Vector3f(0.5f * r, 0.5f * g, 0.5f * b), new Vector3f(x + 1, y + 1, z + 1), new Vector2f(u2, v1),
					new Vector3f(0.5f * r, 0.5f * g, 0.5f * b));
			break;
		case BlockFace.LEFT:
			drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y, z), new Vector2f(u1, v2),
					new Vector3f(0.6f * r, 0.6f * g, 0.6f * b), new Vector3f(x, y + 1, z), new Vector2f(u1, v1),
					new Vector3f(0.6f * r, 0.6f * g, 0.6f * b), new Vector3f(x, y, z + 1), new Vector2f(u2, v2),
					new Vector3f(0.6f * r, 0.6f * g, 0.6f * b), new Vector3f(x, y + 1, z + 1), new Vector2f(u2, v1),
					new Vector3f(0.6f * r, 0.6f * g, 0.6f * b));
			break;
		case BlockFace.TOP:
			drawQuadFacingTop(buffer, new Vector3f(x, y + 1, z), new Vector2f(u1, v2),
					new Vector3f(1.0f * r, 1.0f * g, 1.0f * b), new Vector3f(x + 1, y + 1, z), new Vector2f(u1, v1),
					new Vector3f(1.0f * r, 1.0f * g, 1.0f * b), new Vector3f(x, y + 1, z + 1), new Vector2f(u2, v2),
					new Vector3f(1.0f * r, 1.0f * g, 1.0f * b), new Vector3f(x + 1, y + 1, z + 1), new Vector2f(u2, v1),
					new Vector3f(1.0f * r, 1.0f * g, 1.0f * b));
			break;
		case BlockFace.BOTTOM:
			drawQuadFacingBottom(buffer, new Vector3f(x, y, z), new Vector2f(u1, v2),
					new Vector3f(0.4f * r, 0.4f * g, 0.4f * b), new Vector3f(x + 1, y, z), new Vector2f(u1, v1),
					new Vector3f(0.4f * r, 0.4f * g, 0.4f * b), new Vector3f(x, y, z + 1), new Vector2f(u2, v2),
					new Vector3f(0.4f * r, 0.4f * g, 0.4f * b), new Vector3f(x + 1, y, z + 1), new Vector2f(u2, v1),
					new Vector3f(0.4f * r, 0.4f * g, 0.4f * b));
			break;
		}

		return 6;
	}

	/**
	 * The following method should be always given vertex from little to big for
	 * instance: ^ - *-------------* --v3-------v4-- --------------- ---------------
	 * --------------- --v1-------v2-- *-------------*-->
	 */

	/**
	 * Draw Back and Right Face
	 */
	protected int drawQuadFacingBackOrRight(FloatList buffer, Vector3f v1, Vector2f t1, Vector3f c1, Vector3f v2,
			Vector2f t2, Vector3f c2, Vector3f v3, Vector2f t3, Vector3f c3, Vector3f v4, Vector2f t4, Vector3f c4) {
		buffer.put(t1).put(c1).put(v1);
		buffer.put(t2).put(c2).put(v2);
		buffer.put(t3).put(c3).put(v3);
		buffer.put(t3).put(c3).put(v3);
		buffer.put(t2).put(c2).put(v2);
		buffer.put(t4).put(c4).put(v4);
		return 6;
	}

	/**
	 * Draw Front and Left Face
	 */
	protected int drawQuadFacingFrontOrLeft(FloatList buffer, Vector3f v1, Vector2f t1, Vector3f c1, Vector3f v2,
			Vector2f t2, Vector3f c2, Vector3f v3, Vector2f t3, Vector3f c3, Vector3f v4, Vector2f t4, Vector3f c4) {
		buffer.put(t4).put(c4).put(v4);
		buffer.put(t2).put(c2).put(v2);
		buffer.put(t1).put(c1).put(v1);
		buffer.put(t3).put(c3).put(v3);
		buffer.put(t4).put(c4).put(v4);
		buffer.put(t1).put(c1).put(v1);
		return 6;
	}

	/**
	 * Draw Bottom Face
	 */
	protected int drawQuadFacingTop(FloatList buffer, Vector3f v1, Vector2f t1, Vector3f c1, Vector3f v2, Vector2f t2,
			Vector3f c2, Vector3f v3, Vector2f t3, Vector3f c3, Vector3f v4, Vector2f t4, Vector3f c4) {
		buffer.put(t1).put(c1).put(v1);
		buffer.put(t3).put(c3).put(v3);
		buffer.put(t2).put(c2).put(v2);
		buffer.put(t2).put(c2).put(v2);
		buffer.put(t3).put(c3).put(v3);
		buffer.put(t4).put(c4).put(v4);
		return 6;
	}

	/**
	 * Draw Top Face
	 */
	protected int drawQuadFacingBottom(FloatList buffer, Vector3f v1, Vector2f t1, Vector3f c1, Vector3f v2,
			Vector2f t2, Vector3f c2, Vector3f v3, Vector2f t3, Vector3f c3, Vector3f v4, Vector2f t4, Vector3f c4) {
		buffer.put(t1).put(c1).put(v1);
		buffer.put(t2).put(c2).put(v2);
		buffer.put(t3).put(c3).put(v3);
		buffer.put(t2).put(c2).put(v2);
		buffer.put(t4).put(c4).put(v4);
		buffer.put(t3).put(c3).put(v3);
		return 6;
	}

}
