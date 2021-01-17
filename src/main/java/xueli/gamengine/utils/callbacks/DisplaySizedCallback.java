package xueli.gamengine.utils.callbacks;

import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL11;

public abstract class DisplaySizedCallback extends GLFWWindowSizeCallback {

	public int width, height;
	public float ratio;
	public float scale;

	public static float getScale(int width, int height) {
		return Math.min(width, height) / 400.0f * 0.6f;
	}

	@Override
	public void invoke(long window, int width, int height) {
		if (width != 0)
			this.width = width;
		if (height != 0)
			this.height = height;
		this.ratio = (float) width / height;

		scale = getScale(width, height);

		GL11.glViewport(0, 0, width, height);

		sized();

	}

	public abstract void sized();

}
