package xueli.craftgame.renderer.blocks;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.utils.vector.Matrix4f;
import org.lwjgl.utils.vector.Vector2f;
import org.lwjgl.utils.vector.Vector3f;
import org.lwjgl.utils.vector.Vector3i;
import org.lwjgl.utils.vector.Vector4f;

import xueli.craftgame.CraftGameContext;
import xueli.craftgame.player.LocalPlayer;
import xueli.craftgame.renderer.world.BufferProvider;
import xueli.craftgame.renderer.world.CubeDrawer;
import xueli.craftgame.renderer.IGameRenderer;
import xueli.craftgame.renderer.WorldRenderer;
import xueli.craftgame.utils.Colors;
import xueli.game.resource.texture.Texture;
import xueli.game.utils.WrappedFloatBuffer;
import xueli.game2.renderer.legacy.buffer.VertexPointer;
import xueli.game2.renderer.legacy.system.RenderState;
import xueli.game2.renderer.legacy.system.RenderSystem;
import xueli.game2.resource.ResourceLocation;
import xueli.game2.resource.submanager.render.shader.Shader;
import xueli.game2.resource.submanager.render.shader.ShaderResourceLocation;
import xueli.game2.resource.submanager.render.texture.TextureRenderResource;
import xueli.game2.resource.submanager.render.texture.TextureResourceLocation;
import xueli.game2.resource.submanager.render.texture.TextureType;
import xueli.utils.io.Files;

public class BlockBorderRenderer implements IGameRenderer {

	private static final float BOX_SIZE = 1.01f;

	private static final TextureResourceLocation BORDER_TEXTURE_LOCATION = new TextureResourceLocation(new ResourceLocation("images/hud/border.png"), TextureType.LEGACY);
	private static final ShaderResourceLocation SOLID_BLOCKS_SHADERS = new ShaderResourceLocation(
			new ResourceLocation("shaders/world/world.vert"),
			new ResourceLocation("shaders/world/world.frag")
	);

	private WorldRenderer ctx;
	private LocalPlayer player;

	private RenderSystem renderer;

	private int borderTextureId;
	private Shader shader;

	public BlockBorderRenderer(WorldRenderer renderer) {
		this.ctx = renderer;
		this.player = renderer.getPlayer();

	}

	private float boxPos, boxOffset;

	@Override
	public void init() {
		CraftGameContext cgCtx = ctx.getContext();
		TextureRenderResource textureRenderResource = cgCtx.getTextureRenderResource();
		this.borderTextureId = textureRenderResource.preRegister(BORDER_TEXTURE_LOCATION, true);
		this.shader = cgCtx.getShaderRenderResource().preRegister(SOLID_BLOCKS_SHADERS, true);

		this.renderer = RenderSystem.withState(RenderState.easyState(shader, GL11.GL_TRIANGLES, borderTextureId));
		BufferProvider provider = new BufferProvider(this.renderer);

		Vector4f c1, c2, c3, c4;
		c1 = c2 = c3 = c4 = Colors.WHITE;
		Vector2f p_left_down = new Vector2f(0, 0);
		Vector2f p_right_down = new Vector2f(1, 0);
		Vector2f p_left_top = new Vector2f(0, 1);
		Vector2f p_right_top = new Vector2f(1, 1);
		this.boxPos = -(BOX_SIZE - 1) / 2;
		this.boxOffset = boxPos / 2;
		CubeDrawer.drawQuadFacingFront(provider, new Vector3f(boxPos, boxPos, boxPos), p_left_down, c1,
				new Vector3f(boxPos + BOX_SIZE, boxPos, boxPos), p_right_down, c2,
				new Vector3f(boxPos, boxPos + BOX_SIZE, boxPos), p_left_top, c3,
				new Vector3f(boxPos + BOX_SIZE, boxPos + BOX_SIZE, boxPos), p_right_top, c4);
		CubeDrawer.drawQuadFacingRight(provider, new Vector3f(boxPos + BOX_SIZE, boxPos, boxPos), p_right_down, c1,
				new Vector3f(boxPos + BOX_SIZE, boxPos + BOX_SIZE, boxPos), p_right_top, c2,
				new Vector3f(boxPos + BOX_SIZE, boxPos, boxPos + BOX_SIZE), p_left_down, c3,
				new Vector3f(boxPos + BOX_SIZE, boxPos + BOX_SIZE, boxPos + BOX_SIZE), p_left_top, c4);
		CubeDrawer.drawQuadFacingBack(provider, new Vector3f(boxPos, boxPos, boxPos + BOX_SIZE), p_left_down, c1,
				new Vector3f(boxPos + BOX_SIZE, boxPos, boxPos + BOX_SIZE), p_right_down, c2,
				new Vector3f(boxPos, boxPos + BOX_SIZE, boxPos + BOX_SIZE), p_left_top, c3,
				new Vector3f(boxPos + BOX_SIZE, boxPos + BOX_SIZE, boxPos + BOX_SIZE), p_right_top, c4);
		CubeDrawer.drawQuadFacingLeft(provider, new Vector3f(boxPos, boxPos, boxPos), p_left_down, c1,
				new Vector3f(boxPos, boxPos + BOX_SIZE, boxPos), p_left_top, c2,
				new Vector3f(boxPos, boxPos, boxPos + BOX_SIZE), p_right_down, c3,
				new Vector3f(boxPos, boxPos + BOX_SIZE, boxPos + BOX_SIZE), p_right_top, c4);
		CubeDrawer.drawQuadFacingTop(provider, new Vector3f(boxPos, boxPos + BOX_SIZE, boxPos), p_left_down, c1,
				new Vector3f(boxPos + BOX_SIZE, boxPos + BOX_SIZE, boxPos), p_left_top, c2,
				new Vector3f(boxPos, boxPos + BOX_SIZE, boxPos + BOX_SIZE), p_right_down, c3,
				new Vector3f(boxPos + BOX_SIZE, boxPos + BOX_SIZE, boxPos + BOX_SIZE), p_right_top, c4);
		CubeDrawer.drawQuadFacingBottom(provider, new Vector3f(boxPos, boxPos, boxPos), p_left_down, c1,
				new Vector3f(boxPos + BOX_SIZE, boxPos, boxPos), p_left_top, c2,
				new Vector3f(boxPos, boxPos, boxPos + BOX_SIZE), p_right_down, c3,
				new Vector3f(boxPos + BOX_SIZE, boxPos, boxPos + BOX_SIZE), p_right_top, c4);

	}

	@Override
	public void render() {
		Vector3i targetBlock = player.getSelectedBlock();
		if (targetBlock != null) {
			Matrix4f boxMatrix = new Matrix4f();
			boxMatrix.setIdentity();
			Matrix4f.translate(
					new Vector3f(targetBlock.x - boxOffset, targetBlock.y - boxOffset, targetBlock.z - boxOffset),
					boxMatrix, boxMatrix);
			Matrix4f.mul(ctx.getViewMatrix(), boxMatrix, boxMatrix);
			Shader.setViewMatrix(boxMatrix, shader);

			renderer.tick();

		}

	}

	@Override
	public void release() {
		this.renderer.release();

	}

	public WorldRenderer getWorldRenderer() {
		return ctx;
	}

	public LocalPlayer getPlayer() {
		return player;
	}

}
