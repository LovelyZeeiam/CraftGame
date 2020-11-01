package xueLi.gamengine.view;

import xueLi.gamengine.utils.Display;

import java.util.HashMap;

import static org.lwjgl.nanovg.NanoVG.nvgBeginFrame;
import static org.lwjgl.nanovg.NanoVG.nvgEndFrame;

public class View {

	public String titleString;

	public GUIBackground background;
	public HashMap<String, ViewWidget> widgets = new HashMap<String, ViewWidget>();

	public HashMap<String, IAnimation> animations = new HashMap<String, IAnimation>();
	private IAnimation currentAnimation;

	public View(String titleString) {
		this.titleString = titleString;

	}

	public void create() {

	}

	public void draw(long nvg) {
		if (this.currentAnimation != null) {
			this.currentAnimation.tick(widgets);
		}
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

	public void setAnimation(String name) {
		// 默认IAnimation instanceof GuiAnimation或GuiAnimationGroup
		this.currentAnimation = animations.get(name);
		this.currentAnimation.start(widgets, null);
	}

}
