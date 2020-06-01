package xueLi.craftGame.gui.guis;

import xueLi.craftGame.gui.Color;
import xueLi.craftGame.gui.GUI;
import xueLi.craftGame.gui.GUIRenderer;
import xueLi.craftGame.gui.GuiButtonWithBox;
import xueLi.craftGame.gui.GuiImageView;
import xueLi.craftGame.gui.GuiTextView;
import xueLi.craftGame.gui.WidgetAlignment;
import xueLi.craftGame.utils.Display;

import static org.lwjgl.nanovg.NanoVG.*;

import org.lwjgl.glfw.GLFW;

public class GuiPauseMenu extends GUI {

	private GuiTextView pausedTextView;
	private GuiImageView imageView;

	public GuiPauseMenu() {
		super("Game paused");

		pausedTextView = new GuiTextView("Game Paused", "System", 20, Color.rgba(255, 255, 255, 255),
				NVG_ALIGN_CENTER | NVG_ALIGN_TOP, Display.d_width / 2, 0, 100, 20);
		super.addWidget(pausedTextView);

		imageView = new GuiButtonWithBox("res/test.png", 0,0, WidgetAlignment.NONE, 200, 200) {
			@Override
			public void onLeftClick() {
				// 咱就假装按下了这个键的亚子
				// 因为不是主线程去设置GLFW的鼠标指针模式会出问题(笑哭)
				Display.keysOnce[GLFW.GLFW_KEY_ESCAPE] = true;
				// 正常的将自己的GUI变成空
				GUIRenderer.setGUI(null);
				
			}
		};
		super.addWidget(imageView);

	}

	@Override
	public void update() {

	}

	@Override
	public void sizedUpdate(float width, float height) {
		pausedTextView.x = width / 2;
		
		//屏幕中央
		imageView.x = (Display.d_width - imageView.width) / 2;
		imageView.y = (Display.d_height - imageView.height) / 2;

	}

}
