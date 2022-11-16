package xueli.game.utils;

import org.lwjgl.opengl.GL11;

public class GLHelper {

	public static void enableBlend() {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}

	public static void disableBlend() {
		GL11.glDisable(GL11.GL_BLEND);

	}

}
