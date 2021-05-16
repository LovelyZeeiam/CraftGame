package xueli.game.renderer;

import static org.lwjgl.opengl.GL30.*;

public class SSAO extends FrameBuffer {

	public SSAO() {
		super();
	}

	public SSAO(int width, int height) {
		super(width, height);
	}
	
	@Override
	protected void create() {
		this.fbo = glGenFramebuffers();
		
		this.tbo_image = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, tbo_image);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RED, width, height, 0, GL_RGB, GL_FLOAT, 0);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glBindTexture(GL_TEXTURE_2D, 0);

		glBindFramebuffer(GL_FRAMEBUFFER, fbo);
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, tbo_image, 0);
		
		if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
			throw new RuntimeException("FrameBuffer not complete!");
		}

		glBindFramebuffer(GL_FRAMEBUFFER, 0);
		
	}

}
