package xueli.craftgame.renderer;

import org.lwjgl.utils.vector.Vector2f;
import org.lwjgl.utils.vector.Vector3f;
import org.lwjgl.utils.vector.Vector4f;

import xueli.craftgame.block.BlockFace;
import xueli.game.utils.FloatList;
import xueli.game.utils.WrappedFloatBuffer;

public class CubeDrawer {

	private CubeDrawer() {
	}

	/**
	 * The following method should be always given vertex from little to big for
	 * instance: ^ - *-------------* --v3-------v4-- --------------- ---------------
	 * --------------- --v1-------v2-- *-------------*-->
	 */

	public static int drawQuadFacingRight(FloatList buffer, Vector3f v1, Vector2f u1, Vector4f c1, Vector3f v2,
			Vector2f u2, Vector4f c2, Vector3f v3, Vector2f u3, Vector4f c3, Vector3f v4, Vector2f u4, Vector4f c4) {
		// 1,2,3,3,2,4
		buffer.put(v1).put(u1).put(c1);
		buffer.put(v2).put(u2).put(c2);
		buffer.put(v3).put(u3).put(c3);
		buffer.put(v3).put(u3).put(c3);
		buffer.put(v2).put(u2).put(c2);
		buffer.put(v4).put(u4).put(c4);
		return 6;
	}

	public static int drawQuadFacingBack(FloatList buffer, Vector3f v1, Vector2f u1, Vector4f c1, Vector3f v2,
			Vector2f u2, Vector4f c2, Vector3f v3, Vector2f u3, Vector4f c3, Vector3f v4, Vector2f u4, Vector4f c4) {
		// 1,2,3,3,2,4
		buffer.put(v1).put(u1).put(c1);
		buffer.put(v2).put(u2).put(c2);
		buffer.put(v3).put(u3).put(c3);
		buffer.put(v3).put(u3).put(c3);
		buffer.put(v2).put(u2).put(c2);
		buffer.put(v4).put(u4).put(c4);
		return 6;
	}

	public static int drawQuadFacingFront(FloatList buffer, Vector3f v1, Vector2f u1, Vector4f c1, Vector3f v2,
			Vector2f u2, Vector4f c2, Vector3f v3, Vector2f u3, Vector4f c3, Vector3f v4, Vector2f u4, Vector4f c4) {
		// 4,2,1,3,4,1
		buffer.put(v4).put(u4).put(c4);
		buffer.put(v2).put(u2).put(c2);
		buffer.put(v1).put(u1).put(c1);
		buffer.put(v3).put(u3).put(c3);
		buffer.put(v4).put(u4).put(c4);
		buffer.put(v1).put(u1).put(c1);
		return 6;
	}

	public static int drawQuadFacingLeft(FloatList buffer, Vector3f v1, Vector2f u1, Vector4f c1, Vector3f v2,
			Vector2f u2, Vector4f c2, Vector3f v3, Vector2f u3, Vector4f c3, Vector3f v4, Vector2f u4, Vector4f c4) {
		// 4,2,1,3,4,1
		buffer.put(v4).put(u4).put(c4);
		buffer.put(v2).put(u2).put(c2);
		buffer.put(v1).put(u1).put(c1);
		buffer.put(v3).put(u3).put(c3);
		buffer.put(v4).put(u4).put(c4);
		buffer.put(v1).put(u1).put(c1);
		return 6;
	}

	public static int drawQuadFacingTop(FloatList buffer, Vector3f v1, Vector2f u1, Vector4f c1, Vector3f v2,
			Vector2f u2, Vector4f c2, Vector3f v3, Vector2f u3, Vector4f c3, Vector3f v4, Vector2f u4, Vector4f c4) {
		// 1,3,2,2,3,4
		buffer.put(v1).put(u1).put(c1);
		buffer.put(v3).put(u3).put(c3);
		buffer.put(v2).put(u2).put(c2);
		buffer.put(v2).put(u2).put(c2);
		buffer.put(v3).put(u3).put(c3);
		buffer.put(v4).put(u4).put(c4);
		return 6;
	}

	public static int drawQuadFacingBottom(FloatList buffer, Vector3f v1, Vector2f u1, Vector4f c1, Vector3f v2,
			Vector2f u2, Vector4f c2, Vector3f v3, Vector2f u3, Vector4f c3, Vector3f v4, Vector2f u4, Vector4f c4) {
		// 1,2,3,2,4,3
		buffer.put(v1).put(u1).put(c1);
		buffer.put(v2).put(u2).put(c2);
		buffer.put(v3).put(u3).put(c3);
		buffer.put(v2).put(u2).put(c2);
		buffer.put(v4).put(u4).put(c4);
		buffer.put(v3).put(u3).put(c3);
		return 6;
	}

	public static int drawQuad(FloatList buffer, Vector3f n, Vector3f v1, Vector2f u1, Vector4f c1, Vector3f v2,
			Vector2f u2, Vector4f c2, Vector3f v3, Vector2f u3, Vector4f c3, Vector3f v4, Vector2f u4, Vector4f c4) {
		// 1,2,3,2,4,3
		buffer.put(v1).put(u1).put(c1);
		buffer.put(v2).put(u2).put(c2);
		buffer.put(v3).put(u3).put(c3);
		buffer.put(v2).put(u2).put(c2);
		buffer.put(v4).put(u4).put(c4);
		buffer.put(v3).put(u3).put(c3);
		return 6;
	}

	public static int drawQuadFacingRight(WrappedFloatBuffer buffer, Vector3f v1, Vector2f u1, Vector4f c1, Vector3f v2,
			Vector2f u2, Vector4f c2, Vector3f v3, Vector2f u3, Vector4f c3, Vector3f v4, Vector2f u4, Vector4f c4) {
		// 1,2,3,3,2,4
		buffer.put(v1).put(u1).put(c1);
		buffer.put(v2).put(u2).put(c2);
		buffer.put(v3).put(u3).put(c3);
		buffer.put(v3).put(u3).put(c3);
		buffer.put(v2).put(u2).put(c2);
		buffer.put(v4).put(u4).put(c4);
		return 6;
	}

	public static int drawQuadFacingBack(WrappedFloatBuffer buffer, Vector3f v1, Vector2f u1, Vector4f c1, Vector3f v2,
			Vector2f u2, Vector4f c2, Vector3f v3, Vector2f u3, Vector4f c3, Vector3f v4, Vector2f u4, Vector4f c4) {
		// 1,2,3,3,2,4
		buffer.put(v1).put(u1).put(c1);
		buffer.put(v2).put(u2).put(c2);
		buffer.put(v3).put(u3).put(c3);
		buffer.put(v3).put(u3).put(c3);
		buffer.put(v2).put(u2).put(c2);
		buffer.put(v4).put(u4).put(c4);
		return 6;
	}

	public static int drawQuadFacingFront(WrappedFloatBuffer buffer, Vector3f v1, Vector2f u1, Vector4f c1, Vector3f v2,
			Vector2f u2, Vector4f c2, Vector3f v3, Vector2f u3, Vector4f c3, Vector3f v4, Vector2f u4, Vector4f c4) {
		// 4,2,1,3,4,1
		buffer.put(v4).put(u4).put(c4);
		buffer.put(v2).put(u2).put(c2);
		buffer.put(v1).put(u1).put(c1);
		buffer.put(v3).put(u3).put(c3);
		buffer.put(v4).put(u4).put(c4);
		buffer.put(v1).put(u1).put(c1);
		return 6;
	}

	public static int drawQuadFacingLeft(WrappedFloatBuffer buffer, Vector3f v1, Vector2f u1, Vector4f c1, Vector3f v2,
			Vector2f u2, Vector4f c2, Vector3f v3, Vector2f u3, Vector4f c3, Vector3f v4, Vector2f u4, Vector4f c4) {
		// 4,2,1,3,4,1
		buffer.put(v4).put(u4).put(c4);
		buffer.put(v2).put(u2).put(c2);
		buffer.put(v1).put(u1).put(c1);
		buffer.put(v3).put(u3).put(c3);
		buffer.put(v4).put(u4).put(c4);
		buffer.put(v1).put(u1).put(c1);
		return 6;
	}

	public static int drawQuadFacingTop(WrappedFloatBuffer buffer, Vector3f v1, Vector2f u1, Vector4f c1, Vector3f v2,
			Vector2f u2, Vector4f c2, Vector3f v3, Vector2f u3, Vector4f c3, Vector3f v4, Vector2f u4, Vector4f c4) {
		// 1,3,2,2,3,4
		buffer.put(v1).put(u1).put(c1);
		buffer.put(v3).put(u3).put(c3);
		buffer.put(v2).put(u2).put(c2);
		buffer.put(v2).put(u2).put(c2);
		buffer.put(v3).put(u3).put(c3);
		buffer.put(v4).put(u4).put(c4);
		return 6;
	}

	public static int drawQuadFacingBottom(WrappedFloatBuffer buffer, Vector3f v1, Vector2f u1, Vector4f c1,
			Vector3f v2, Vector2f u2, Vector4f c2, Vector3f v3, Vector2f u3, Vector4f c3, Vector3f v4, Vector2f u4,
			Vector4f c4) {
		// 1,2,3,2,4,3
		buffer.put(v1).put(u1).put(c1);
		buffer.put(v2).put(u2).put(c2);
		buffer.put(v3).put(u3).put(c3);
		buffer.put(v2).put(u2).put(c2);
		buffer.put(v4).put(u4).put(c4);
		buffer.put(v3).put(u3).put(c3);
		return 6;
	}

	public static int drawQuad(WrappedFloatBuffer buffer, Vector3f n, Vector3f v1, Vector2f u1, Vector4f c1,
			Vector3f v2, Vector2f u2, Vector4f c2, Vector3f v3, Vector2f u3, Vector4f c3, Vector3f v4, Vector2f u4,
			Vector4f c4) {
		// 1,2,3,2,4,3
		buffer.put(v1).put(u1).put(c1);
		buffer.put(v2).put(u2).put(c2);
		buffer.put(v3).put(u3).put(c3);
		buffer.put(v2).put(u2).put(c2);
		buffer.put(v4).put(u4).put(c4);
		buffer.put(v3).put(u3).put(c3);
		return 6;
	}

	public static int drawQuad(WrappedFloatBuffer buffer, byte face, Vector3f n, Vector3f v1, Vector2f u1, Vector4f c1,
			Vector3f v2, Vector2f u2, Vector4f c2, Vector3f v3, Vector2f u3, Vector4f c3, Vector3f v4, Vector2f u4,
			Vector4f c4) {
		int vertCount = 6;
		switch (face) {
		case BlockFace.FRONT -> drawQuadFacingFront(buffer, v1, u1, c1, v2, u2, c2, v3, u3, c3, v4, u4, c4);
		case BlockFace.BACK -> drawQuadFacingBack(buffer, v1, u1, c1, v2, u2, c2, v3, u3, c3, v4, u4, c4);
		case BlockFace.LEFT -> drawQuadFacingLeft(buffer, v1, u1, c1, v2, u2, c2, v3, u3, c3, v4, u4, c4);
		case BlockFace.RIGHT -> drawQuadFacingRight(buffer, v1, u1, c1, v2, u2, c2, v3, u3, c3, v4, u4, c4);
		case BlockFace.TOP -> drawQuadFacingTop(buffer, v1, u1, c1, v2, u2, c2, v3, u3, c3, v4, u4, c4);
		case BlockFace.BOTTOM -> drawQuadFacingBottom(buffer, v1, u1, c1, v2, u2, c2, v3, u3, c3, v4, u4, c4);
		default -> vertCount = 0;
		}
		return vertCount;
	}

}
