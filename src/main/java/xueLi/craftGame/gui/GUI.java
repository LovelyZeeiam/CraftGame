package xueLi.craftGame.gui;

import java.util.ArrayList;

import xueLi.craftGame.events.KeyEvent;
import xueLi.craftGame.events.MouseButtonEvent;
import xueLi.craftGame.utils.GLHelper;

public class GUI {

	public String title;
	
	private volatile ArrayList<GUIWidget> widgets = new ArrayList<GUIWidget>();
	
	public GUI(String title) {
		this.title = title;
		
	}
	
	protected void addWidget(GUIWidget widget) {
		this.widgets.add(widget);
	}
	
	public void processMouseButtonEvent(MouseButtonEvent event) {
		for (GUIWidget guiWidget : widgets) {
			guiWidget.isFocused = false;
			if(GLHelper.isPointInGUIWidget(event.x, event.y, guiWidget)) {
				guiWidget.onMouseButtonEvent(event);
				guiWidget.isFocused = true;
			}
		}
		
	}
	
	public void processKeyEvent(KeyEvent event) {
		for (GUIWidget guiWidget : widgets) {
			if(guiWidget.isFocused)
				guiWidget.onKeyEvent(event);
		}

	}

	public void render() {
		for(GUIWidget widget : widgets) {
			widget.render();
		}
	}
	
}
