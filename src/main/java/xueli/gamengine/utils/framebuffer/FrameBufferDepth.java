package xueli.gamengine.utils.framebuffer;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

public class FrameBufferDepth {

	private int fbo, rbq, tbo_depth;

	private int width, height;

	public void create(int width, int height) {
		this.width = width;
		this.height = height;

		this.fbo = glGenFramebuffers();
		glBindFramebuffer(GL_FRAMEBUFFER, fbo);

		this.tbo_depth = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, this.tbo_depth);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT, width, height, 0, GL_DEPTH_COMPONENT, GL_FLOAT, 0);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_TEXTURE_2D, tbo_depth, 0);
		glDrawBuffer(GL_NONE);
		glReadBuffer(GL_NONE);
		glBindTexture(GL_TEXTURE_2D, 0);

		glBindFramebuffer(GL_FRAMEBUFFER, 0);

	}

	public void size(int width, int height) {
		release();
		create(width, height);

	}

	public int getFbo() {
		return fbo;
	}

	public int getRbq() {
		return rbq;
	}

	public int getTbo_depth() {
		return tbo_depth;
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
		GL15.glDeleteTextures(tbo_depth);
		GL30.glDeleteRenderbuffers(rbq);
		GL30.glDeleteFramebuffers(fbo);
	}

}
