package xueLi.craftGame.gui;

import xueLi.craftGame.inputListener.KeyEvent;
import xueLi.craftGame.inputListener.MouseButtonEvent;

import static org.lwjgl.nanovg.NanoVG.*;

import static xueLi.craftGame.gui.GUIRenderer.nvg;

import org.lwjgl.glfw.GLFW;

/**
 * puppet Button 嘻嘻
 *
 */
public class GuiButtonWithBox extends GuiImageView {
	
	private static float box_width = 2.0f;

	public GuiButtonWithBox(String path, float x, float y, WidgetAlignment alignment, float width, float height) {
		super(path, x, y, alignment, width, height);

	}

	@Override
	public void render() {
		super.render();
		if(isMouseCovered()) {
			nvgBeginPath(nvg);
			
			nvgFillColor(nvg, Color.rgba(255, 255, 255, 255));
			//上边框
			nvgRoundedRect(nvg, x - box_width, y - box_width, box_width * 2 + width , box_width, 0);
			//左边框
			nvgRoundedRect(nvg, x - box_width, y , box_width, height, 0);
			//下边框
			nvgRoundedRect(nvg, x - box_width, y + height, box_width * 2 + width , box_width, 0);
			//右边框
			nvgRoundedRect(nvg, x + width, y , box_width, height, 0);
			
			nvgFill(nvg);
			
		}

	}
	
	public void onLeftClick() {
	}
	
	public void onRightClick() {
	}

	@Override
	public void onKeyEvent(KeyEvent event) {
		
	}

	@Override
	public void onMouseButtonEvent(MouseButtonEvent event) {
		//左键点击
		if(event.button == GLFW.GLFW_MOUSE_BUTTON_LEFT && event.action == GLFW.GLFW_RELEASE)
			onLeftClick();
		//右键点击
		else if(event.button == GLFW.GLFW_MOUSE_BUTTON_RIGHT && event.action == GLFW.GLFW_RELEASE)
			onRightClick();

	}

}
