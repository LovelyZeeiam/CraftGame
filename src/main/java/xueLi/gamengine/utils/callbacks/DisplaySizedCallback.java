package xueLi.gamengine.utils.callbacks;

import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL11;

public abstract class DisplaySizedCallback extends GLFWWindowSizeCallback {

	public int width, height;
	public float ratio;
	public float scale;

	@Override
	public void invoke(long window, int width, int height) {
		if (width != 0)
			this.width = width;
		if (height != 0)
			this.height = height;
		this.ratio = (float) width / height;

		if (width < 400 || height < 300)
			scale = 0.1f;
		else if(width < 1000 || height < 600)
			scale = 0.4f;
		else
			scale = 1.0f;

		GL11.glViewport(0, 0, width, height);

		sized();

	}

	public abstract void sized();

}
