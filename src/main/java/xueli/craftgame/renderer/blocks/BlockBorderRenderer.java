package xueli.craftgame.renderer.blocks;

import org.lwjgl.utils.vector.Vector2f;
import org.lwjgl.utils.vector.Vector4f;
import xueli.craftgame.player.LocalPlayer;
import xueli.craftgame.renderer.IGameRenderer;
import xueli.craftgame.renderer.WorldRenderer;
import xueli.craftgame.utils.Colors;
import xueli.game.resource.texture.Texture;
import xueli.game.utils.WrappedFloatBuffer;
import xueli.utils.io.Files;

import java.io.IOException;

public class BlockBorderRenderer implements IGameRenderer {

	private static final float BOX_SIZE = 1.01f;

	private WorldRenderer ctx;
	private LocalPlayer player;

	private IBlockRenderer renderer;
	private Texture borderTexture;
//	private VertexPointer pointer;

	public BlockBorderRenderer(WorldRenderer renderer) {
		this.ctx = renderer;
		this.player = renderer.getPlayer();

	}

	private float boxPos, boxOffset;

	@Override
	public void init() {
//		this.renderer = ctx.rendererCube();
//		try {
//			this.borderTexture = Texture
//					.loadTexture(Files.getResourcePackedInJarStream("/assets/images/hud/border.png"));
//		} catch (IOException e) {
//			ctx.getContext().announceCrash("BlockBorderRenderer Init",
//					new Exception("Could load image: /assets/images/hud/border.png", e));
//			return;
//		}
//
//		WrappedFloatBuffer buffer = new WrappedFloatBuffer();
//		Vector4f c1, c2, c3, c4;
//		c1 = c2 = c3 = c4 = Colors.WHITE;
//		Vector2f p_left_down = new Vector2f(0, 0);
//		Vector2f p_right_down = new Vector2f(1, 0);
//		Vector2f p_left_top = new Vector2f(0, 1);
//		Vector2f p_right_top = new Vector2f(1, 1);
//		this.boxPos = -(BOX_SIZE - 1) / 2;
//		this.boxOffset = boxPos / 2;
//		CubeDrawer.drawQuadFacingFront(buffer, new Vector3f(boxPos, boxPos, boxPos), p_left_down, c1,
//				new Vector3f(boxPos + BOX_SIZE, boxPos, boxPos), p_right_down, c2,
//				new Vector3f(boxPos, boxPos + BOX_SIZE, boxPos), p_left_top, c3,
//				new Vector3f(boxPos + BOX_SIZE, boxPos + BOX_SIZE, boxPos), p_right_top, c4);
//		CubeDrawer.drawQuadFacingRight(buffer, new Vector3f(boxPos + BOX_SIZE, boxPos, boxPos), p_right_down, c1,
//				new Vector3f(boxPos + BOX_SIZE, boxPos + BOX_SIZE, boxPos), p_right_top, c2,
//				new Vector3f(boxPos + BOX_SIZE, boxPos, boxPos + BOX_SIZE), p_left_down, c3,
//				new Vector3f(boxPos + BOX_SIZE, boxPos + BOX_SIZE, boxPos + BOX_SIZE), p_left_top, c4);
//		CubeDrawer.drawQuadFacingBack(buffer, new Vector3f(boxPos, boxPos, boxPos + BOX_SIZE), p_left_down, c1,
//				new Vector3f(boxPos + BOX_SIZE, boxPos, boxPos + BOX_SIZE), p_right_down, c2,
//				new Vector3f(boxPos, boxPos + BOX_SIZE, boxPos + BOX_SIZE), p_left_top, c3,
//				new Vector3f(boxPos + BOX_SIZE, boxPos + BOX_SIZE, boxPos + BOX_SIZE), p_right_top, c4);
//		CubeDrawer.drawQuadFacingLeft(buffer, new Vector3f(boxPos, boxPos, boxPos), p_left_down, c1,
//				new Vector3f(boxPos, boxPos + BOX_SIZE, boxPos), p_left_top, c2,
//				new Vector3f(boxPos, boxPos, boxPos + BOX_SIZE), p_right_down, c3,
//				new Vector3f(boxPos, boxPos + BOX_SIZE, boxPos + BOX_SIZE), p_right_top, c4);
//		CubeDrawer.drawQuadFacingTop(buffer, new Vector3f(boxPos, boxPos + BOX_SIZE, boxPos), p_left_down, c1,
//				new Vector3f(boxPos + BOX_SIZE, boxPos + BOX_SIZE, boxPos), p_left_top, c2,
//				new Vector3f(boxPos, boxPos + BOX_SIZE, boxPos + BOX_SIZE), p_right_down, c3,
//				new Vector3f(boxPos + BOX_SIZE, boxPos + BOX_SIZE, boxPos + BOX_SIZE), p_right_top, c4);
//		CubeDrawer.drawQuadFacingBottom(buffer, new Vector3f(boxPos, boxPos, boxPos), p_left_down, c1,
//				new Vector3f(boxPos + BOX_SIZE, boxPos, boxPos), p_left_top, c2,
//				new Vector3f(boxPos, boxPos, boxPos + BOX_SIZE), p_right_down, c3,
//				new Vector3f(boxPos + BOX_SIZE, boxPos, boxPos + BOX_SIZE), p_right_top, c4);
//
//		this.pointer = new VertexPointer(0, GL15.GL_STATIC_DRAW);
//		this.pointer.initDraw();
//		buffer.getBuffer().flip();
//		this.pointer.bufferData(buffer.getBuffer());
//		this.pointer.postDraw();

	}

	@Override
	public void render() {
//		Vector3i targetBlock = player.getSelectedBlock();
//		if (targetBlock != null) {
//			Shader shader = renderer.getShader();
//
//			Matrix4f boxMatrix = new Matrix4f();
//			boxMatrix.setIdentity();
//			Matrix4f.translate(
//					new Vector3f(targetBlock.x - boxOffset, targetBlock.y - boxOffset, targetBlock.z - boxOffset),
//					boxMatrix, boxMatrix);
//			Matrix4f.mul(ctx.getViewMatrix(), boxMatrix, boxMatrix);
//			Shader.setViewMatrix(boxMatrix, shader);
//
//			shader.bind();
//			borderTexture.bind();
//			this.pointer.initDraw();
//			this.pointer.draw(36);
//			this.pointer.postDraw();
//			borderTexture.unbind();
//			shader.unbind();
//
//		}

	}

	@Override
	public void release() {
//		this.pointer.delete();

	}

	public WorldRenderer getWorldRenderer() {
		return ctx;
	}

	public LocalPlayer getPlayer() {
		return player;
	}

}
