package xueLi.gamengine.gui;

import java.util.HashMap;

import org.lwjgl.nanovg.NVGColor;

public class GUI {

	public String titleString;
	public NVGColor backgroundColor;
	
	public HashMap<String, GUIWidget> widgets = new HashMap<String, GUIWidget>();

	public GUI(String titleString) {
		this.titleString = titleString;

	}

	public void draw(long nvg) {
		for (GUIWidget widget : widgets.values()) {
			widget.draw(nvg);
		}
	}

	public void size() {
		for (GUIWidget widget : widgets.values()) {
			widget.size();
		}
	}

}
