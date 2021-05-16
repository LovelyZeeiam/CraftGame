package xueli.game;

import static org.lwjgl.opengl.GL30.*;

import java.io.IOException;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import xueli.craftgame.renderer.VertexPointer;
import xueli.game.player.FirstPersonCamera;
import xueli.game.utils.Shader;
import xueli.game.utils.math.MatrixHelper;
import xueli.game.utils.texture.Texture;

public class ShadowMappingTest extends Game {

	private static final float[] VERTICES = new float[] { 0, 0, 0.2f, 0.3f, 0.6f, 0, 0, 0, 0, 0, 0.2f, 0.3f, 0.6f, 0, 0,
			20, 0, 0, 0.2f, 0.3f, 0.6f, 20, 0, 0, 0, 0, 0.2f, 0.3f, 0.6f, 20, 0, 20,

			0, 0, 0.4f, 0.5f, 0.8f, 10, 5, 10, 0, 0, 0.4f, 0.5f, 0.8f, 10, 5, 13, 0, 0, 0.4f, 0.5f, 0.8f, 13, 5, 10, 0,
			0, 0.4f, 0.5f, 0.8f, 13, 5, 13,

	};

	private static final float[] QUAD = new float[] { 0, 0, 1, 1, 1, -1, -1, 0, 1, 0, 1, 1, 1, 1, -1, 0, 0, 1, 1, 1, 1,
			-1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0 };

	private VertexPointer pointer, pointerQuad;

	private Shader shader, shader_depth, shadow_depth_show;
	private FirstPersonCamera camera = new FirstPersonCamera(0, 10, 0, 0, 135, 0);

	private static final int SHADOW_SIZE = 4096;
	private int fbo, f_tbo, f_tbo_image;

	@SuppressWarnings("unused")
	private Texture test_texture;

	private Vector3f lightPosVector3f = new Vector3f(0, 100, 0);
	private Matrix4f lightProj = MatrixHelper.ortho(-20, 20, -20, 20, 0.1f, 9999999.0f);
	private Matrix4f lightLookAt = MatrixHelper.lookAt(lightPosVector3f, new Vector3f(10, 5, 10),
			new Vector3f(0, 1, 0));

	public ShadowMappingTest() {
		super(800, 600, "Test - ShadowMapping");

	}

	@Override
	public void oncreate() {
		this.shader = new Shader("res/shaders/test_shadow_mapping/vert.txt",
				"res/shaders/test_shadow_mapping/frag.txt");
		this.shader_depth = new Shader("res/shaders/test_shadow_mapping_depth/vert.txt",
				"res/shaders/test_shadow_mapping_depth/frag.txt");
		this.shadow_depth_show = new Shader("res/shaders/test_shadow_mapping_depth_show/vert.txt",
				"res/shaders/test_shadow_mapping_depth_show/frag.txt");

		this.shadow_depth_show.use();
		shadow_depth_show.setInt(shadow_depth_show.getUnifromLocation("depthMap"), 0);
		shadow_depth_show.setInt(shadow_depth_show.getUnifromLocation("viewTexture"), 1);
		this.shadow_depth_show.unbind();

		try {
			this.test_texture = Texture.loadTexture("res/icon.png");
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.pointer = new VertexPointer(1024, GL_STATIC_DRAW);
		this.pointer.initDraw();
		this.pointer.mapBuffer().asFloatBuffer().put(VERTICES);
		this.pointer.unmap();
		this.pointer.postDraw();

		this.pointerQuad = new VertexPointer(1024, GL_STATIC_DRAW);
		this.pointerQuad.initDraw();
		this.pointerQuad.mapBuffer().asFloatBuffer().put(QUAD);
		this.pointerQuad.unmap();
		this.pointerQuad.postDraw();

		{
			this.fbo = glGenFramebuffers();

			this.f_tbo = glGenTextures();
			glBindTexture(GL_TEXTURE_2D, f_tbo);
			glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT, SHADOW_SIZE, SHADOW_SIZE, 0, GL_DEPTH_COMPONENT,
					GL_FLOAT, 0);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

			this.f_tbo_image = glGenTextures();
			glBindTexture(GL_TEXTURE_2D, f_tbo_image);
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, SHADOW_SIZE, SHADOW_SIZE, 0, GL_RGB, GL_UNSIGNED_BYTE, 0);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

			glBindFramebuffer(GL_FRAMEBUFFER, fbo);
			glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_TEXTURE_2D, f_tbo, 0);
			glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, f_tbo_image, 0);
			// glDrawBuffer(GL_NONE);
			// glReadBuffer(GL_NONE);

			if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
				throw new RuntimeException("FrameBuffer not complete!");
			}

			glBindFramebuffer(GL_FRAMEBUFFER, 0);

		}

	}

	private void drawScene() {
		this.pointer.initDraw();
		this.pointer.draw(GL_TRIANGLE_STRIP, 0, 4);
		this.pointer.draw(GL_TRIANGLE_STRIP, 4, 4);
		this.pointer.postDraw();

	}

	@SuppressWarnings("unused")
	private void drawQuad() {
		this.pointerQuad.initDraw();
		this.pointerQuad.draw(GL_TRIANGLE_STRIP, 0, 4);
		this.pointerQuad.postDraw();

	}

	@Override
	public void ontick() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glClearColor(0.5f, 0.6f, 0.9f, 1.0f);

		glEnable(GL_DEPTH_TEST);
		glEnable(GL_CULL_FACE);

		{
			glBindFramebuffer(GL_FRAMEBUFFER, fbo);
			glViewport(0, 0, SHADOW_SIZE, SHADOW_SIZE);
			glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);

			Shader.setProjectionMatrix(shader_depth, lightProj);
			Shader.setViewMatrix(lightLookAt, shader_depth);

			shader_depth.use();
			//glCullFace(GL_FRONT);
			drawScene();
			//glCullFace(GL_BACK);
			shader_depth.unbind();

			glBindFramebuffer(GL_FRAMEBUFFER, 0);

		}

		/*
		 * { defaultViewport();
		 * 
		 * shadow_depth_show.use(); glActiveTexture(GL_TEXTURE0);
		 * glBindTexture(GL_TEXTURE_2D, f_tbo); glActiveTexture(GL_TEXTURE1);
		 * glBindTexture(GL_TEXTURE_2D, f_tbo_image); drawQuad();
		 * glBindTexture(GL_TEXTURE_2D, 0); shadow_depth_show.unbind();
		 * 
		 * }
		 */

		{
			getDisplay().setMouseGrabbed(true);

			defaultViewport();

			camera.tick();
			Shader.setProjectionMatrix(this, shader);
			Shader.setViewMatrix(camera.getPos(), shader);

			shader.use();

			Matrix4f lightSpaceMat = Matrix4f.mul(lightProj, lightLookAt, null);
			shader.setUniformMatrix(shader.getUnifromLocation("lightSpaceMatrix"), lightSpaceMat);

			glActiveTexture(GL_TEXTURE0);
			glBindTexture(GL_TEXTURE_2D, f_tbo);
			drawScene();

			shader.unbind();

		}

	}

	@Override
	public void onSize(int width, int height) {
		super.onSize(width, height);
		Shader.setProjectionMatrix(this, shader);

	}

	@Override
	public void onrelease() {

	}

	public static void main(String[] args) {
		new Thread(new ShadowMappingTest()).start();
	}

}
