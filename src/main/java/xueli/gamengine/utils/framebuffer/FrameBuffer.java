package xueli.gamengine.utils.framebuffer;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glReadPixels;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;
import static org.lwjgl.opengl.GL30.GL_DEPTH24_STENCIL8;
import static org.lwjgl.opengl.GL30.GL_DEPTH_STENCIL_ATTACHMENT;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_COMPLETE;
import static org.lwjgl.opengl.GL30.GL_RENDERBUFFER;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;
import static org.lwjgl.opengl.GL30.glBindRenderbuffer;
import static org.lwjgl.opengl.GL30.glCheckFramebufferStatus;
import static org.lwjgl.opengl.GL30.glDeleteFramebuffers;
import static org.lwjgl.opengl.GL30.glDeleteRenderbuffers;
import static org.lwjgl.opengl.GL30.glFramebufferRenderbuffer;
import static org.lwjgl.opengl.GL30.glFramebufferTexture2D;
import static org.lwjgl.opengl.GL30.glGenFramebuffers;
import static org.lwjgl.opengl.GL30.glGenRenderbuffers;
import static org.lwjgl.opengl.GL30.glRenderbufferStorage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import xueli.utils.Logger;

public class FrameBuffer {

	private int fbo, rbq, tbo;

	private int width, height;

	public void create(int width, int height) {
		this.width = width;
		this.height = height;

		this.fbo = glGenFramebuffers();
		glBindFramebuffer(GL_FRAMEBUFFER, fbo);

		this.tbo = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, this.tbo);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, 0);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, tbo, 0);
		glBindTexture(GL_TEXTURE_2D, 0);

		this.rbq = glGenRenderbuffers();
		glBindRenderbuffer(GL_RENDERBUFFER, rbq);
		glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH24_STENCIL8, width, height);
		glBindRenderbuffer(GL_RENDERBUFFER, 0);

		glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_STENCIL_ATTACHMENT, GL_RENDERBUFFER, rbq);

		if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
			Logger.error("[Frame Buffer] Can't create frame buffer!");
		}

		glBindFramebuffer(GL_FRAMEBUFFER, 0);

	}

	public void size(int width, int height) {
		release();
		create(width, height);

	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getFbo() {
		return fbo;
	}

	public int getRbq() {
		return rbq;
	}

	public int getTbo() {
		return tbo;
	}

	public void save(String path) {
		int[] data = new int[width * height];

		bind();
		glReadPixels(0, 0, width, height, GL_RGBA, GL_UNSIGNED_BYTE, data);
		unbind();

		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR_PRE);
		image.setRGB(0, 0, width, height, data, 0, width);
		try {
			ImageIO.write(image, "png", new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void bind() {
		glBindFramebuffer(GL_FRAMEBUFFER, fbo);
	}

	public void unbind() {
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
	}

	public void release() {
		glDeleteFramebuffers(fbo);
		glDeleteTextures(tbo);
		glDeleteRenderbuffers(rbq);

	}

}
