package xueli.game.utils;

import org.lwjgl.opengl.GL11;

import xueli.utils.io.Log;

public class GLHelper {

	public static void checkGLError(String state) {
		int error = -1;
		while (error != 0) {
			error = GL11.glGetError();
			if (error != 0)
				System.out.println("[" + state + "] OpenGL Error: " + error);
		}
	}

	public static void printDeviceInfo() {
		String nameString = GL11.glGetString(GL11.GL_VENDOR);
		String platform = GL11.glGetString(GL11.GL_RENDERER);
		String glVersion = GL11.glGetString(GL11.GL_VERSION);

		Log.logger.info("OpenGL: " + nameString + ", " + platform + ", " + glVersion);

	}

}
