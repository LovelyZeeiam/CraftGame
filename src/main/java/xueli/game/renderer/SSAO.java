package xueli.game.renderer;

import static org.lwjgl.opengl.GL30.*;

import java.util.Random;

import org.lwjgl.util.vector.Vector3f;

import xueli.game.utils.math.MathUtils;

public class SSAO extends FrameBuffer {
	
	private static Vector3f[] randomFloats = new Vector3f[64];
	
	
	static {
		Random random = new Random();
		
		for(int i = 0; i < 64; i++) {
			randomFloats[i] = new Vector3f(
				random.nextFloat() * 2 - 1,
				random.nextFloat() * 2 - 1,
				random.nextFloat()
			);
			randomFloats[i].normalise();
			randomFloats[i].scale(random.nextFloat());
			
			float scale = i / 64.0f;
			scale = MathUtils.mixLinear(0.1f, 1.0f, scale * scale);
			
			randomFloats[i].scale(scale);
		}
		
	}

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
