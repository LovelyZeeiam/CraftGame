package xueli.game.ui;

import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGLUFramebuffer;
import org.lwjgl.opengl.GL11;
import xueli.game.renderer.Renderer;
import xueli.game.utils.NVGColors;

import java.util.ArrayList;

import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.nanovg.NanoVGGL3.*;

public abstract class UIView implements Renderer {

	private UIRenderer ctxRenderer;

	private NVGLUFramebuffer framebuffer;
	public static final NVGColor ColorFrameBufferNull = NVGColors.DARK_GRAY;
	private boolean needRepaint = true;

	private float width = 0, height = 0;

	UIView fatherComponent;
	private ArrayList<UIView> subCompnents = new ArrayList<>();

	public UIView(UIRenderer ctx) {
		this.ctxRenderer = ctx;

	}

	@Override
	public void render() {
		if (framebuffer != null) {
			UI.drawTexture(ctxRenderer.nvg, 0, 0, width, height, framebuffer.image());
		} else {
			UI.drawRect(ctxRenderer.nvg, 0, 0, width, height, ColorFrameBufferNull);
		}

	}

	@Override
	public void update() {
		subCompnents.forEach(UIView::update);

		if (needRepaint) {
			size();
			stroke();
			needRepaint = false;
		}

	}

	public abstract void stroke();

	@Override
	public void size() {
		this.width = ctxRenderer.getGame().getWidth();
		this.height = ctxRenderer.getGame().getHeight();

		if (framebuffer != null) {
			nvgluDeleteFramebuffer(ctxRenderer.nvg, framebuffer);
		}
		framebuffer = nvgluCreateFramebuffer(ctxRenderer.nvg, (int) width, (int) height, NVG_IMAGE_NEAREST);

		subCompnents.forEach(UIView::requestRepaint);

	}

	@Override
	public void release() {
		if (framebuffer != null) {
			nvgluDeleteFramebuffer(ctxRenderer.nvg, framebuffer);
		}

		subCompnents.forEach(UIView::release);

	}

	protected void bindFrameBuffer() {
		nvgluBindFramebuffer(ctxRenderer.nvg, framebuffer);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);
		nvgBeginFrame(ctxRenderer.nvg, getWidth(), getHeight(), getWidth() / getHeight());
	}

	protected void unbindFrameBuffer() {
		nvgEndFrame(ctxRenderer.nvg);
		nvgluBindFramebuffer(ctxRenderer.nvg, null);
	}

	public <T extends UIView> T addView(T view) {
		subCompnents.add(view);
		view.fatherComponent = this;
		return view;
	}

	public <T extends UIView> T removeView(T view) {
		subCompnents.remove(view);
		return view;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public UIRenderer getCtxRenderer() {
		return ctxRenderer;
	}

	public int getImage(String pathInJar) {
		return ctxRenderer.getImageManager().getImage(pathInJar);
	}

	public void requestRepaint() {
		this.needRepaint = true;
		if (this.fatherComponent != null) {
			this.fatherComponent.requestRepaint();
		}
	}

}
