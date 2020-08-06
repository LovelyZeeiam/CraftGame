package xueLi.gamengine.gui;

import xueLi.gamengine.resource.GuiResource;
import xueLi.gamengine.utils.Display;
import xueLi.gamengine.utils.Logger;

import static org.lwjgl.nanovg.NanoVGGL3.*;

import org.lwjgl.opengl.GL11;

import static org.lwjgl.nanovg.NanoVG.*;

public class GUIManager {

	private Display display;

	private GUI currentGui;

	static long nvg;

	static {
		nvg = nvgCreate(NVG_STENCIL_STROKES | NVG_ANTIALIAS | NVG_DEBUG);
		if (nvg == 0) {
			Logger.error(new Throwable("[GUI] Emm, You don't want a game without gui, do it?"));
		}

	}

	public int loadTexture(String path) {
		return nvgCreateImage(nvg, path, NVG_IMAGE_GENERATE_MIPMAPS);
	}

	public GUIManager(Display display) {
		this.display = display;
	}

	private GuiResource resource;

	// 字体ID
	int fontID = -1;

	public void setResourceSource(GuiResource resource) {
		this.resource = resource;
		this.fontID = nvgCreateFont(nvg, "simhei", resource.getPathString() + "fonts/simhei.ttf");

	}

	public GUI setGui(String guiName) {
		this.currentGui = resource.getGui(guiName);
		display.setSubtitle(currentGui.titleString);
		return currentGui;
	}

	public void draw() {
		if (currentGui == null)
			return;
		GL11.glClearColor(currentGui.backgroundColor.r(), currentGui.backgroundColor.g(),
				currentGui.backgroundColor.b(), currentGui.backgroundColor.a());
		nvgBeginFrame(nvg, display.getWidth(), display.getHeight(), display.getRatio());
		currentGui.draw(nvg);
		nvgEndFrame(nvg);
	}

	public void size() {
		if (currentGui != null) {
			currentGui.size();
		}

	}

	public void destroy() {
		nvgDelete(nvg);

	}

}
