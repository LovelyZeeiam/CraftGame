package xueli.game.renderer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import xueli.game2.renderer.legacy.DefaultRenderBuffer;
import xueli.game2.resource.submanager.render.shader.Shader;
import xueli.utils.io.Files;

public class ScreenQuadRenderer {

	private static final float[] VERTICES = new float[] { -1, -1, 0, 0, -1, 1, 0, 1, 1, 1, 1, 1, 1, -1, 1, 0 };

	private ScreenQuadPointer pointer;
	private Shader shader;

	public ScreenQuadRenderer() {
		pointer = new ScreenQuadPointer();

		pointer.bind();
		pointer.mapBuffer().asFloatBuffer().put(VERTICES);
		pointer.unmap();
		pointer.unbind();

		try {
			// TODO
			shader = Shader.getShader(
					new String(Files.readResourcePackedInJar("/assets/craftgame/shaders/screen_quad/vert.txt"),
							StandardCharsets.UTF_8),
					new String(Files.readResourcePackedInJar("/assets/craftgame/shaders/screen_quad/frag.txt"),
							StandardCharsets.UTF_8));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		// shader = new Shader("res/shaders/screen_quad/vert.txt",
		// "res/shaders/screen_quad/frag.txt");

		shader.bind();
		shader.setInt(shader.getUnifromLocation("tex"), 0);
		shader.setInt(shader.getUnifromLocation("depth"), 1);
		shader.unbind();

	}

	public ScreenQuadRenderer(Shader shader) {
		pointer = new ScreenQuadPointer();

		pointer.bind();
		pointer.mapBuffer().asFloatBuffer().put(VERTICES);
		pointer.unmap();
		pointer.unbind();

		this.shader = shader;

	}

	public void render(int textureId) {
		shader.bind();
		pointer.bind();
		GL30.glBindTexture(GL30.GL_TEXTURE_2D, textureId);
		pointer.draw(GL30.GL_TRIANGLE_FAN, 0, 4);
		pointer.unbind();
		shader.unbind();

	}

	public void render(int textureId, int depthTexID) {
		shader.bind();
		pointer.bind();

		GL30.glActiveTexture(GL30.GL_TEXTURE0);
		GL30.glBindTexture(GL30.GL_TEXTURE_2D, textureId);
		GL30.glActiveTexture(GL30.GL_TEXTURE1);
		GL30.glBindTexture(GL30.GL_TEXTURE_2D, depthTexID);

		pointer.draw(GL30.GL_TRIANGLE_FAN, 0, 4);
		pointer.unbind();
		shader.unbind();

	}

	public ScreenQuadPointer getPointer() {
		return pointer;
	}

	public Shader getShader() {
		return shader;
	}

	public static class ScreenQuadPointer extends DefaultRenderBuffer.DefaultVertexPointer {

		public ScreenQuadPointer() {
			super(1024, GL30.GL_STATIC_DRAW);

		}

		@Override
		protected void registerVertex() {
			// UV
			GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 4 * 4, 2 * 4);
			GL20.glEnableVertexAttribArray(1);

			// position
			GL20.glVertexAttribPointer(0, 2, GL11.GL_FLOAT, false, 4 * 4, 0 * 4);
			GL20.glEnableVertexAttribArray(0);

		}

	}

}
