package xueli.gamengine.utils;

import org.lwjgl.opengl.GL11;

public class GLHelper {

    public static void checkGLError(String state) {
        int error = GL11.glGetError();
        if (error != 0) {
            System.out.println("[" + state + "] OpenGL Error: " + error);
        }
    }

    public static void printDeviceInfo() {
        String nameString = GL11.glGetString(GL11.GL_VENDOR);
        String platform = GL11.glGetString(GL11.GL_RENDERER);
        String glVersion = GL11.glGetString(GL11.GL_VERSION);

        Logger.info("OpenGL: " + nameString + ", " + platform + ", " + glVersion);

    }

}
