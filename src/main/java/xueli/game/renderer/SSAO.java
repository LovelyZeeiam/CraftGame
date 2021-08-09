package xueli.game.renderer;

import static org.lwjgl.opengl.GL11.GL_DEPTH_COMPONENT;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.GL_TEXTURE2;
import static org.lwjgl.opengl.GL13.GL_TEXTURE3;
import static org.lwjgl.opengl.GL13.GL_TEXTURE4;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT1;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT2;
import static org.lwjgl.opengl.GL30.GL_DEPTH_ATTACHMENT;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_COMPLETE;
import static org.lwjgl.opengl.GL30.GL_RGB16F;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;
import static org.lwjgl.opengl.GL30.glCheckFramebufferStatus;
import static org.lwjgl.opengl.GL30.glFramebufferTexture2D;

import java.util.Random;

import org.lwjgl.utils.vector.Vector3f;

import xueli.game.utils.Shader;
import xueli.game.utils.math.MathUtils;
import xueli.game.utils.math.MatrixHelper;

public class SSAO extends FrameBuffer {

	private static Vector3f[] randomFloats = new Vector3f[64];
	private static float[] noise = new float[64];

	static {
		Random random = new Random();

		for (int i = 0; i < 64; i++) {
			randomFloats[i] = new Vector3f(random.nextFloat() * 2 - 1, random.nextFloat() * 2 - 1, random.nextFloat());
			randomFloats[i].normalise();
			randomFloats[i].scale(random.nextFloat());

			float scale = i / 64.0f;
			scale = MathUtils.mixLinear(0.1f, 1.0f, scale * scale);

			randomFloats[i].scale(scale);
		}

		for (int i = 0; i < 16; i++) {
			noise[i] = random.nextFloat() * 2 - 1;
			noise[i + 1] = random.nextFloat() * 2 - 1;
			noise[i + 2] = 0;
			noise[i + 3] = 1.0f;

		}

	}

	private int noiseTex;
	private int ssaoColorBuffer;
	private int gBuffer_Normal, gBuffer_FragPos;

	private Shader quadShader;
	private ScreenQuadRenderer quadRenderer;

	public SSAO() {
		super();
	}

	public SSAO(int width, int height) {
		super(width, height);
	}

	@Override
	protected void create() {
		super.create();

		this.quadShader = new Shader("res/shaders/screen_quad_ssao/vert.txt", "res/shaders/screen_quad_ssao/frag.txt");
		this.quadShader.use();
		quadShader.setInt(quadShader.getUnifromLocation("tex"), 0);
		quadShader.setInt(quadShader.getUnifromLocation("ssaoColor"), 1);
		quadShader.setInt(quadShader.getUnifromLocation("noise"), 2);
		quadShader.setInt(quadShader.getUnifromLocation("normal"), 3);
		quadShader.setInt(quadShader.getUnifromLocation("frag"), 4);
		for (int i = 0; i < 64; i++) {
			quadShader.setUniformVector3(quadShader.getUnifromLocation("samplers[" + i + "]"), randomFloats[i]);
		}
		this.quadShader.unbind();

		this.quadRenderer = new ScreenQuadRenderer(quadShader);

		noiseTex = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, noiseTex);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB16F, 4, 4, 0, GL_RGB, GL_FLOAT, noise);

		gBuffer_Normal = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, gBuffer_Normal);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB16F, width, height, 0, GL_RGB, GL_FLOAT, 0);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glBindTexture(GL_TEXTURE_2D, 0);

		gBuffer_FragPos = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, gBuffer_FragPos);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB16F, width, height, 0, GL_RGB, GL_FLOAT, 0);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glBindTexture(GL_TEXTURE_2D, 0);

		ssaoColorBuffer = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, ssaoColorBuffer);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT, width, height, 0, GL_DEPTH_COMPONENT, GL_FLOAT, 0);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glBindTexture(GL_TEXTURE_2D, 0);

		glBindFramebuffer(GL_FRAMEBUFFER, fbo);
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_TEXTURE_2D, ssaoColorBuffer, 0);
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT1, GL_TEXTURE_2D, gBuffer_Normal, 0);
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT2, GL_TEXTURE_2D, gBuffer_FragPos, 0);
		if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
			throw new RuntimeException("FrameBuffer not complete!");
		}
		glBindFramebuffer(GL_FRAMEBUFFER, 0);

	}

	public void render() {
		quadShader.use();
		quadShader.setUniformMatrix(quadShader.getUnifromLocation("projection"), MatrixHelper.lastTimeProjMatrix);

		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, this.tbo_image);
		glActiveTexture(GL_TEXTURE1);
		glBindTexture(GL_TEXTURE_2D, this.ssaoColorBuffer);
		glActiveTexture(GL_TEXTURE2);
		glBindTexture(GL_TEXTURE_2D, this.noiseTex);
		glActiveTexture(GL_TEXTURE3);
		glBindTexture(GL_TEXTURE_2D, this.gBuffer_Normal);
		glActiveTexture(GL_TEXTURE4);
		glBindTexture(GL_TEXTURE_2D, this.gBuffer_FragPos);

		quadRenderer.getPointer().initDraw();
		quadRenderer.getPointer().draw(GL_TRIANGLE_FAN, 0, 4);
		quadRenderer.getPointer().postDraw();

		quadShader.unbind();

	}

	public int getNoiseTex() {
		return noiseTex;
	}

}
