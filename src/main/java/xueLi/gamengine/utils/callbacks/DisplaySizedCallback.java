package xueLi.gamengine.utils.callbacks;

import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL11;

public abstract class DisplaySizedCallback extends GLFWWindowSizeCallback {

	public int width, height;
	public float ratio;
	public float scale;

	@Override
	public void invoke(long window, int width, int height) {
		this.width = width;
		this.height = height;
		this.ratio = (float) width / height;

		if (width < 800 || height < 600)
			scale = 0.3f;
		else
			scale = 1.0f;

		GL11.glViewport(0, 0, width, height);

		sized();

	}

	public abstract void sized();

}
