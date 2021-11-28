package xueli.game.renderer;

import xueli.game.Game;
import xueli.game.utils.Shader;

import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glDrawBuffers;
import static org.lwjgl.opengl.GL30.*;

public class GBuffer extends FrameBuffer {

	private static final IntBuffer ATTACHMENTS;

	static {
		ATTACHMENTS = IntBuffer.allocate(3);
		int[] attachments = { GL_COLOR_ATTACHMENT0, GL_COLOR_ATTACHMENT1, GL_COLOR_ATTACHMENT2 };
		ATTACHMENTS.put(attachments);

	}

	private int gPos, gNormal, gTex;

	private Shader shader;

	public GBuffer() {
		super((int) Game.INSTANCE_GAME.getWidth(), (int) Game.INSTANCE_GAME.getHeight());

		shader = new Shader("res/shaders/g_buffer/vert.txt", "res/shaders/g_buffer/frag.txt");

	}

	@Override
	protected void create() {
		this.fbo = glGenFramebuffers();
		glBindFramebuffer(GL_FRAMEBUFFER, fbo);

		gPos = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, gPos);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB16F, width, height, 0, GL_RGB, GL_FLOAT, 0);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, gPos, 0);
		glBindTexture(GL_TEXTURE_2D, 0);

		gNormal = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, gNormal);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB16F, width, height, 0, GL_RGB, GL_FLOAT, 0);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT1, GL_TEXTURE_2D, gNormal, 0);
		glBindTexture(GL_TEXTURE_2D, 0);

		gTex = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, gTex);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_FLOAT, 0);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT2, GL_TEXTURE_2D, gTex, 0);
		glBindTexture(GL_TEXTURE_2D, 0);

		glDrawBuffers(ATTACHMENTS);

		this.rbo = glGenRenderbuffers();
		glBindRenderbuffer(GL_RENDERBUFFER, rbo);
		glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT, width, height);
		glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, rbo);
		glBindRenderbuffer(GL_RENDERBUFFER, 0);

		if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
			throw new RuntimeException("FrameBuffer not complete!");
		}

		glBindFramebuffer(GL_FRAMEBUFFER, 0);

	}

	@Override
	public void use() {
		super.use();
		shader.use();
	}

	@Override
	public void unbind() {
		shader.unbind();
		super.unbind();
	}

	@Override
	public void delete() {
		super.delete();

		glDeleteTextures(gPos);
		glDeleteTextures(gNormal);
		glDeleteTextures(gTex);

	}

	public int getgPos() {
		return gPos;
	}

	public int getgNormal() {
		return gNormal;
	}

	public int getgTex() {
		return gTex;
	}

	public Shader getShader() {
		return shader;
	}

}
