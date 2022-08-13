package xueli.craftgame.renderer.world;

import org.lwjgl.utils.vector.Vector2f;
import org.lwjgl.utils.vector.Vector3f;
import org.lwjgl.utils.vector.Vector4f;

import xueli.craftgame.block.BlockFace;
import xueli.game2.renderer.legacy.buffer.AttributeBuffer;

public class CubeDrawer {

	private CubeDrawer() {
	}

	/**
	 * The following method should be always given vertex from little to big for
	 * instance: ^ -<br/>
	 * *-------------*<br/>
	 * --v3-------v4--<br/>
	 * ---------------<br/>
	 * ---------------<br/>
	 * ---------------<br/>
	 * --v1-------v2--<br/>
	 * *-------------*--><br/>
	 */

	public static void drawQuadFacingRight(IBufferProvider buffer, Vector3f v1, Vector2f u1, Vector4f c1, Vector3f v2,
										   Vector2f u2, Vector4f c2, Vector3f v3, Vector2f u3, Vector4f c3, Vector3f v4, Vector2f u4, Vector4f c4) {
		// 1,2,3,3,2,4
		AttributeBuffer vb = buffer.vertexBuffer();
		AttributeBuffer cb = buffer.colorBuffer();
		AttributeBuffer ub = buffer.uvBuffer();

		vb.submit(v1).submit(v2).submit(v3).submit(v3).submit(v2).submit(v4);
		cb.submit(c1).submit(c2).submit(c3).submit(c3).submit(c2).submit(c4);
		ub.submit(u1).submit(u2).submit(u3).submit(u3).submit(u2).submit(u4);
	}

	public static void drawQuadFacingBack(IBufferProvider buffer, Vector3f v1, Vector2f u1, Vector4f c1, Vector3f v2,
										  Vector2f u2, Vector4f c2, Vector3f v3, Vector2f u3, Vector4f c3, Vector3f v4, Vector2f u4, Vector4f c4) {
		// 1,2,3,3,2,4
		AttributeBuffer vb = buffer.vertexBuffer();
		AttributeBuffer cb = buffer.colorBuffer();
		AttributeBuffer ub = buffer.uvBuffer();

		vb.submit(v1).submit(v2).submit(v3).submit(v3).submit(v2).submit(v4);
		cb.submit(c1).submit(c2).submit(c3).submit(c3).submit(c2).submit(c4);
		ub.submit(u1).submit(u2).submit(u3).submit(u3).submit(u2).submit(u4);
	}

	public static void drawQuadFacingFront(IBufferProvider buffer, Vector3f v1, Vector2f u1, Vector4f c1, Vector3f v2,
										   Vector2f u2, Vector4f c2, Vector3f v3, Vector2f u3, Vector4f c3, Vector3f v4, Vector2f u4, Vector4f c4) {
		// 4,2,1,3,4,1
		AttributeBuffer vb = buffer.vertexBuffer();
		AttributeBuffer cb = buffer.colorBuffer();
		AttributeBuffer ub = buffer.uvBuffer();

		vb.submit(v4).submit(v2).submit(v1).submit(v3).submit(v4).submit(v1);
		cb.submit(c4).submit(c2).submit(c1).submit(c3).submit(c4).submit(c1);
		ub.submit(u4).submit(u2).submit(u1).submit(u3).submit(u4).submit(u1);
	}

	public static void drawQuadFacingLeft(IBufferProvider buffer, Vector3f v1, Vector2f u1, Vector4f c1, Vector3f v2,
										  Vector2f u2, Vector4f c2, Vector3f v3, Vector2f u3, Vector4f c3, Vector3f v4, Vector2f u4, Vector4f c4) {
		// 4,2,1,3,4,1
		AttributeBuffer vb = buffer.vertexBuffer();
		AttributeBuffer cb = buffer.colorBuffer();
		AttributeBuffer ub = buffer.uvBuffer();

		vb.submit(v4).submit(v2).submit(v1).submit(v3).submit(v4).submit(v1);
		cb.submit(c4).submit(c2).submit(c1).submit(c3).submit(c4).submit(c1);
		ub.submit(u4).submit(u2).submit(u1).submit(u3).submit(u4).submit(u1);
	}

	public static void drawQuadFacingTop(IBufferProvider buffer, Vector3f v1, Vector2f u1, Vector4f c1, Vector3f v2,
										 Vector2f u2, Vector4f c2, Vector3f v3, Vector2f u3, Vector4f c3, Vector3f v4, Vector2f u4, Vector4f c4) {
		// 1,3,2,2,3,4
		AttributeBuffer vb = buffer.vertexBuffer();
		AttributeBuffer cb = buffer.colorBuffer();
		AttributeBuffer ub = buffer.uvBuffer();

		vb.submit(v1).submit(v3).submit(v2).submit(v2).submit(v3).submit(v4);
		cb.submit(c1).submit(c3).submit(c2).submit(c2).submit(c3).submit(c4);
		ub.submit(u1).submit(u3).submit(u2).submit(u2).submit(u3).submit(u4);
	}

	public static void drawQuadFacingBottom(IBufferProvider buffer, Vector3f v1, Vector2f u1, Vector4f c1,
											Vector3f v2, Vector2f u2, Vector4f c2, Vector3f v3, Vector2f u3, Vector4f c3, Vector3f v4, Vector2f u4,
											Vector4f c4) {
		// 1,2,3,2,4,3
		AttributeBuffer vb = buffer.vertexBuffer();
		AttributeBuffer cb = buffer.colorBuffer();
		AttributeBuffer ub = buffer.uvBuffer();

		vb.submit(v1).submit(v2).submit(v3).submit(v2).submit(v4).submit(v3);
		cb.submit(c1).submit(c2).submit(c3).submit(c2).submit(c4).submit(c3);
		ub.submit(u1).submit(u2).submit(u3).submit(u2).submit(u4).submit(u3);
	}

	public static void drawQuad(IBufferProvider buffer, Vector3f n, Vector3f v1, Vector2f u1, Vector4f c1,
								Vector3f v2, Vector2f u2, Vector4f c2, Vector3f v3, Vector2f u3, Vector4f c3, Vector3f v4, Vector2f u4,
								Vector4f c4) {
		// 1,2,3,2,4,3
		AttributeBuffer vb = buffer.vertexBuffer();
		AttributeBuffer cb = buffer.colorBuffer();
		AttributeBuffer ub = buffer.uvBuffer();

		vb.submit(v1).submit(v2).submit(v3).submit(v2).submit(v4).submit(v3);
		cb.submit(c1).submit(c2).submit(c3).submit(c2).submit(c4).submit(c3);
		ub.submit(u1).submit(u2).submit(u3).submit(u2).submit(u4).submit(u3);
	}

	public static void drawQuad(IBufferProvider buffer, byte face, Vector3f n, Vector3f v1, Vector2f u1, Vector4f c1,
								Vector3f v2, Vector2f u2, Vector4f c2, Vector3f v3, Vector2f u3, Vector4f c3, Vector3f v4, Vector2f u4,
								Vector4f c4) {
		switch (face) {
			case BlockFace.FRONT -> drawQuadFacingFront(buffer, v1, u1, c1, v2, u2, c2, v3, u3, c3, v4, u4, c4);
			case BlockFace.BACK -> drawQuadFacingBack(buffer, v1, u1, c1, v2, u2, c2, v3, u3, c3, v4, u4, c4);
			case BlockFace.LEFT -> drawQuadFacingLeft(buffer, v1, u1, c1, v2, u2, c2, v3, u3, c3, v4, u4, c4);
			case BlockFace.RIGHT -> drawQuadFacingRight(buffer, v1, u1, c1, v2, u2, c2, v3, u3, c3, v4, u4, c4);
			case BlockFace.TOP -> drawQuadFacingTop(buffer, v1, u1, c1, v2, u2, c2, v3, u3, c3, v4, u4, c4);
			case BlockFace.BOTTOM -> drawQuadFacingBottom(buffer, v1, u1, c1, v2, u2, c2, v3, u3, c3, v4, u4, c4);
		}
	}

}
