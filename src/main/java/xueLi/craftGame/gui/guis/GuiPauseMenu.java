package xueLi.craftGame.gui.guis;

import xueLi.craftGame.gui.GUI;
import xueLi.craftGame.gui.GUIRenderer;
import xueLi.craftGame.gui.GuiButtonWithBox;
import xueLi.craftGame.gui.GuiImageView;
import xueLi.craftGame.gui.GuiTextView;
import xueLi.craftGame.gui.WidgetAlignment;
import xueLi.craftGame.utils.Display;
import xueLi.craftGame.utils.TaskManager;
import static org.lwjgl.nanovg.NanoVG.*;

import org.lwjgl.util.vector.Vector4b;

public class GuiPauseMenu extends GUI {

	static {
		// Grub Mouse
		TaskManager.addTask(0, () -> {
			Display.grabMouse(true);
		});

	}

	private GuiTextView pausedTextView;
	private GuiImageView imageView;

	public GuiPauseMenu() {
		super("Game paused");

		pausedTextView = new GuiTextView("Game Paused", "System", 20,
				new Vector4b((byte) 249, (byte) 249, (byte) 249, (byte) 249), NVG_ALIGN_CENTER | NVG_ALIGN_TOP,
				Display.d_width / 2, 0, 100, 100);
		super.addWidget(pausedTextView);

		imageView = new GuiButtonWithBox("res/test.png", new Vector4b((byte) 249, (byte) 249, (byte) 249, (byte) 249),
				new Vector4b((byte) 0, (byte) 200, (byte) 255, (byte) 255), 0, 0, WidgetAlignment.NONE, 200, 200) {
			@Override
			public void onLeftClick() {
				TaskManager.addTaskToMainThread(0);
				// 正常的将自己的GUI变成空
				GUIRenderer.setGUI(null);
				
				GUI.playSound(0);

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

		// 屏幕中央
		imageView.x = (Display.d_width - imageView.width) / 2;
		imageView.y = (Display.d_height - imageView.height) / 2;

	}

}
