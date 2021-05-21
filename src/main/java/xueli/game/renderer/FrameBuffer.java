package xueli.game.renderer;

import static org.lwjgl.opengl.GL30.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import xueli.game.Game;

public class FrameBuffer {

	protected int width, height;
	
	protected int fbo, tbo_image, rbo;
	
	public FrameBuffer() {
		this((int)Game.INSTANCE_GAME.getWidth(), (int)Game.INSTANCE_GAME.getHeight());
		
	}
	
	public FrameBuffer(int width, int height) {
		this.width = width;
		this.height = height;
		
		create();
		
	}
	
	protected void create() {
		this.fbo = glGenFramebuffers();
		
		this.tbo_image = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, tbo_image);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, 0);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glBindTexture(GL_TEXTURE_2D, 0);
		
		this.rbo = glGenRenderbuffers();
		glBindRenderbuffer(GL_RENDERBUFFER, rbo);
		glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH24_STENCIL8, width, height);
		glBindRenderbuffer(GL_RENDERBUFFER, 0);

		glBindFramebuffer(GL_FRAMEBUFFER, fbo);
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, tbo_image, 0);
		glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_STENCIL_ATTACHMENT, GL_RENDERBUFFER, rbo);

		if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
			throw new RuntimeException("FrameBuffer not complete!");
		}

		glBindFramebuffer(GL_FRAMEBUFFER, 0);
		
	}
	
	// TODO: UPSIDE DOWN
	public void save(String path) {
		int[] data = new int[width * height];

		glBindFramebuffer(GL_FRAMEBUFFER, fbo);
		glReadPixels(0, 0, width, height, GL_RGBA, GL_UNSIGNED_BYTE, data);
		glBindFramebuffer(GL_FRAMEBUFFER, 0);

		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR_PRE);
		image.setRGB(0, 0, width, height, data, 0, width);
		
		try {
			ImageIO.write(image, "png", new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void use() {
		glBindFramebuffer(GL_FRAMEBUFFER, fbo);
		glViewport(0, 0, width, height);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
	}
	
	public void unbind() {
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
		glViewport(0, 0, (int)Game.INSTANCE_GAME.getWidth(), (int)Game.INSTANCE_GAME.getHeight());
	}
	
	public void resize() {
		this.width = (int) Game.INSTANCE_GAME.getWidth();
		this.height = (int) Game.INSTANCE_GAME.getHeight();
		
		delete();
		create();
		
	}
	
	public void delete() {
		glDeleteTextures(tbo_image);
		glDeleteFramebuffers(fbo);
		glDeleteRenderbuffers(rbo);
		
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getTbo_image() {
		return tbo_image;
	}
	
	public int getFbo() {
		return fbo;
	}
	
}
