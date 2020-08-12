package xueLi.gamengine.view;

import java.util.HashMap;

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
		if (background != null)
			background.draw(nvg);
		for (ViewWidget widget : widgets.values()) {
			widget.draw(nvg);
		}
	}

	public void size() {
		if (background != null)
			background.size();
		for (ViewWidget widget : widgets.values()) {
			widget.size();
		}
	}

}
