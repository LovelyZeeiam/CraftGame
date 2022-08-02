package xueli.craftgame.renderer.blocks;

import org.lwjgl.opengl.GL11;
import org.lwjgl.utils.vector.Matrix4f;
import org.lwjgl.utils.vector.Vector3f;

import xueli.craftgame.CraftGameContext;
import xueli.craftgame.block.BlockType;
import xueli.craftgame.block.Blocks;
import xueli.craftgame.renderer.WorldRenderer;
import xueli.craftgame.resource.render.shader.Shader;
import xueli.game.renderer.FrameBuffer;
import xueli.game.utils.GLHelper;
import xueli.game.utils.math.MatrixHelper;

public class BlockIconGenerator {

	private static final int MODEL_VIEW_SIZE = 256;

	private static final Matrix4f viewMatrix, projMatrix;

	static {
		projMatrix = MatrixHelper.ortho(-0.9f, 0.9f, -0.9f, 0.9f, 0.01f, 1000000.0f);
		viewMatrix = MatrixHelper.lookAt(new Vector3f(4 + (float) (Math.random() * 2.0f - 1.0f) * 0.4f,
				3.5f + (float) (Math.random() * 2.0f - 1.0f) * 0.4f, 4 + (float) (Math.random() * 2.0f - 1.0f) * 0.4f),
				new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(0, -1, 0));

	}

	public static FrameBuffer generate(BlockType base, CraftGameContext ctx) {
		FrameBuffer frameBuffer = new FrameBuffer(MODEL_VIEW_SIZE, MODEL_VIEW_SIZE);

		WorldRenderer renderer = ctx.getWorldRenderer();
		renderer.getRenderers().forEach(r -> {
			r.newChunkBuffer(0, 0);
			r.getChunkBuffer(0, 0).shouldSyncBuffer = true;
		});
		base.getRenderable().renderReview(renderer);

		frameBuffer.use();
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		for (IBlockRenderer r : renderer.getRenderers()) {
			Shader shader = r.getShader();
			Shader.setViewMatrix(viewMatrix, shader);
			Shader.setProjectionMatrix(shader, projMatrix);
			shader.use();

			ChunkBuffer buf = r.getChunkBuffer(0, 0);
			GLHelper.checkGLError("BlockRendererPreview - Before");
			Blocks.blockTextureAtlas.bind();
			buf.draw();
			Blocks.blockTextureAtlas.unbind();
			GLHelper.checkGLError("BlockRendererPreview - Before");

			shader.unbind();
		}
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		frameBuffer.unbind();

		// frameBuffer.save("temp/" + base.getName() + ".png");

		return frameBuffer;
	}

}
