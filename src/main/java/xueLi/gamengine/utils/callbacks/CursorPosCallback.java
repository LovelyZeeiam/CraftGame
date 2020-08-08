package xueLi.gamengine.utils.callbacks;

import org.lwjgl.glfw.GLFWCursorPosCallback;

public abstract class CursorPosCallback extends GLFWCursorPosCallback {

	private double lastTimeMouseX = 0, lastTimeMouseY = 0;
	public double mouseDX = 0, mouseDY = 0;
	public double mouseX = 0, mouseY = 0;

	@Override
	public void invoke(long window, double xpos, double ypos) {
		mouseDX = xpos - lastTimeMouseX;
		mouseDY = lastTimeMouseY - ypos;

		lastTimeMouseX = xpos;
		lastTimeMouseY = ypos;

		mouseX = xpos;
		mouseY = ypos;
	}

	public abstract void invoke();

}
