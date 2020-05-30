package xueLi.craftGame.gui;

import xueLi.craftGame.utils.Display;

public class GUIRenderer {

	public static GUI currentGui;
	private static GUIShader shader;
	
	static {
		shader = new GUIShader();
	}
	
	public static void updateOrthoMatrix() {
		shader.use();
		shader.updateOrthoMatrix();
		shader.unbind();
			
	}

	public static void setGUI(GUI gui) {
		currentGui = gui;
		if (gui == null)
			Display.setSubtitie(null);
		else
			Display.setSubtitie(gui.title);
	}

	public static void render() {
		if (currentGui != null) {
			shader.use();
			currentGui.render();
			shader.unbind();
			
			
		}
	}

}
