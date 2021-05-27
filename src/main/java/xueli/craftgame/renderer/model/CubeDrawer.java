package xueli.craftgame.renderer.model;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import xueli.craftgame.block.BlockFace;
import xueli.game.utils.FloatList;
import xueli.game.vector.Vector4b;

public class CubeDrawer {

	private CubeDrawer() {
	}

	/**
	 * The following method should be always given vertex from little to big for
	 * instance: ^ - *-------------* --v3-------v4-- --------------- ---------------
	 * --------------- --v1-------v2-- *-------------*-->
	 */
	
	public static int drawQuadFacingRight(FloatList buffer,
			Vector3f v1, Vector2f u1, Vector4b l1,
			Vector3f v2, Vector2f u2, Vector4b l2,
			Vector3f v3, Vector2f u3, Vector4b l3,
			Vector3f v4, Vector2f u4, Vector4b l4) {
		// 1,2,3,3,2,4
		buffer.put(v1).put(u1).put(BlockFace.RIGHT_NORMAL).put(l1);
		buffer.put(v2).put(u2).put(BlockFace.RIGHT_NORMAL).put(l2);
		buffer.put(v3).put(u3).put(BlockFace.RIGHT_NORMAL).put(l3);
		buffer.put(v3).put(u3).put(BlockFace.RIGHT_NORMAL).put(l3);
		buffer.put(v2).put(u2).put(BlockFace.RIGHT_NORMAL).put(l2);
		buffer.put(v4).put(u4).put(BlockFace.RIGHT_NORMAL).put(l4);
		return 6;
	}
	
	public static int drawQuadFacingBack(FloatList buffer,
			Vector3f v1, Vector2f u1, Vector4b l1,
			Vector3f v2, Vector2f u2, Vector4b l2,
			Vector3f v3, Vector2f u3, Vector4b l3,
			Vector3f v4, Vector2f u4, Vector4b l4) {
		// 1,2,3,3,2,4
		buffer.put(v1).put(u1).put(BlockFace.BACK_NORMAL).put(l1);
		buffer.put(v2).put(u2).put(BlockFace.BACK_NORMAL).put(l2);
		buffer.put(v3).put(u3).put(BlockFace.BACK_NORMAL).put(l3);
		buffer.put(v3).put(u3).put(BlockFace.BACK_NORMAL).put(l3);
		buffer.put(v2).put(u2).put(BlockFace.BACK_NORMAL).put(l2);
		buffer.put(v4).put(u4).put(BlockFace.BACK_NORMAL).put(l4);
		return 6;
	}
	
	public static int drawQuadFacingFront(FloatList buffer, 
			Vector3f v1, Vector2f u1, Vector4b l1,
			Vector3f v2, Vector2f u2, Vector4b l2,
			Vector3f v3, Vector2f u3, Vector4b l3,
			Vector3f v4, Vector2f u4, Vector4b l4) {
		// 4,2,1,3,4,1
		buffer.put(v4).put(u4).put(BlockFace.FRONT_NORMAL).put(l4);
		buffer.put(v2).put(u2).put(BlockFace.FRONT_NORMAL).put(l2);
		buffer.put(v1).put(u1).put(BlockFace.FRONT_NORMAL).put(l1);
		buffer.put(v3).put(u3).put(BlockFace.FRONT_NORMAL).put(l3);
		buffer.put(v4).put(u4).put(BlockFace.FRONT_NORMAL).put(l4);
		buffer.put(v1).put(u1).put(BlockFace.FRONT_NORMAL).put(l1);
		return 6;
	}

	public static int drawQuadFacingLeft(FloatList buffer,
			Vector3f v1, Vector2f u1, Vector4b l1,
			Vector3f v2, Vector2f u2, Vector4b l2,
			Vector3f v3, Vector2f u3, Vector4b l3,
			Vector3f v4, Vector2f u4, Vector4b l4) {
		// 4,2,1,3,4,1
		buffer.put(v4).put(u4).put(BlockFace.LEFT_NORMAL).put(l4);
		buffer.put(v2).put(u2).put(BlockFace.LEFT_NORMAL).put(l2);
		buffer.put(v1).put(u1).put(BlockFace.LEFT_NORMAL).put(l1);
		buffer.put(v3).put(u3).put(BlockFace.LEFT_NORMAL).put(l3);
		buffer.put(v4).put(u4).put(BlockFace.LEFT_NORMAL).put(l4);
		buffer.put(v1).put(u1).put(BlockFace.LEFT_NORMAL).put(l1);
		return 6;
	}

	public static int drawQuadFacingTop(FloatList buffer, 
			Vector3f v1, Vector2f u1, Vector4b l1,
			Vector3f v2, Vector2f u2, Vector4b l2,
			Vector3f v3, Vector2f u3, Vector4b l3,
			Vector3f v4, Vector2f u4, Vector4b l4) {
		// 1,3,2,2,3,4
		buffer.put(v1).put(u1).put(BlockFace.TOP_NORMAL).put(l1);
		buffer.put(v3).put(u3).put(BlockFace.TOP_NORMAL).put(l3);
		buffer.put(v2).put(u2).put(BlockFace.TOP_NORMAL).put(l2);
		buffer.put(v2).put(u2).put(BlockFace.TOP_NORMAL).put(l2);
		buffer.put(v3).put(u3).put(BlockFace.TOP_NORMAL).put(l3);
		buffer.put(v4).put(u4).put(BlockFace.TOP_NORMAL).put(l4);
		return 6;
	}

	public static int drawQuadFacingBottom(FloatList buffer,
			Vector3f v1, Vector2f u1, Vector4b l1,
			Vector3f v2, Vector2f u2, Vector4b l2,
			Vector3f v3, Vector2f u3, Vector4b l3,
			Vector3f v4, Vector2f u4, Vector4b l4) {
		// 1,2,3,2,4,3
		buffer.put(v1).put(u1).put(BlockFace.TOP_NORMAL).put(l1);
		buffer.put(v2).put(u2).put(BlockFace.TOP_NORMAL).put(l2);
		buffer.put(v3).put(u3).put(BlockFace.TOP_NORMAL).put(l3);
		buffer.put(v2).put(u2).put(BlockFace.TOP_NORMAL).put(l2);
		buffer.put(v4).put(u4).put(BlockFace.TOP_NORMAL).put(l4);
		buffer.put(v3).put(u3).put(BlockFace.TOP_NORMAL).put(l3);
		return 6;
	}
	
	public static int drawQuad(FloatList buffer, Vector3f n,
			Vector3f v1, Vector2f u1, Vector4b l1,
			Vector3f v2, Vector2f u2, Vector4b l2,
			Vector3f v3, Vector2f u3, Vector4b l3,
			Vector3f v4, Vector2f u4, Vector4b l4) {
		// 1,2,3,2,4,3
		buffer.put(v1).put(u1).put(n).put(l1);
		buffer.put(v2).put(u2).put(n).put(l2);
		buffer.put(v3).put(u3).put(n).put(l3);
		buffer.put(v2).put(u2).put(n).put(l2);
		buffer.put(v4).put(u4).put(n).put(l4);
		buffer.put(v3).put(u3).put(n).put(l3);
		return 6;
	}

}
