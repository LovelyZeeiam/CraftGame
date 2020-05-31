package xueLi.craftGame.gui;

import xueLi.craftGame.events.KeyEvent;
import xueLi.craftGame.events.MouseButtonEvent;
import xueLi.craftGame.utils.Display;
import xueLi.craftGame.utils.GLHelper;

public abstract class GUIWidget {

	public String label;
	public WidgetAlignment alignment;

	public float x, y;
	public float width, height;

	public boolean isFocused = false;

	public GUIWidget(float x, float y, WidgetAlignment alignment, float width, float height) {
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		this.alignment = alignment;

	}
	
	protected boolean isMouseCovered() {
		return GLHelper.isPointInGUIWidget(Display.mouseX, Display.mouseY, this);
	}

	public abstract void render();

	public abstract void onKeyEvent(KeyEvent event);

	public abstract void onMouseButtonEvent(MouseButtonEvent event);

}
