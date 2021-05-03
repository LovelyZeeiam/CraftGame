package xueli.craftgame.renderer.model;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import xueli.game.utils.FloatList;

public class CubeDrawer {

	private CubeDrawer() {
	}

	/**
	 * The following method should be always given vertex from little to big for
	 * instance: ^ - *-------------* --v3-------v4-- --------------- ---------------
	 * --------------- --v1-------v2-- *-------------*-->
	 */

	/**
	 * Draw Back and Right Face
	 */
	public static int drawQuadFacingBackOrRight(FloatList buffer, Vector3f v1, Vector2f t1, Vector3f c1, Vector3f v2,
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
	public static int drawQuadFacingFrontOrLeft(FloatList buffer, Vector3f v1, Vector2f t1, Vector3f c1, Vector3f v2,
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
	public static int drawQuadFacingTop(FloatList buffer, Vector3f v1, Vector2f t1, Vector3f c1, Vector3f v2, Vector2f t2,
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
	public static int drawQuadFacingBottom(FloatList buffer, Vector3f v1, Vector2f t1, Vector3f c1, Vector3f v2, Vector2f t2,
			Vector3f c2, Vector3f v3, Vector2f t3, Vector3f c3, Vector3f v4, Vector2f t4, Vector3f c4) {
		buffer.put(t1).put(c1).put(v1);
		buffer.put(t2).put(c2).put(v2);
		buffer.put(t3).put(c3).put(v3);
		buffer.put(t2).put(c2).put(v2);
		buffer.put(t4).put(c4).put(v4);
		buffer.put(t3).put(c3).put(v3);
		return 6;
	}

}
