package xueli.craftgame.renderer.model;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import xueli.craftgame.block.BlockFace;
import xueli.game.utils.FloatList;

public class CubeDrawer {

	private CubeDrawer() {
	}

	/**
	 * The following method should be always given vertex from little to big for
	 * instance: ^ - *-------------* --v3-------v4-- --------------- ---------------
	 * --------------- --v1-------v2-- *-------------*-->
	 */
	
	public static int drawQuadFacingRight(FloatList buffer, Vector3f v1, Vector2f t1, Vector3f c1, float s1, Vector3f v2,
			Vector2f t2, Vector3f c2,float s2, Vector3f v3, Vector2f t3, Vector3f c3,float s3, Vector3f v4, Vector2f t4, Vector3f c4,float s4) {
		buffer.put(t1).put(c1).put(s1).put(v1).put(BlockFace.RIGHT_NORMAL);
		buffer.put(t2).put(c2).put(s2).put(v2).put(BlockFace.RIGHT_NORMAL);
		buffer.put(t3).put(c3).put(s3).put(v3).put(BlockFace.RIGHT_NORMAL);
		buffer.put(t3).put(c3).put(s3).put(v3).put(BlockFace.RIGHT_NORMAL);
		buffer.put(t2).put(c2).put(s2).put(v2).put(BlockFace.RIGHT_NORMAL);
		buffer.put(t4).put(c4).put(s4).put(v4).put(BlockFace.RIGHT_NORMAL);
		return 6;
	}
	
	public static int drawQuadFacingBack(FloatList buffer, Vector3f v1, Vector2f t1, Vector3f c1, float s1, Vector3f v2,
			Vector2f t2, Vector3f c2,float s2, Vector3f v3, Vector2f t3, Vector3f c3,float s3, Vector3f v4, Vector2f t4, Vector3f c4,float s4) {
		buffer.put(t1).put(c1).put(s1).put(v1).put(BlockFace.BACK_NORMAL);
		buffer.put(t2).put(c2).put(s2).put(v2).put(BlockFace.BACK_NORMAL);
		buffer.put(t3).put(c3).put(s3).put(v3).put(BlockFace.BACK_NORMAL);
		buffer.put(t3).put(c3).put(s3).put(v3).put(BlockFace.BACK_NORMAL);
		buffer.put(t2).put(c2).put(s2).put(v2).put(BlockFace.BACK_NORMAL);
		buffer.put(t4).put(c4).put(s4).put(v4).put(BlockFace.BACK_NORMAL);
		return 6;
	}
	
	public static int drawQuadFacingFront(FloatList buffer, Vector3f v1, Vector2f t1, Vector3f c1, float s1, Vector3f v2,
			Vector2f t2, Vector3f c2,float s2, Vector3f v3, Vector2f t3, Vector3f c3,float s3, Vector3f v4, Vector2f t4, Vector3f c4,float s4) {
		buffer.put(t4).put(c4).put(s4).put(v4).put(BlockFace.FRONT_NORMAL);
		buffer.put(t2).put(c2).put(s2).put(v2).put(BlockFace.FRONT_NORMAL);
		buffer.put(t1).put(c1).put(s1).put(v1).put(BlockFace.FRONT_NORMAL);
		buffer.put(t3).put(c3).put(s3).put(v3).put(BlockFace.FRONT_NORMAL);
		buffer.put(t4).put(c4).put(s4).put(v4).put(BlockFace.FRONT_NORMAL);
		buffer.put(t1).put(c1).put(s1).put(v1).put(BlockFace.FRONT_NORMAL);
		return 6;
	}

	public static int drawQuadFacingLeft(FloatList buffer, Vector3f v1, Vector2f t1, Vector3f c1, float s1, Vector3f v2,
			Vector2f t2, Vector3f c2,float s2, Vector3f v3, Vector2f t3, Vector3f c3,float s3, Vector3f v4, Vector2f t4, Vector3f c4,float s4) {
		buffer.put(t4).put(c4).put(s4).put(v4).put(BlockFace.LEFT_NORMAL);
		buffer.put(t2).put(c2).put(s2).put(v2).put(BlockFace.LEFT_NORMAL);
		buffer.put(t1).put(c1).put(s1).put(v1).put(BlockFace.LEFT_NORMAL);
		buffer.put(t3).put(c3).put(s3).put(v3).put(BlockFace.LEFT_NORMAL);
		buffer.put(t4).put(c4).put(s4).put(v4).put(BlockFace.LEFT_NORMAL);
		buffer.put(t1).put(c1).put(s1).put(v1).put(BlockFace.LEFT_NORMAL);
		return 6;
	}

	public static int drawQuadFacingTop(FloatList buffer, Vector3f v1, Vector2f t1, Vector3f c1, float s1, Vector3f v2,
			Vector2f t2, Vector3f c2,float s2, Vector3f v3, Vector2f t3, Vector3f c3,float s3, Vector3f v4, Vector2f t4, Vector3f c4,float s4) {
		buffer.put(t1).put(c1).put(s1).put(v1).put(BlockFace.TOP_NORMAL);
		buffer.put(t3).put(c3).put(s3).put(v3).put(BlockFace.TOP_NORMAL);
		buffer.put(t2).put(c2).put(s2).put(v2).put(BlockFace.TOP_NORMAL);
		buffer.put(t2).put(c2).put(s2).put(v2).put(BlockFace.TOP_NORMAL);
		buffer.put(t3).put(c3).put(s3).put(v3).put(BlockFace.TOP_NORMAL);
		buffer.put(t4).put(c4).put(s4).put(v4).put(BlockFace.TOP_NORMAL);
		return 6;
	}

	public static int drawQuadFacingBottom(FloatList buffer, Vector3f v1, Vector2f t1, Vector3f c1, float s1, Vector3f v2,
			Vector2f t2, Vector3f c2,float s2, Vector3f v3, Vector2f t3, Vector3f c3,float s3, Vector3f v4, Vector2f t4, Vector3f c4,float s4) {
		buffer.put(t1).put(c1).put(s1).put(v1).put(BlockFace.TOP_NORMAL);
		buffer.put(t2).put(c2).put(s2).put(v2).put(BlockFace.TOP_NORMAL);
		buffer.put(t3).put(c3).put(s3).put(v3).put(BlockFace.TOP_NORMAL);
		buffer.put(t2).put(c2).put(s2).put(v2).put(BlockFace.TOP_NORMAL);
		buffer.put(t4).put(c4).put(s4).put(v4).put(BlockFace.TOP_NORMAL);
		buffer.put(t3).put(c3).put(s3).put(v3).put(BlockFace.TOP_NORMAL);
		return 6;
	}
	
	public static int drawQuad(FloatList buffer, Vector3f v1, Vector2f t1, Vector3f c1, float s1, Vector3f v2,
			Vector2f t2, Vector3f c2,float s2, Vector3f v3, Vector2f t3, Vector3f c3,float s3, Vector3f v4, Vector2f t4, Vector3f c4,float s4, Vector3f n) {
		buffer.put(t1).put(c1).put(s1).put(v1).put(n);
		buffer.put(t2).put(c2).put(s2).put(v2).put(n);
		buffer.put(t3).put(c3).put(s3).put(v3).put(n);
		buffer.put(t2).put(c2).put(s2).put(v2).put(n);
		buffer.put(t4).put(c4).put(s4).put(v4).put(n);
		buffer.put(t3).put(c3).put(s3).put(v3).put(n);
		return 6;
	}

}
