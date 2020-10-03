package xueLi.gamengine.view;

import xueLi.gamengine.resource.GuiResource;
import xueLi.gamengine.resource.Options;
import xueLi.gamengine.utils.Display;
import xueLi.gamengine.utils.FrameBuffer;
import xueLi.gamengine.utils.Logger;
import xueLi.gamengine.utils.Shader;
import xueLi.gamengine.view.GUIFader.Fader;

import static org.lwjgl.nanovg.NanoVGGL3.*;

import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;

import static org.lwjgl.nanovg.NanoVG.*;

public class ViewManager {

	private Display display;
	private Options options;

	// 当前GUI
	private volatile View currentGui;
	private FrameBuffer currentFrameBuffer;
	// 即将淡入的GUI
	private volatile View fadeInGui;
	private FrameBuffer fadeInFrameBuffer;
	// 是否在淡入淡出
	private boolean isFading = false;
	// 控制GUI的着色器
	private Shader guiShader;
	private Fader fader;
	// 锵 ~ GUIの标准素养 之 全屏展示
	private static int vao, vbo, tpo;
	// GUI的fade时间 让gui有一个逐渐显示的过程
	private long fade_duration;

	// 上次鼠标点在哪个widget上面 在输入文字的时候会用到
	private ViewWidget focusedWidget;

	// Gui渲染系统的实例的内存地址
	private static long nvg;
	// 字体ID
	private int fontID = -1;

	static {
		nvg = nvgCreate(NVG_STENCIL_STROKES | NVG_ANTIALIAS | NVG_DEBUG);
		if (nvg == 0) {
			Logger.error(new Throwable("[GUI] Emm, You don't want a game without gui, do u?"));
		}

		IntBuffer verticesBuffer = BufferUtils.createIntBuffer(8);
		verticesBuffer.put(new int[] { 1, 1, 1, -1, -1, -1, -1, 1, });
		verticesBuffer.flip();
		IntBuffer texCoordBuffer = BufferUtils.createIntBuffer(8);
		texCoordBuffer.put(new int[] { 1, 1, 1, 0, 0, 0, 0, 1, });
		texCoordBuffer.flip();

		vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);
		vbo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(0, 2, GL11.GL_INT, false, 0, 0);
		GL20.glEnableVertexAttribArray(0);
		tpo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, tpo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, texCoordBuffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(1, 2, GL11.GL_INT, false, 0, 0);
		GL20.glEnableVertexAttribArray(1);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);

	}

	public ViewManager(Display display, Options options, Shader guiShader) {
		this.display = display;
		this.guiShader = guiShader;
		this.options = options;

		guiShader.use();
		guiShader.setInt(guiShader.getUnifromLocation("texture1"), 0);
		guiShader.setInt(guiShader.getUnifromLocation("texture2"), 1);
		restoreShader();
		guiShader.unbind();

		if (options != null)
			fade_duration = this.options.get("fade_duration").getAsLong();

		currentFrameBuffer = new FrameBuffer();
		fadeInFrameBuffer = new FrameBuffer();

	}

	public int loadTexture(String path, int flag) {
		return nvgCreateImage(nvg, path, flag);
	}

	private GuiResource resource;

	public void setResourceSource(GuiResource resource) {
		this.resource = resource;

	}

	public void setFont(String pathString) {
		this.fontID = nvgCreateFont(nvg, "simhei", resource.getPathString() + "fonts/" + pathString);
		if (this.fontID == -1) {
			Logger.error("[Font] Can't create font!");
		}
	}

	public View setGui(String guiName) {
		focusedWidget = null;
		this.currentGui = resource.getGui(guiName);
		this.currentGui.create();
		this.currentGui.size();
		display.setSubtitle(currentGui.titleString);
		return currentGui;
	}

	public void setGui(View gui) {
		focusedWidget = null;
		if (gui == null || this.currentGui == null) {
			this.currentGui.delete();
			this.currentGui = null;
			display.setSubtitle(null);
			return;
		}
		this.currentGui = gui;
		this.currentGui.create();
		this.currentGui.size();
		display.setSubtitle(gui.titleString);
	}

	private long fadeStartTime = -1;

	public View setFadeinGui(String guiName, Fader fader) {
		focusedWidget = null;
		this.fadeInGui = resource.getGui(guiName);
		this.fadeInGui.create();
		this.fadeInGui.size();
		this.fader = fader;
		this.isFading = true;
		return this.fadeInGui;
	}

	public void setFadeinGui(View gui, Fader fader) {
		focusedWidget = null;
		this.fadeInGui = gui;
		this.fadeInGui.create();
		this.fadeInGui.size();
		this.fader = fader;
		this.isFading = true;
	}

	public boolean needToRender = true;

	public void draw() {
		needToRender = false;

		if (currentGui != null) {
			currentFrameBuffer.bind();
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);

			synchronized (this.currentGui) {
				currentGui.draw(nvg);
			}

			currentFrameBuffer.unbind();

			needToRender = true;

		}

		if (fadeInGui != null) {
			if (this.fadeStartTime == -1) {
				this.fadeStartTime = System.currentTimeMillis();
				// System.out.println(this.fadeStartTime);
			}

			fadeInFrameBuffer.bind();
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);

			synchronized (this.fadeInGui) {
				fadeInGui.draw(nvg);
			}

			fadeInFrameBuffer.unbind();

			needToRender = true;

		}

		if (needToRender) {
			guiShader.use();

			restoreShader();

			if (this.fader != null) {
				// System.out.println(fadeStartTime);
				this.isFading = !fader.fade(fade_duration, guiShader, fadeStartTime);

			}

			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, currentFrameBuffer.getTbo());
			GL13.glActiveTexture(GL13.GL_TEXTURE1);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, fadeInFrameBuffer.getTbo());

			GL30.glBindVertexArray(vao);
			GL11.glDrawArrays(GL11.GL_TRIANGLE_FAN, 0, 4);
			GL30.glBindVertexArray(0);
			guiShader.unbind();

			if (!this.isFading & this.fadeInGui != null) {
				if (this.currentGui != null)
					this.currentGui.delete();
				this.currentGui = fadeInGui;
				this.fadeInGui = null;
				this.fadeStartTime = -1;
				this.fader = null;
				display.setSubtitle(currentGui.titleString);

				restoreShader();

			}

			GL11.glDisable(GL11.GL_BLEND);

		}

	}

	public FrameBuffer getCurrentFrameBuffer() {
		return currentFrameBuffer;
	}

	public FrameBuffer getFadeInFrameBuffer() {
		return fadeInFrameBuffer;
	}

	private void restoreShader() {
		guiShader.setFloat(guiShader.getUnifromLocation("mix_value"), 0.0f);
		guiShader.setUniformVector2(guiShader.getUnifromLocation("translate"), new Vector2f(0.0f, 0.0f));

	}

	public void size() {
		currentFrameBuffer.size(display.getWidth(), display.getHeight());
		fadeInFrameBuffer.size(display.getWidth(), display.getHeight());

		if (currentGui != null) {
			currentGui.size();

		}
		if (fadeInGui != null) {
			fadeInGui.size();

		}

	}

	public void mouseClicked(int button) {
		focusedWidget = null;
		if (currentGui != null) {
			for (ViewWidget widget : currentGui.widgets.values()) {
				if (display.getMouseX() > widget.real_x & display.getMouseX() < widget.real_x + widget.real_width
						& display.getMouseY() > widget.real_y
						& display.getMouseY() < widget.real_y + widget.real_height) {
					widget.mouseClicked = true;
					if (widget.onClickListener != null)
						widget.onClickListener.onClick(button);
					focusedWidget = widget;
				}
			}
		}

	}

	public void destroy() {
		nvgDelete(nvg);
		guiShader.release();

	}

}
