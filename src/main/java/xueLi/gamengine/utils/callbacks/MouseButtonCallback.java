package xueLi.gamengine.utils.callbacks;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import xueLi.gamengine.utils.Logger;

public abstract class MouseButtonCallback extends GLFWMouseButtonCallback {

	// 只要按下按钮 true就不会停息
	public boolean[] buttons = new boolean[8];

	@Override
	public void invoke(long window, int button, int action, int mods) {
		if (button < 0) {
			Logger.info("[Mouse] What is this button? -1");
			return;
		}

		buttons[button] = action != GLFW.GLFW_RELEASE;

	}

}
