package xueLi.craftGame.gui;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GUIRenderer {

	private static GUIScreen currentGUI;
	public static GUIShader shader;
	public static boolean mouseGrubbed = true;
	
	static {
		shader = new GUIShader();
	}
	
	public static void setGUI(GUIScreen screen) {
		if(currentGUI != null)
			currentGUI.close();
		currentGUI = screen;
		if(screen == null) {
			mouseGrubbed = true;
			Mouse.setGrabbed(true);
			return;
		}
		currentGUI.show();
		mouseGrubbed = currentGUI.isMouseGrubbed;
		Mouse.setGrabbed(mouseGrubbed);
	}
	
	public static void draw() {
		if(currentGUI == null)
			return;
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		shader.use();
		currentGUI.render();
		shader.unbind();
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}

	public static void removeGUI() {
		currentGUI.close();
		currentGUI = null;
		mouseGrubbed = true;
		Mouse.setGrabbed(true);
	}
	
}
