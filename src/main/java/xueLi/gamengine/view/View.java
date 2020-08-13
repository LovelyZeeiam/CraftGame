package xueLi.gamengine.view;

import static org.lwjgl.nanovg.NanoVG.nvgBeginFrame;
import static org.lwjgl.nanovg.NanoVG.nvgEndFrame;

import java.util.HashMap;

import xueLi.gamengine.utils.Display;

public class View {

	public String titleString;

	public GUIBackground background;
	public HashMap<String, ViewWidget> widgets = new HashMap<String, ViewWidget>();

	public View(String titleString) {
		this.titleString = titleString;

	}

	public void create() {

	}

	public void draw(long nvg) {
		nvgBeginFrame(nvg, Display.currentDisplay.getWidth(), Display.currentDisplay.getHeight(),
				Display.currentDisplay.getRatio());
		if (background != null)
			background.draw(nvg);
		for (ViewWidget widget : widgets.values()) {
			widget.draw(nvg);
		}
		nvgEndFrame(nvg);
	}

	public void size() {
		if (background != null)
			background.size();
		for (ViewWidget widget : widgets.values()) {
			widget.size();
		}

	}

	public void delete() {

	}

}
