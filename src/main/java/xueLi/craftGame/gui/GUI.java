package xueLi.craftGame.gui;

import java.util.ArrayList;

import xueLi.craftGame.inputListener.KeyEvent;
import xueLi.craftGame.inputListener.MouseButtonEvent;
import xueLi.craftGame.utils.ALHelper;
import xueLi.craftGame.utils.AudioManager;
import xueLi.craftGame.utils.GLHelper;

public abstract class GUI {

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
			if (GLHelper.isPointInGUIWidget(event.x, event.y, guiWidget)) {
				guiWidget.onMouseButtonEvent(event);
				guiWidget.isFocused = true;
			}
		}

	}

	public void processKeyEvent(KeyEvent event) {
		for (GUIWidget guiWidget : widgets) {
			if (guiWidget.isFocused)
				guiWidget.onKeyEvent(event);
		}

	}

	public abstract void update();

	public abstract void sizedUpdate(float width, float height);

	public void render() {
		for (GUIWidget widget : widgets) {
			widget.render();
		}
	}

	private static ALHelper.Speaker speaker;

	static {
		speaker = new ALHelper.Speaker(1, 1);

	}

	public static void playSound(int id) {
		if (speaker.isPlaying())
			speaker.stop();
		else {
			ALHelper.Audio audio = AudioManager.getBuffer(id);
			speaker.play(audio.bufferID);
		}
	}

}
