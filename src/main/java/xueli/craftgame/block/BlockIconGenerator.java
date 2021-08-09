package xueli.craftgame.block;

import org.lwjgl.opengl.GL30;
import org.lwjgl.utils.vector.Vector3f;

import xueli.craftgame.renderer.VertexPointer;
import xueli.game.renderer.FrameBuffer;
import xueli.game.utils.FloatList;
import xueli.game.utils.Shader;
import xueli.game.utils.math.MatrixHelper;
import xueli.game.utils.texture.TextureAtlas;

public class BlockIconGenerator {

	private static final int MODEL_VIEW_SIZE = 256;

	private static Shader shader;
	private static VertexPointer pointer;
	private static FloatList buf = new FloatList(4096);

	static {
		pointer = new VertexPointer(16384, GL30.GL_STATIC_DRAW);
		shader = new Shader("res/shaders/block_view/vert.txt", "res/shaders/block_view/frag.txt");

		Shader.setProjectionMatrix(shader, MatrixHelper.ortho(-0.9f, 0.9f, -0.9f, 0.9f, 0.01f, 1000000.0f));

	}

	public static FrameBuffer generate(BlockBase base, TextureAtlas blocksAtlas) {
		// ADD SOME RANDOM
		Shader.setViewMatrix(MatrixHelper.lookAt(new Vector3f(4 + (float) (Math.random() * 2.0f - 1.0f) * 0.4f,
				3.5f + (float) (Math.random() * 2.0f - 1.0f) * 0.4f, 4 + (float) (Math.random() * 2.0f - 1.0f) * 0.4f),
				new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(0, 1, 0)), shader);

		FrameBuffer buffer = new FrameBuffer(MODEL_VIEW_SIZE, MODEL_VIEW_SIZE);

		int vertCount = base.getRenderModelViewData(buf);

		GL30.glEnable(GL30.GL_DEPTH_TEST);

		shader.use();
		buffer.use();
		pointer.initDraw();
		buf.storeInBuffer(pointer.mapBuffer().asFloatBuffer());
		pointer.unmap();
		blocksAtlas.bind();
		pointer.draw(GL30.GL_TRIANGLES, 0, vertCount);
		blocksAtlas.unbind();
		pointer.postDraw();
		buffer.unbind();
		shader.unbind();

		GL30.glDisable(GL30.GL_DEPTH_TEST);

		buf.clear();

		return buffer;
	}

}
