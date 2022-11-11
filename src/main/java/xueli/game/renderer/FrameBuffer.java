package xueli.game.renderer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

public class FrameBuffer {

	protected int width, height;

	protected int fbo, tbo_image, rbo;

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

		for (int i = 0; i < data.length; i++) {
			int origin = data[i];
			int r = (origin) & 0xff;
			int g = (origin >> 8) & 0xff;
			int b = (origin >> 16) & 0xff;
			int a = (origin >> 24) & 0xff;
			data[i] = (a << 24) + (r << 16) + (g << 8) + b;

		}

		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		image.setRGB(0, 0, width, height, data, 0, width);

		BufferedImage newImage = new BufferedImage(width, height, image.getType());
		Graphics2D g = newImage.createGraphics();
		g.rotate(Math.toRadians(180), width / 2.0f, height / 2.0f);
		g.drawImage(image, null, 0, 0);

		try {
			ImageIO.write(newImage, "png", new File(path));
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
	}

	public void resize(int width, int height) {
		this.width = width;
		this.height = height;

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
