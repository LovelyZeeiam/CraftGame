package xueli.gamengine.utils;

import static org.lwjgl.opengl.GL30.*;

public class FrameBuffer {

    private int fbo, rbq, tbo;

    public void create(int width, int height) {
        this.fbo = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, fbo);

        this.tbo = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, this.tbo);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, 0);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glBindTexture(GL_TEXTURE_2D, 0);

        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, tbo, 0);

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

    public int getFbo() {
        return fbo;
    }

    public int getRbq() {
        return rbq;
    }

    public int getTbo() {
        return tbo;
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
