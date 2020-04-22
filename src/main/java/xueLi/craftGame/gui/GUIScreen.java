package xueLi.craftGame.gui;

import java.util.ArrayList;
import java.util.List;

public abstract class GUIScreen {

	private static List<GUIWidget> widgets = new ArrayList<GUIWidget>();

	public boolean isBeenShown = false;
	public final boolean isMouseGrubbed;

	public GUIScreen(boolean isMouseGrubbed) {
		this.isMouseGrubbed = isMouseGrubbed;
		
	}

	public void add(GUIWidget widget) {
		widgets.add(widget);
	}
	
	public abstract void update();

	public void render() {
		this.update();
		for (GUIWidget widget : widgets) {
			widget.update();
			widget.draw();
		}
	}

	public void show() {
		isBeenShown = true;
	}

	public void close() {
		for (GUIWidget widget : widgets) {
			widget.stopAnimation();
			widget.cleanUp();
		}
		isBeenShown = false;
	}

}
