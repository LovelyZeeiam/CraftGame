package xueli.gamengine.utils.callbacks;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import xueli.gamengine.utils.Logger;

import java.util.ArrayList;

public abstract class MouseButtonCallback extends GLFWMouseButtonCallback {

	// 只要按下按钮 true就不会停息
	public static boolean[] buttons = new boolean[8];

	public static boolean[] keysOnce = new boolean[65536];
	private static ArrayList<Integer> keyPressed = new ArrayList<Integer>();

	@Override
	public void invoke(long window, int button, int action, int mods) {
		if (button < 0) {
			Logger.info("[Mouse] What is this button? -1");
			return;
		}

		buttons[button] = action != GLFW.GLFW_RELEASE;

		keysOnce[button] = action == GLFW.GLFW_RELEASE;
		keyPressed.add(button);

	}

	public static void tick() {
		for (int keyID : keyPressed)
			keysOnce[keyID] = false;
		keyPressed.clear();

	}

}
